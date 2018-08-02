package spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  *select($"name",$"sum(num1)".cast(LongType))转换科学计数法为long
  *
  * @author zhanglingjun@17paipai.cn 861724927
  *         2018/6/26 17:42
  */
object SqlcontextReadJson {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("123456").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")
    val sqlContext=new SQLContext(sc)
    import org.apache.spark.sql.functions._
    import sqlContext.implicits._
    sqlContext.read.json("E:\\08_data\\zx.txt").show(false)
  }
}
