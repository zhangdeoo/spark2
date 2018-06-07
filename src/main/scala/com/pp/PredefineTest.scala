package com.pp

/**
  * classOf、isInstanceOf、asInstanceOf三个预定义方法分析
  * Created by Administrator on 2018/5/29.
  */
class Person

class Student extends Person

object PredefineTest {
  def main(args: Array[String]): Unit = {
    val p: Person = new Student
    //isInstanceOf判定是否为Person类或者其子类,如果返回为true,则可以使用asInstanceOf进行向上或者向下转型。
    println(p.isInstanceOf[Person])
    println(p.asInstanceOf[Person])
    //isInstanceOf无法精准的判定到底是本类还是子类。如果需要精准的判定则需要使用getclass和classOf
    println(p.getClass == classOf[Person])
    println(p.getClass == classOf[Student])


  }
}
