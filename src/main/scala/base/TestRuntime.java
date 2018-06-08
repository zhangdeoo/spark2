package base;

/**
 * Runtime在每个应用程序中都只存在一个单例。
 * 用于获取系统信息。还可以用于执行外部命令
 */
public class TestRuntime {
    public static void main(String[] args) {
        //addShutdownHook解释主程序中打印出的Runtime的对象的信息和钩子线程中打印出的Runtime的信息是一致的。
//        1.虚拟机自己结束时会调用，比如程序运行完成，或者用户跟虚拟机交互之后，虚拟机接收到退出信号，自己结束
//
//        2.外部力量结束虚拟机时，不能保证钩子线程启动，比如在windows平台中，启动任务管理器，直接将这个Java虚拟机关闭进程，这种情况下不能保证钩子线程一定会被调用
//
//                上边的1和2都可以通过稍微修改下最上边的程序验证
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("I am still alive!");
                try {
                    Thread.currentThread().sleep(1000);
                    System.out.println(Runtime.getRuntime().toString());
                    System.out.println(Runtime.getRuntime().hashCode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Runtime.getRuntime().toString());
        System.out.println(Runtime.getRuntime().hashCode());
        System.out.println("I am exit!");


    }

    /*
    //halt不会执行钩子程序
     */
    public static void halt() {
        Runtime.getRuntime().halt(1);
    }

    /*
 * 获取当前jvm的内存信息,返回的值是 字节为单位
 * */
    public static void getFreeMemory() {
        //获取可用内存
        long value = Runtime.getRuntime().freeMemory();
        System.out.println("可用内存为:" + value / 1024 / 1024 + "mb");
        //获取jvm的总数量，该值会不断的变化
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("全部内存为:" + totalMemory / 1024 / 1024 + "mb");
        //获取jvm 可以最大使用的内存数量，如果没有被限制 返回 Long.MAX_VALUE;
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("可用最大内存为:" + maxMemory / 1024 / 1024 + "mb");
    }

    /*
 * 获取jvm可用的处理器核心的数量
 * */
    public static void getAvailableProcessors() {
        int value = Runtime.getRuntime().availableProcessors();
        System.out.println(value);
    }
}
