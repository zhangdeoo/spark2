package randomJson

import java.util.Random

import org.json.JSONObject

/**
  *
  *
  * @author zhanglingjun@17paipai.cn 861724927
  *         2018/7/25 14:36
  */
object RandomJSON {
  def main(args: Array[String]): Unit = {
    val random=new Random()
    val json = new JSONObject()
    var start=1532509200
    val end=1532509200+3600
    while(start<end){

      json.put("loginfo","loginfo-"+random.nextInt(60000))
      json.put("logerror","logerror"+random.nextInt(60000))
      json.put("timestmap",start)
      json.put("user_id",random.nextInt(5000))
      println("{\"index\":{\"_index\":\"love\"}}")
      println(json.toString)
      start=start+1
    }



  }
}
