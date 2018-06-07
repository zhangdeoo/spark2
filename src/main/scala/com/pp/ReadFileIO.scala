package com.pp

import java.util

import scala.io.Source

/**
  * Created by Administrator on 2018/5/31.
  */
object ReadFileIO {
  val mapscope_truncate = new util.HashMap[String, Int]()

  def main(args: Array[String]): Unit = {
    val fileName = "E:\\01_workspcace\\02_IDEAworkspace\\data-audit\\data-audit-spark\\src\\main\\resources\\file\\ip_20170905115730.txt"
    val source = Source.fromFile(fileName)
    val lines = source.getLines
    //
    var first_china = true
    var continuous_china = true
    var index = 0
    var contins_china = 0
    for (line <- lines) {
      val arr = line.split(",", -1)
      val startIpNum = arr(0)
      val stopIpNum = arr(1)
      if (line.contains("中国")) {
        if (first_china) {
          first_china = false
          mapscope_truncate.put(startIpNum, index)
          mapscope_truncate.put(stopIpNum, index)
        } else {
          if (continuous_china) {
            mapscope_truncate.put(stopIpNum, index)
          } else {
            mapscope_truncate.put(startIpNum + "#discontinuity", index)
            mapscope_truncate.put(stopIpNum, index)
          }
        }
        continuous_china = true
      } else {
        continuous_china = false
      }
      //索引号自增
      index += 1
    }
    println(mapscope_truncate.size())
    println(mapscope_truncate)
    source.close; //记得要关闭source
  }

}
