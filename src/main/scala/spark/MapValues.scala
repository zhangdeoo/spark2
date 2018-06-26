package spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  *RDD中的Key保持不变，与新的Value一起组成新的RDD中的元素。因此，该函数只适用于元素为KV对的RDD。
  * @author zhanglingjun@17paipai.cn 861724927
  *         2018/6/25 15:08
  */
object MapValues {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Spark Rdd Test").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val a = sc.parallelize(List("dog", "tiger", "lion", "cat", "panther", " eagle"), 2)
    val b = a.map(x => (x.length, x))
    b.mapValues("x" + _ + "x")
      .collect
      .foreach(println(_))
  }

}
