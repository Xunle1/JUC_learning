package example_04;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 定制化通信
 * 线程A操作5次，B操作10次，C操作15次，循环十轮
 * @author xunle
 * @date 2021/10/28 14:36
 */
public class ConditionDemo {

    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    resource.doSomethingA(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    resource.doSomethingB(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    resource.doSomethingC(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
    }


}

class SharedResource{
    //设置标志位
    private int flag = 1;

    private ReentrantLock lock = new ReentrantLock();

    Condition a = lock.newCondition();
    Condition b = lock.newCondition();
    Condition c = lock.newCondition();

    public void doSomethingA(int loop) throws InterruptedException{
        try{
            lock.lock();
            if (flag != 1) {
                a.await();
            }

            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i + ",第" + loop + "轮");
            }

            flag = 2;
            //通知b
            b.signal();
        } finally {
            lock.unlock();
        }
    }

    public void doSomethingB(int loop) throws InterruptedException{
        try{
            lock.lock();
            if (flag != 2) {
                b.await();
            }

            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i + ",第" + loop + "轮");
            }

            flag = 3;
            c.signal();
        } finally {
            lock.unlock();
        }
    }

    public void doSomethingC(int loop) throws InterruptedException{
        try{
            lock.lock();
            if (flag != 3) {
                c.await();
            }

            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "::" + i + ",第" + loop + "轮");
            }

            flag = 1;
            a.signal();
        } finally {
            lock.unlock();
        }
    }
}


