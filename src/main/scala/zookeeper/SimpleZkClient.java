package zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author zhanglingjun@17paipai.cn 861724927
 * 2018/6/21 10:04
 */
public class SimpleZkClient {
    private static final String connectString = "192.168.40.130:2181,192.168.40.131:2181,192.168.40.132:2181";
    private static final int sessionTimeout = 2000;

    ZooKeeper zkClient = null;
    public void init() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
                System.out.println(event.getType() + "---" + event.getPath());
                try {
                    zkClient.getChildren("/", true);
                } catch (Exception e) {
                }
            }
        });
    }
    // 获取子节点
    public void getChildren() throws Exception {
        List<String> children = zkClient.getChildren("/brokers/topics", true);
        for (String child : children) {
            System.out.println(child);
        }
    }
    public void testExist() throws Exception{
        Stat stat = zkClient.exists("/eclipse", false);
        System.out.println(stat==null?"not exist":"exist");
    }
    public static void main(String[] args) throws Exception {
        SimpleZkClient siz=new SimpleZkClient();
        siz.init();
        siz.testExist();
        siz.getChildren();
    }
}
