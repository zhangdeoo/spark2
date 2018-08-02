package com.pp

import java.io.{File, PrintWriter}
import java.util

import scala.io.Source

/**
  * Created by Administrator on 2018/5/31.
  */
object ReadFileIO {

  def main(args: Array[String]): Unit = {
    val fileName = "C:\\t.txt"
    val source = Source.fromFile(fileName)
    val lines = source.getLines
    val writer = new PrintWriter(new File("C:\\learningScala.txt"))
    for (line <- lines) {
      val linearr=line.split(" ",-1)
      val newline=linearr(0)+"\t"+linearr(1)+""+linearr(2)
      writer.println(newline)
    }

    source.close; //记得要关闭source
  }

}
