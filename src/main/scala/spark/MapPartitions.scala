package spark
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
/**
  *
  *map()的输入函数是应用于RDD中每个元素，而mapPartitions()的输入函数是应用于每个分区
  * @author zhanglingjun@17paipai.cn 861724927
  *         2018/6/25 15:08
  */
object MapPartitions {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Spark Rdd Test").setMaster("local[2]")
    val spark = new SparkContext(conf)
    val input = spark.parallelize(List(1, 2, 3, 4, 5, 6), 2)//RDD有6个元素，分成2个partition
    val result = input.mapPartitions(
      partition => Iterator(sumOfEveryPartition(partition))
    )//partition是传入的参数，是个list，要求返回也是list，即Iterator(sumOfEveryPartition(partition))
    result.collect().foreach {
      println(_)//6 15
    }
    spark.stop()
  }
  def sumOfEveryPartition(input: Iterator[Int]): Int = {
    var total = 0
    input.foreach { elem =>
      total += elem
    }
    total
  }
}
