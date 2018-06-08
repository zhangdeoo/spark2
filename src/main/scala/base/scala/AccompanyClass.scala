package base.scala

/**
  * scala没有静态的修饰符，但object下的成员都是静态的 ,若有同名的class,这其作为它的伴生类。
  * 在object中一般可以为伴生类做一些初始化等操作,如我们常常使用的val array=Array(1,2,3)  (ps:其使用了apply方法)
  */
object AccompanyClass {
  private var age = 0

  def Age = {
    age += 1
    age
  }
}

class AccompanyClass {
  var age1 = AccompanyClass.age //AccompanyClass.age是object Dog的私有字段。
}