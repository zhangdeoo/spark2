package com.pp
import java.io.PrintWriter
import java.io.File
/**
  *
  *
  * @author zhanglingjun@17paipai.cn 861724927
  *         2018/7/13 19:13
  */
object Write {
  def main(args: Array[String]): Unit = {
    val writer = new PrintWriter(new File("learningScala.txt"))
  }
}
