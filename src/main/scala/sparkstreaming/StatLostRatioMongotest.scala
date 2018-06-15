package sparkstreaming

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.ZKGroupTopicDirs
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaManager, KafkaUtils, OffsetRange}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  */
object StatLostRatioMongotest {
  def main(args: Array[String]): Unit = {


    val sparkConf = new SparkConf().setAppName("1").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")
    val ssc = new StreamingContext(sc, Seconds(5))

    val zkHost = "192.168.40.130:2181,192.168.40.131:2181,192.168.40.132:2181"
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> "192.168.40.130:9092,192.168.40.131:9092,192.168.40.132:9092",
      "group.id" -> "g",
      "zookeeper.connect" -> zkHost,
      "auto.offset.reset" -> "largest"
    )
    import org.apache.zookeeper.ZooKeeper
    import org.apache.zookeeper.data.Stat
    import org.apache.zookeeper.WatchedEvent

    import org.apache.zookeeper.Watcher
    val zkClient = new  ZooKeeper(zkHost, 2000, new Watcher(){
      def process(event: WatchedEvent): Unit = { // 收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
      }
    })
    val topicstr="test5"
    val topic=Set(topicstr)
    var offsetRanges = Array[OffsetRange]()
    val km = new KafkaManager(kafkaParams)
    val topicDirs = new ZKGroupTopicDirs("g", "test5")  //创建一个 ZKGroupTopicDirs 对象，对保存
    val children = zkClient.getChildren(s"${topicDirs.consumerOffsetDir}",false).size()    //查询该路径下是否字节点（默认有字节点为我们自己保存不同 partition 时生成的
    var fromOffsets: Map[TopicAndPartition, Long] = Map()   //如果 zookeeper 中有保存 offset，我们会利用这个 offset 作为 kafkaStream 的起始位置
    var messages: InputDStream[(String, String)] = null
    if (children > 0) {   //如果保存过 offset，这里更好的做法，还应该和  kafka 上最小的 offset 做对比，不然会报 OutOfRange 的错误
      for (i <- 0 until children) {
        print(s"${topicDirs.consumerOffsetDir}/${i}")
        val  stat = new org.apache.zookeeper.data.Stat();
        val partitionOffset = zkClient.getData(s"${topicDirs.consumerOffsetDir}/${i}",false,null)
        val tp = TopicAndPartition(topicstr, i)
        fromOffsets += (tp -> new String(partitionOffset).toLong)  //将不同 partition 对应的 offset 增加到 fromOffsets 中
      }
      val messageHandler = (mmd : MessageAndMetadata[String, String]) => (mmd.topic, mmd.message())  //这个会将 kafka 的消息进行 transform，最终 kafak 的数据都会变成 (topic_name, message) 这样的 tuple
      messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](ssc, kafkaParams, fromOffsets, messageHandler)
    } else {
      messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topic) //如果未保存，根据 kafkaParam 的配置使用最新或者最旧的 offset
    }
    messages.transform{ (rdd,time) =>
      offsetRanges = rdd.asInstanceOf[org.apache.spark.streaming.kafka.HasOffsetRanges].offsetRanges
      rdd
    }.foreachRDD(rdd => {//foreachrdd其实只有一个rdd，rdd中的分区数默认是kafka的分区数。
      for (o <- offsetRanges) {
        println(s"${o.topic} ${o.partition} ${o.fromOffset} ${o.untilOffset}")
      }
      if (!rdd.isEmpty()) {
        //处理消息
        val rdd_2=rdd.map(x=>(x._1,x._2))
        rdd_2.collect().foreach(println(_))
        // 再更新offsets
        km.updateZKOffsets(rdd,offsetRanges)
      }
    })

    ssc.start()
    ssc.awaitTermination()
    ssc.stop(true,true)
  }
}





