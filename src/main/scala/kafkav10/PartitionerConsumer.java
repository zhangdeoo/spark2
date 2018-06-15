package kafkav10;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
/**
 *
 * @description: 通过 Kafka 的消费者 API 验证分区入库的消息
 *
 * @author zhanglingjun@17paipai.cn 861724927
 * @date 2018/6/9 11:30
 */
public class PartitionerConsumer {
    public static void main(String[] args) {
        String topic = config.topic;
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(createConsumerConfig());
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
        System.out.println(stream.toString());
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
            MessageAndMetadata<byte[], byte[]> mam = it.next();
            Long offset=mam.offset();
            System.out.println();
            System.out.println("consume: Partition [" + mam.partition() + ",offset:"+offset+"] Message: [" + new String(mam.message())
                    + "] ..");
        }

    }

    private static ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        props.put("group.id", "group1");
        props.put("zookeeper.connect", config.zookeeperConnect);
        props.put("zookeeper.session.timeout.ms", "40000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        return new ConsumerConfig(props);
    }
}
