package cn.thread.consumer$producer;

import org.junit.Test;

/**
 * ThreadLocal的Lambda构造方式：withInitial
 * 用ThreadLocal作为容器，当每个线程访问这个 balance 变量时，ThreadLocal会为每个线程提供一份变量，各个线程互不影响。
 * @author winter
 * @date 2018-12-25
 */
public class ThreadLocalLambdaDemo {

    /**
     * 运行入口
     */
    @Test
    public void main() {
//        safeDeposit();
        notSafeDeposit();
    }

    /**
     * 线程安全的存款
     */
    private void safeDeposit() {
        SafeBank bank = new SafeBank();
        Thread thread1 = new Thread(() -> bank.deposit(200), "张成瑶");
        Thread thread2 = new Thread(() -> bank.deposit(200), "马云");
        Thread thread3 = new Thread(() -> bank.deposit(500), "马化腾");
        thread1.start();
        thread2.start();
        thread3.start();
    }

    /**
     * 非线程安全的存款
     */
    private void notSafeDeposit() {
        NotSafeBank bank = new NotSafeBank();
        Thread thread1 = new Thread(() -> bank.deposit(200), "张成瑶");
        Thread thread2 = new Thread(() -> bank.deposit(200), "马云");
        Thread thread3 = new Thread(() -> bank.deposit(500), "马化腾");
        thread1.start();
        thread2.start();
        thread3.start();
    }

}

/**
 * 非线程安全的银行
 */
class NotSafeBank {

    /**
     * 当前余额
     */
    private int balance = 1000;

    /**
     * 存款
     *
     * @param money 存款金额
     */
    public void deposit(int money) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " -> 当前账户余额为：" + this.balance);
        this.balance += money;
        System.out.println(threadName + " -> 存入 " + money + " 后，当前账户余额为：" + this.balance);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 线程安全的银行
 */
class SafeBank {

    /**
     * 当前余额
     */
    private ThreadLocal<Integer> balance = ThreadLocal.withInitial(() -> 1000);

    /**
     * 存款
     *
     * @param money 存款金额
     */
    public void deposit(int money) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " -> 当前账户余额为：" + this.balance.get());
        this.balance.set(this.balance.get() + money);
        System.out.println(threadName + " -> 存入 " + money + " 后，当前账户余额为：" + this.balance.get());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
