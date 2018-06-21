import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.*;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import java.util.concurrent.TimeUnit;
/**
 * @author zhanglingjun@17paipai.cn 861724927
 * 2018/6/20 10:18
 */
public class DistributedClient {
    private static final String connectString = "192.168.40.130:2181,192.168.40.131:2181,192.168.40.132:2181";
    private static final String boot_servers = "192.168.40.130:9092,192.168.40.131:9092,192.168.40.132:9092";
    private static final int sessionTimeout = 2000;
    private static final String parentNode = "/brokers/topics";
    private static KafkaConsumer<Integer, String> consumer=null;
    // 注意:加volatile
    private volatile List<String> topicList;
    private  ZooKeeper zk = null;

    /**
     * 创建到zk的客户端连接
     *
     * @throws Exception
     */
    public void getConnect() throws Exception {
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
                System.out.println(event.getType() + "---" + event.getPath());
                try {
                    Thread.sleep(3000);
                    getTopicList();
                } catch (Exception e) {
                }
            }
        });
    }
    /**
     * 获取topic列表
     *
     * @throws Exception
     */
    public void getTopicList() throws Exception {

        List<String> children = zk.getChildren(parentNode, true);
        // 先创建一个局部的list来存topic信息
        List<String> list = new ArrayList<String>();
        for (String child : children) {
            list.add(child);
        }
        list.remove("__consumer_offsets");
        // 把list赋值给成员变量topicList，已提供给各业务线程使用
        topicList = list;
        //打印topic列表
        System.out.println(topicList);
        getConsumer(topicList);
    }

    public static void getConsumer(List<String> topicList){
//    public static void getConsumer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", boot_servers);
        props.put("group.id", "getvaluebytimestamp");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<Integer, String> consumer_new = new KafkaConsumer(props);
        consumer_new.subscribe(topicList, new ConsumerRebalanceListener() {
//        consumer_new.subscribe(Collections.singletonList("m8"), new ConsumerRebalanceListener() {

            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            }
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            }
        });
        System.out.println("getConsumer.....");
        consumer=consumer_new;
    }

    /**
     * 业务功能
     *
     * @throws InterruptedException
     */
    public void handleBussiness() {

        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(100);
            for (ConsumerRecord<Integer, String> record : records) {
                System.out.printf("topic = %s, offset = %s, value = %s", record.topic(), record.offset(), record.value());
                System.out.println();
            }
            try {
                TimeUnit.MICROSECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        // 获取zk连接
        DistributedClient client = new DistributedClient();
        client.getConnect();
//        getConsumer();
        // 获取topic的子节点信息（并监听），从中获取列表
        client.getTopicList();
        // 业务线程启动
        client.handleBussiness();

    }

}
