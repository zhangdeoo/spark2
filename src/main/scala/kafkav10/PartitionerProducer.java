package kafkav10;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * 按照key进行hash分区存入kafka
 */
public class PartitionerProducer {
    public static void main(String[] args) {
        producerData();
    }
    private static void producerData() {
        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", "192.168.40.130:9092,192.168.40.131:9092,192.168.40.132:9092");
        props.put("partitioner.class", "kafkav10.CustomerPartitioner");
        Producer<String, String> producer = new Producer<String, String>(new ProducerConfig(props));
        String topic = "testtimepartion3";
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        for (int i = 0; i < list.size(); i++) {
            String k = "key" + i;
            String v = new String(list.get(i));
            producer.send(new KeyedMessage<String, String>(topic, k, v));

            if (i == (list.size() - 1)) {
                return;
            }
        }
        producer.close();
    }
}
