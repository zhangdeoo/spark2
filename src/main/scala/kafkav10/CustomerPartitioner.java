package kafkav10;
import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

import java.util.Random;

/**
 *
 *
 * @Note 先 Hash 再取模，得到分区索引
 */
public class CustomerPartitioner implements Partitioner {

    public CustomerPartitioner(VerifiableProperties props) {
    }

    public int partition(Object key, int numPartitions) {
        int partition = 0;
        String k = (String) key;
        partition = Math.abs(k.hashCode()) % numPartitions;
        int part=new Random().nextInt(3);
        System.out.println(part);
        return part;
    }

}