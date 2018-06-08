package base.scala

/**
  * Created by Administrator on 2018/6/7.
  */
class ScalaClassAndObject1 {

}

//1.主构造器在类名后,参数会被声明字段,若参数没有使用var或者val声明，则会被声明称私有字段
//2.实例化类的时候,类中的语句会被执行:println("person")
class Person1(name: String, var age: Int) {
  println("person1")

  var gender: String = _

  def show(): Unit = {
    println("show.." + name)
  }

  //次构造器必须调用主构造器,参数不能使用var
  def this(name: String, age: Int, gender: String) {
    this(name, age)
    this.gender = gender
  }
}
