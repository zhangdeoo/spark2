package base.scala

/**
  * Created by Administrator on 2018/6/7.
  */
object ScalaClassAndObject2 {
  def main(args: Array[String]): Unit = {
    val sco2 = new ScalaClassAndObject2()
    println(sco2.age)
  }
}

class ScalaClassAndObject2 {
  var age: Int = _
  private[this] var gender = "male"
  //private[this] 只有该类的this可以使用
}
