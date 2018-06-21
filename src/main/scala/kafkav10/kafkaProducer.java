package kafkav10;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class kafkaProducer extends Thread {
    private String topic;

    public kafkaProducer(String topic) {
        super();
        this.topic = topic;
    }

    public static void main(String[] args) {

        new kafkaProducer(config.topic).start();
    }

    @Override
    public void run() {
//        Producer producer = createProducer();
        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", "192.168.40.130:9092,192.168.40.131:9092,192.168.40.132:9092");
//        props.put("partitioner.class", "kafkav10.CustomerPartitioner");
        Producer<String, String> producer = new Producer<String, String>(new ProducerConfig(props));
        int i = 0;
        while (true) {
//            new ProducerRecord<String, String>("my-topic", null, System.currentTimeMillis(), "key", "value");
            producer.send(new KeyedMessage<String, String>("m8", "message:" + i++));
            System.out.println(i+"、发送成功！");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Producer createProducer() {
        Properties properties = new Properties();
//        properties.put("zk.connect", config.zookeeperConnect);
//        properties.put("serializer.class", StringEncoder.class.getName());
//        properties.put("metadata.broker.list", config.kafkaBrokers);
//        properties.put("partitioner.class", "kafkav10.CustomerPartitioner");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("metadata.broker.list", "192.168.40.130:9092,192.168.40.131:9092,192.168.40.132:9092");
        properties.put("partitioner.class", "kafkav10.CustomerPartitioner");
        return new Producer<Integer, String>(new ProducerConfig(properties));
    }
}