package com.pp

import org.apache.spark.scheduler._

/**
  * Created by Administrator on 2018/5/30.
  */
object testSparkListen extends SparkListener {
  var num = 0

  override def onStageCompleted(stageCompleted: SparkListenerStageCompleted): Unit = {
    num += 1;
    println(num)
  }

  override def onStageSubmitted(stageSubmitted: SparkListenerStageSubmitted): Unit = {
    num += 1; println(num)
  }

  override def onTaskStart(taskStart: SparkListenerTaskStart): Unit = {
    num += 1; println(num)
  }

  override def onTaskGettingResult(taskGettingResult: SparkListenerTaskGettingResult): Unit = {
    num += 1; println(num)
  }

  override def onTaskEnd(taskEnd: SparkListenerTaskEnd): Unit = {
    num += 1; println(num)
  }

  override def onJobStart(jobStart: SparkListenerJobStart): Unit = {
    num += 1; println(num)
  }

  override def onJobEnd(jobEnd: SparkListenerJobEnd): Unit = {
    num += 1; println(num)
  }

  override def onEnvironmentUpdate(environmentUpdate: SparkListenerEnvironmentUpdate): Unit = {
    num += 1; println(num)
  }

  override def onBlockManagerAdded(blockManagerAdded: SparkListenerBlockManagerAdded): Unit = {
    num += 1; println(num)
  }

  override def onBlockManagerRemoved(
                                      blockManagerRemoved: SparkListenerBlockManagerRemoved): Unit = {
    num += 1; println(num)
  }

  override def onUnpersistRDD(unpersistRDD: SparkListenerUnpersistRDD): Unit = {
    num += 1; println(num)
  }

  override def onApplicationStart(applicationStart: SparkListenerApplicationStart): Unit = {
    num += 1; println(num)
  }

  override def onApplicationEnd(applicationEnd: SparkListenerApplicationEnd): Unit = {
    num += 1; println(num)
  }

  override def onExecutorMetricsUpdate(
                                        executorMetricsUpdate: SparkListenerExecutorMetricsUpdate): Unit = {
    num += 1; println(num)
  }

  override def onExecutorAdded(executorAdded: SparkListenerExecutorAdded): Unit = {
    num += 1; println(num)
  }

  override def onExecutorRemoved(executorRemoved: SparkListenerExecutorRemoved): Unit = {
    num += 1; println(num)
  }

  override def onBlockUpdated(blockUpdated: SparkListenerBlockUpdated): Unit = {
    num += 1; println(num)
  }

  override def onOtherEvent(event: SparkListenerEvent): Unit = {
    num += 1; println(num)
  }

  def main(args: Array[String]): Unit = {
    println("test")
  }
}
