package cn.arithmetic.trafficControl;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author:chendong
 * @Date:2018/11/13
 */
public class FutureMethodTest {
    final String[] result = new String[1];

    private Future<Integer> future_callable(ExecutorService executorService) {
        Callable<Integer> callable = () -> {
            System.out.println("future_callable...");
            return 1;
        };
        return executorService.submit(callable);
    }

    private Future getFuture_runnable(ExecutorService executorService) {
        Runnable runnable = () -> {
            System.out.println("getFuture_runnable...");
        };
        return executorService.submit(runnable);
    }

    private Future<String[]> future_r(ExecutorService executorService) {
        Runnable r = new Runnable() {
            public void run() {
                result[0] = "future_r...";
            }
        };
        return executorService.submit(r, result);
    }

    @Test
    public void futureMethodTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 1.
        Future<String[]> future_r = future_r(executorService);

        // 2.
        Future<Integer> future_callable = future_callable(executorService);

        // 3.
        Future future_runnable = getFuture_runnable(executorService);


        // 4.
        Future future_tasks = executorService.submit(new Tasks());
        // 5.
        Future<Integer> future_callableTask = executorService.submit(new CallableTask());

        /**
         * 测试代码的时候，要手动关闭掉线程池，不然 线程执行完之后，idea 还是会在 running 的状态；
         */
        executorService.shutdown();

        try {
            /**
             * boolean isDone();
             * Returns {@code true} if this task completed.
             * 该方法不会阻塞等待子线程完毕；main线程执行到该方法的时候，子线程还没有执行完，则返回 false； 子线程已经执行完毕，则返回 true；
             */
            System.out.println(future_runnable.isDone());

            /**
             * V get() throws InterruptedException, ExecutionException;
             * Waits if necessary for the computation to complete, and then
             * retrieves its result.
             * 该方法会阻塞等待子线程执行；
             * main线程执行到该方法的时候，若子线程还没有执行完，则阻塞主线程，等待子线程执行完，返回执行结果；
             */
            System.out.println("result: " + future_runnable.get());
//            System.out.println("result[0]: " + future.get()[0]);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (
                ExecutionException e) {
            e.printStackTrace();
        }

    }

    class Tasks implements Runnable {

        @Override
        public void run() {
            result[0] = "future_tasks...";
        }
    }
}

class CallableTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int i = 0;
        System.out.println("future_callableTask...");
        return i;
    }
}
