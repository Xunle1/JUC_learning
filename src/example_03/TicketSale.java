package example_03;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xunle
 * @date 2021/10/10 14:47
 */
public class TicketSale {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            while (true) {
                ticket.sale();
            }
        }, "A").start();

        new Thread(() -> {
            while (true) {
                ticket.sale();
            }
        }, "B").start();

    }
}

class Ticket{
    private int num = 200;
    //创建锁对象
    private final ReentrantLock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        //finally保证可以解锁
        try {
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + "：卖出一张票,剩余：" + --num);
            }
        } finally {
            lock.unlock();
        }
    }
}
