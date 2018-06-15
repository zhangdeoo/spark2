package com.pp

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 测试spark的split拆分后会返回数组
  * substring_index会返回一个新列
  * 会返回一个数组形式的新列
  * +------+-------------+--------+
  * |     1|        北京_朝阳|[北京, 朝阳]|
  * |     2|        山东_济南|[山东, 济南]|
  * |     3|        山西_太原|[山西, 太原]|
  */
object data_frame_function_split {
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
    df.withColumn("p_c", split($"province_city", "_"))
      .withColumn("province", substring_index($"province_city", "_", 1)) //1代表从左边数要1个
      .withColumn("city", substring_index($"province_city", "_", -1)) //-1从右边数要1个
      .withColumn("region", substring_index(substring_index($"province_city", "_", -2),"_",-1)) //-2从右边数要2个
      .show()

  }
}
