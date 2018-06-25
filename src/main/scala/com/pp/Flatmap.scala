package com.pp

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  */
object Flatmap {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("123456").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")
    val sqlContext = new SQLContext(sc)
    import org.apache.spark.sql.functions._
    import sqlContext.implicits._
    val fileName = "c:\\newquery.txt"
    sc.textFile(fileName).map(_.replaceAll("\t",","))
      .filter(_.split(",",-1).length>2)
      .map(
      ele=>{
        val arr=ele.split(",",-1)
        val uid=ele.indexOf(",",arr(0).length+1)
        uid+2
        (arr(0),ele.substring(uid+2,ele.length))
      }
    ).flatMapValues(_.split(",",-1))
      .map(str=>{
        val arr=str._2.split(":",-1)
        val userid=str._1
        val bigclass=arr(1)
        val smallclass=arr(2)
        val num=arr(3)
        (userid,bigclass,smallclass,num)
      }).toDF("userid","bigclass","smallclass","num")
      .show()

  }
}
