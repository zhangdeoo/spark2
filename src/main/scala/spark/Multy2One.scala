package spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * n行转1行
  * @author zhanglingjun@17paipai.cn 861724927
  *         2018/6/25 15:08
  */
object Multy2One {

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Spark Rdd Test").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val _from = "newquery.txt"

    val s = sc.textFile(_from).collect()
    val n = 2
    val numSlices = s.length / n
    val x = sc.parallelize(s, numSlices).zipWithIndex().aggregate(List.empty[List[String]])(seqOp = (result, lineWithIndex) => {
      lineWithIndex match {
        case (line, index) =>
          if (index % n == 0) List(line) :: result else (line :: result.head) :: result.tail
      }
    }, combOp = (x, y) => x ::: y).map(_.reverse mkString " ")

    sc.parallelize(x).collect().foreach(println(_))
//      .saveAsTextFile(_to)
  }

}
