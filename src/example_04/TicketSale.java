package example_04;

/**
 * 通知/等待机制，可能出现虚假唤醒问题（线程数>2）
 * @author xunle
 * @date 2021/10/10 15:34
 */
public class TicketSale {
    private static final Object lock = new Object();
    private static int number = 0;

    public static void main(String[] args) throws InterruptedException{
        new Thread(()->{
            synchronized (lock) {
                while(number < 100) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "窗口卖出第" + (++number) + "张票");
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notify();
                }
            }
        }, "A").start();
        new Thread(()->{
            synchronized (lock) {
                while (number < 100) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "窗口卖出第" + (++number) + "张票");
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notify();
                }
            }
        }, "B").start();
    }
}
