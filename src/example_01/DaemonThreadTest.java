package example_01;

/**
 * @author xunle
 * @date 2021/9/11 1:30
 */
public class DaemonThreadTest {
    public static void main(String[] args) {
        Thread userThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "A");
        userThread.start();

        Thread daemonThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "B");
        daemonThread.setDaemon(true);
        daemonThread.start();

        System.out.println(Thread.currentThread().getName()+ " over");
    }
}
