package base.scala

/**
  * 用于测试编译后是否自动生成get,set方法
  * 结论：
  * 所有的属性无论是否用private修饰。在编译以后的class文件中都是默认private属性。
  * get和set方法的private或者public 由属性的是否被private修饰决定，被修饰的->私有的get和set。不被private修饰->public的get和set
  *
  * private[this] 不会生成任何的get和set方法。只能供本类调用。伴生对象也无法获取此值。
  */
class TestClass {
  val ages: Int = 15
  //变成私有并改名
  //val 类型的变量不能用占位符修饰
  //  val ages1:Int=_
  private val name: String = ""
  var age: Int = _
  private var inist_age: Int = 0
  private var family: String = ""
  private[this] var familyroom: String = ""

  def getage = inist_age

  def setage_=(newvalue: Int) = {
    if (newvalue > inist_age) inist_age = newvalue
  }

}
