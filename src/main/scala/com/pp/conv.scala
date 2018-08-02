package com.pp

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**进制转换函数
  */
object conv {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("123456").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    sc.setLogLevel("ERROR")
    val sqlContext=new SQLContext(sc)
    import org.apache.spark.sql.functions._
    import sqlContext.implicits._
    val name1="valet_id"
    val a=sc.parallelize(List(
      "4977921651011",
    "1679398233851"
    )).toDF(name1.split(","): _*)
    a.withColumn("er",lpad(org.apache.spark.sql.functions.conv($"valet_id",10,2),64,"0"))
      .withColumn("instance_id",org.apache.spark.sql.functions.conv(substring($"er",29,36),2,10))
      .withColumn("seed_id",org.apache.spark.sql.functions.conv(substring($"er",1,26),2,10))
      .show(false)

    sc.parallelize(List(
      "123456",
      "123456"
    )).toDF(name1.split(","): _*)
      .withColumn("substr",substring($"valet_id",2,2)).show()



  }
}
