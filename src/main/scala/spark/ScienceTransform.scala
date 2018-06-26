package spark
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
/**
  *select($"name",$"sum(num1)".cast(LongType))转换科学计数法为long
  *
  * @author zhanglingjun@17paipai.cn 861724927
  *         2018/6/26 17:42
  */
object ScienceTransform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("123456").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")
    val sqlContext=new SQLContext(sc)
    import sqlContext.implicits._
    import org.apache.spark.sql.functions._
    import org.apache.spark.sql.SaveMode
    import org.apache.spark.sql._
    val name1="name,num1"
    val a=sc.parallelize(List(
      (1,"1488913200"),
      (1,"1420138800"),
      (1,"1110222000"),
      (1,"1357066800")
    )).toDF(name1.split(","): _*)
    import org.apache.spark.sql.types.{IntegerType, LongType}
    a.groupBy("name").agg(sum("num1"))
      .select($"name",$"sum(num1)".cast(LongType))
      .show(false)
  }
}
