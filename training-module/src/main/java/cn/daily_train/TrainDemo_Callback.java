package cn.daily_train;

/**
 * @Author:chendong
 * @Date:2018/11/13
 */

/**
 * What is a callback method in java? Term seems to be used loosely
 * <p>
 * A callback is a piece of code that you pass as an argument to some other code so that it executes it.
 * Since Java doesn't yet support function pointers, they are implemented as Command objects. Something like
 */
public class TrainDemo_Callback {

    public static void main(String[] args) {

        // 使用 lamda 表达式：
        new TrainDemo_Callback().doWork(() -> {
            System.out.println("callback called");  // 传入要实现的方法
        });

        // 直接使用匿名内部类：
        new TrainDemo_Callback().doWork(new Callback() { // implementing class
            @Override
            public void call() {
                System.out.println("callback called");
            }
        });
    }

    public void doWork(Callback callback) {
        System.out.println("doing work");
        callback.call();
    }

    public interface Callback {
        void call();
    }
}
