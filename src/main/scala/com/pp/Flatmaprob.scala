package com.pp

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  */
object Flatmaprob {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("123456").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")
    val sqlContext = new SQLContext(sc)
    import org.apache.spark.sql.functions._
    import sqlContext.implicits._
    val fileName = "./newquery.txt"
//    val fileName = "c:\\dajie13*"
    sc.textFile(fileName).map(_.replaceAll("\t",","))
      .map(ele=>{
        val arr=ele.split(",",-1)
        val uid=ele.indexOf(",",arr(0).length)
        (arr(0),ele.substring(uid+1,ele.length))
      })
      .flatMapValues(_.split(",",-1))
//      .filter(_._2.split(":",-1).length==1)
//      .foreach(println(_))
      .map(str=>{
        val arr=str._2.split(":",-1)
        val userid=str._1
        val bigclass=arr(0)
        val smallclass=arr(1)
        val num=arr(2)
        (userid,bigclass,smallclass,num)
      }).toDF("userid","bigclass","smallclass","num")
      .show(100)

  }
}
