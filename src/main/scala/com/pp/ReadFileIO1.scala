package com.pp

import java.util

import scala.io.Source

/**
  * Created by Administrator on 2018/5/31.
  */
object ReadFileIO1 {
  val listscope_truncate: util.ArrayList[String] = new util.ArrayList[String]()

  def main(args: Array[String]): Unit = {
    val fileName = "E:\\01_workspcace\\02_IDEAworkspace\\data-audit\\data-audit-spark\\src\\main\\resources\\file\\ip_20170905115730.txt"
    val source = Source.fromFile(fileName)
    val lines = source.getLines
    //
    var interrupt_boolean = false
    var interrupt_num = 0
    var num = 0
    var contins_china = 0
    for (line <- lines) {
      if (line.contains("中国")) {
        num += 1
        contins_china += 1
      }
      else {
        if (num != 0) {
          println(num)
          num = 0
        }
      }
    }
    println(contins_china)
    source.close; //记得要关闭source
  }

}
