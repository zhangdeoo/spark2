package com.pp

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**一行拆成多行
  * 一列拆成多列
  */
object one2multify {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("123456").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")
    val sqlContext = new SQLContext(sc)
    import org.apache.spark.sql.functions._
    import sqlContext.implicits._
    val name1 = "userid,province_city"
    val df = sc.parallelize(List(
      (1, "北京_朝阳_朝阳"),
      (2, "山东_济南_平阴"),
      (3, "江苏_南京_玄武")
    )).toDF(name1.split(","): _*)

    df.withColumn("arraycol",split($"province_city","_"))
      .withColumn("expCol",explode($"arraycol"))//一行变多行
      .select($"arraycol".getItem(0).as("c0"),
      $"arraycol".getItem(1).as("c1"),
      $"arraycol".getItem(2).as("c2"),
      $"userid",
      $"expCol"
      )
      .show()

  }
}
