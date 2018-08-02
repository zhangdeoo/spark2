package com.pp
import java.text.SimpleDateFormat

import jdk.nashorn.internal.runtime.regexp.joni.constants.StringType
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
/**
  *
  *
  * @author zhanglingjun@17paipai.cn 861724927
  *         2018/7/19 15:20
  */
object DF {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("123456").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)
    import org.apache.spark.{SparkConf, SparkContext}

    sc.setLogLevel("ERROR")
    val sqlContext=new SQLContext(sc)
    import sqlContext.implicits._
    import org.apache.spark.sql.functions._
    import org.apache.spark.sql.SaveMode
    import org.apache.spark.sql._
    val name1="userid,time,rank"
    val a=sc.parallelize(List(
      (76990079, "1",1),
    (76990079, "2",2),
    (193041942, "3",3),
    (193041942, "4",4)
    )).toDF(name1.split(","): _*)
//    a.withColumn("t",unix_timestamp($"time","yyyy/MM/dd HH:mm:ss")).show()
    a.groupBy("userid").agg(max("rank"),first("time")).show()
  }
}
