package example_02;

/**
 * 使用synchronized实现卖票
 * @author xunle
 * @date 2021/9/12 14:40
 */
public class TicketSale {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(()-> {
            for (int i = 0; i < 120; i++) {
                ticket.sale();
            }
        }, "A").start();

        new Thread(()-> {
            for (int i = 0; i < 120; i++) {
                ticket.sale();
            }
        }, "B").start();
    }
}

class Ticket{
    private int num = 100;

    public synchronized void sale() {
        if (num > 0) {
            System.out.println(Thread.currentThread().getName() + "：卖出一张票,剩余：" + --num);
            //使用synchronized如果临界区因为I/O或者sleep方法阻塞，那么所有的线程会等待
            if (num == 30) {
                System.out.println(Thread.currentThread().getName() + "执行Sleep");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
