package base.mulityThread

import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.{ScheduledThreadPoolExecutor, ThreadFactory}

import org.apache.kafka.common.utils.Utils

object testThreadPool {
  def main(args: Array[String]): Unit = {
    new ReentrantLock()
    val executor = new ScheduledThreadPoolExecutor(5)
    executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false)
    executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false)
//    executor.setThreadFactory(new ThreadFactory() {
//      def newThread(runnable: Runnable): Thread =
//        Utils.newThread("thread", runnable, true)
//    })
  }
}
