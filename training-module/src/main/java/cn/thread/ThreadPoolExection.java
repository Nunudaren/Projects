package cn.thread;

import java.util.concurrent.*;

/**
 * @Author:chendong
 * @Date:2018/9/5
 */
public class ThreadPoolExection {

    private final static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(6, 10, 5,
                    TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    public static void main(String[] args) throws Exception {
        Callable<String> call = new Callable<String>() {
            public String call() throws Exception {
                //开始执行耗时操作
                Thread.sleep(1000 * 5);
                return "线程执行完成.";
            }
        };

        try {
            Future<String> future = executor.submit(call);
            // 如果在timeout时间内没有取到就失败返回，而不再阻塞
            String obj = future.get(1000 * 6, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒
            System.out.println("任务成功返回:" + obj);
        } catch (TimeoutException ex) {
            System.out.println("处理超时啦....");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("处理失败.");
            e.printStackTrace();
        } finally {
            // 关闭线程池
            executor.shutdown();
        }
    }

}
