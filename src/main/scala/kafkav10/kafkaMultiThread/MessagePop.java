package kafkav10.kafkaMultiThread;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import net.sf.json.JSONArray;
/**
 * @author zhanglingjun@17paipai.cn 861724927
 * 2018/6/26 20:53
 */
public class MessagePop {
    private String topic;
    private int numThreads = 3;
    public  ConsumerConnector consumer;
    private ExecutorService executor;
    private BlockingQueue<String> _message = new LinkedBlockingQueue<String>();

    public JSONArray getMessage(){
        JSONArray msg = null;
        if (!_message.isEmpty()) {
            msg = JSONArray.fromObject(_message.poll());
        }
        return msg;
    }

    public void setTopic(String Topic){
        topic = Topic;
    }
    @SuppressWarnings("unchecked")
    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        executor = Executors.newFixedThreadPool(numThreads);
        for (@SuppressWarnings("rawtypes") final KafkaStream stream : streams) {
            executor.submit(new ConsumerRunnable(stream, numThreads));
        }
    }

    /**
     * 启用多线程读取数据，并写入到内存队列中
     */
    class ConsumerRunnable implements Runnable {
        private KafkaStream<byte[], byte[]> m_stream;

        public ConsumerRunnable(KafkaStream<byte[], byte[]> a_stream, int a_threadNumber) {
            this.m_stream = a_stream;
        }

        public void run() {
            ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
            while (it.hasNext()) {
                _message.add(new String(it.next().message()));
            }
        }

    }
}
