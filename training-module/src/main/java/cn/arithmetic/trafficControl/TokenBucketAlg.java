package cn.arithmetic.trafficControl;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @Author:chendong
 * @Date:2018/11/13
 */
@Slf4j
@Component
public class TokenBucketAlg {

    private static RateLimiter rateLimiter = null; // 限流器
    private static Double rateActual = null; // 限流器当前限速

    /**
     * @PostConstruct
     *
     * The PostConstruct annotation is used on a method that needs to be executed
     * after dependency injection（依赖注入 -> @Autowired）is done to perform any initialization.
     * 也就是说 只有启动 Spring Container，加载 Spring 上下文之后，该注解的方法才会生效；
     * 其他类 依赖注入该类之前，该类 @PostConstruct 注解上的方法会被执行；
     * 使用该注解的方法 相当于在 Spring 配置文件中 配置 bean 的 <init-method> 参数;
     * 也等同于 实现 InitializingBean 接口，重写 afterPropertiesSet() 方法；
     */
//    @PostConstruct
//    private void init(){
//        rateLimiter = RateLimiter.create(10.0);
//        rateActual = 30.0;
//    }

    static {
        rateLimiter = RateLimiter.create(5.0);
        rateActual = 5.0;
    }


    @Test
//    @Ignore
    public void rateLimiterTest() throws ExecutionException, InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(20);

        /**
         * StopWatch : 测试 某段程序执行时间和所占时间比
         */
        StopWatch totalUseTime = new StopWatch();
        totalUseTime.start("demo rateLimiter...");

        for (int j = 0; j < 12; j++) {
            int finalJ = j;
            Future future = threadPool.submit(() -> trafficControl(finalJ));
            // future.get() 该方法会阻塞等待子线程执行；
//            System.out.println("rate value: " + future.get());
        }
        threadPool.shutdown();
        trafficControl(10);
        totalUseTime.stop();
        System.out.println(totalUseTime.prettyPrint());

    }

    // when rateLimiter.create(5.0)  (每秒产生 5 个令牌；平均每 200ms 产生一个) ; j < 5 (同时开启 5 个线程);
    // rateLimiter.tryAcquire(500, TimeUnit.MILLISECONDS) (每个请求取一个令牌，容忍等待时间 500ms)

    /**
     *  <------------- output ----------------->
     *
     * current cycle: 0
     * current cycle: 1
     * thread-pool-id 12 : start!
     * thread-pool-id 11 : start!
     * current cycle: 2
     * thread-pool-id 13 : start!
     * current cycle: 3
     * thread-pool-id 14 : start!
     * current cycle: 10
     * thread-pool-id 1 : start!
     * thread-pool-id 11 : done!
     * 2018-11-15 12:02:51.074 [pool-1-thread-3] [c.a.trafficControl.TokenBucketAlg] [WARN ] [95] - 接口请求参数异常, errorMessage:访问频率过快，请稍后重试
     * thread-pool-id 13 : done!
     * 2018-11-15 12:02:51.074 [pool-1-thread-2] [c.a.trafficControl.TokenBucketAlg] [WARN ] [95] - 接口请求参数异常, errorMessage:访问频率过快，请稍后重试
     * thread-pool-id 12 : done!
     * thread-pool-id 1 : done!
     *
     *
     */

    // when rateLimiter.create(5.0)  (每秒产生 5 个令牌；平均每 200ms 产生一个) ; j < 12 (同时开启 12 个线程);
    // rateLimiter.acquire() < 2   (每个请求取 1 premit & 容忍时间：waitTime < 2s, 大于 2s 则拒绝请求)

    /**
     * <------------- output ----------------->
     *
     * current cycle: 0
     * thread-pool-id 11 : start!
     * thread-pool-id 11 waitTime: 0.0
     * current cycle: 1
     * thread-pool-id 11 : done!
     * thread-pool-id 12 : start!
     * current cycle: 2
     * thread-pool-id 13 : start!
     * current cycle: 3
     * thread-pool-id 14 : start!
     * current cycle: 4
     * thread-pool-id 15 : start!
     * current cycle: 5
     * thread-pool-id 16 : start!
     * current cycle: 6
     * thread-pool-id 17 : start!
     * current cycle: 7
     * thread-pool-id 18 : start!
     * current cycle: 8
     * thread-pool-id 19 : start!
     * current cycle: 9
     * thread-pool-id 20 : start!
     * current cycle: 10
     * thread-pool-id 21 : start!
     * current cycle: 11
     * thread-pool-id 22 : start!
     * current cycle: 10
     * thread-pool-id 1 : start!
     * thread-pool-id 12 waitTime: 0.07092
     * thread-pool-id 12 : done!
     * thread-pool-id 13 waitTime: 0.270427
     * thread-pool-id 13 : done!
     * thread-pool-id 14 waitTime: 0.470076
     * thread-pool-id 14 : done!
     * thread-pool-id 15 waitTime: 0.669905
     * thread-pool-id 15 : done!
     * thread-pool-id 16 waitTime: 0.869767
     * thread-pool-id 16 : done!
     * thread-pool-id 17 waitTime: 1.069583
     * thread-pool-id 17 : done!
     * thread-pool-id 18 waitTime: 1.269506
     * thread-pool-id 18 : done!
     * thread-pool-id 19 waitTime: 1.46936
     * thread-pool-id 19 : done!
     * thread-pool-id 20 waitTime: 1.669187
     * thread-pool-id 20 : done!
     * thread-pool-id 21 waitTime: 1.869005
     * thread-pool-id 21 : done!
     * thread-pool-id 22 waitTime: 2.068614
     * 2018-11-15 13:43:48.452 [pool-1-thread-12] [c.a.trafficControl.TokenBucketAlg] [WARN ] [126] - 接口请求参数异常, errorMessage:访问频次过快，请稍后重试
     * thread-pool-id 22 : done!
     * thread-pool-id 1 waitTime: 2.268181
     * 2018-11-15 13:43:48.647 [main] [c.a.trafficControl.TokenBucketAlg] [WARN ] [126] - 接口请求参数异常, errorMessage:访问频次过快，请稍后重试
     * thread-pool-id 1 : done!
     * StopWatch '': running time (millis) = 2402
     * -----------------------------------------
     * ms     %     Task name
     * -----------------------------------------
     * 02402  100%  demo rateLimiter...
     *
     */


    private double setRateLimiter() {
        return 5.0;
    }

    public double trafficControl(int i) throws InterruptedException {
        Double rateExpected = setRateLimiter();

        try {

            System.out.println("current cycle: " + i);
            System.out.println("thread-pool-id " + Thread.currentThread().getId() + " : start!");
            Preconditions.checkState(rateLimiter != null, "[RateLimiter] is null");
            Preconditions.checkState(rateActual.equals(rateExpected), "[RateLimiter] rate is changed");

        } catch (Exception e) {
            Double rateActualOld = rateActual;
            rateActual = rateExpected;
            rateLimiter.setRate(rateExpected);
            log.info(new StringBuilder("[RateLimiter] init/reset:").append("\n")
                    .append("reason - ").append(e.getMessage()).append("\n")
                    .append("rateActual - ").append(rateActualOld).append("\n")
                    .append("rateExpected - ").append(rateExpected)
                    .toString());
        }

        try {
//            Preconditions.checkState(rateLimiter.tryAcquire(500, TimeUnit.MILLISECONDS), "访问频率过快，请稍后重试");
            double waitTime = rateLimiter.acquire();
            System.out.println("thread-pool-id " + Thread.currentThread().getId() + " waitTime: " + waitTime);
            if(waitTime > 2)
                throw new Exception("访问频次过快，请稍后重试");
        } catch (Exception e) {
            log.warn("接口请求参数异常, errorMessage:{}", e.getMessage());
        }
        System.out.println("thread-pool-id " + Thread.currentThread().getId() + " : done!");
        return rateLimiter.getRate();
    }


    /**
     *
     * example 0
     * @throws InterruptedException
     */
    @Test
    public void trainExampleZero() throws InterruptedException {
        // we request 2 permit per seconds
        RateLimiter lt = RateLimiter.create(2);
        System.out.println("Acquired one time: " + lt.acquire());
        System.out.println("Acquired two time: " + lt.acquire());
        System.out.println("Acquired third time: " + lt.acquire());
    }

    // <---------------------------------->
    /*
     *  the output is:
     *      Acquired one time: 0.0
     *      Acquired two time: 0.498175
     *      Acquired third time: 0.494781
     */


    /**
     *
     * example 1
     * @throws InterruptedException
     */
    @Test
    public void trainExampleOne() throws InterruptedException {
        // we request 2 permit per seconds
        RateLimiter lt = RateLimiter.create(2);
        System.out.println("Acquired one: " + lt.tryAcquire());
        System.out.println("Acquired two: " + lt.tryAcquire());
        System.out.println("Acquired third: " + lt.tryAcquire());
    }

    // <---------------------------------->
    /*
     *  the output is:
     *      Acquired one: true
     *      Acquired two: false
     *      Acquired third: false
     */
    // <---------------------------------->


    /**
     *
     * example 2
     * From the Guava docs:
     * The returned RateLimiter ensures that on average no more than permitsPerSecond are issued during any given second,
     * with sustained requests being smoothly spread over each second.
     *
     * @throws InterruptedException
     */
    @Test
    public void trainExampleTwo() throws InterruptedException {
        // we request 2 permit per seconds
        RateLimiter lt = RateLimiter.create(2);
        System.out.println("Acquired one: " + lt.tryAcquire());
        System.out.println("Acquired two: " + lt.tryAcquire());

        // waiting 1/2 second we will be able to get the second permit
        Thread.sleep(500);
        System.out.println("Acquired third: " + lt.tryAcquire());
    }

    // <---------------------------------->
    /*
     *  the output is:
     *      Acquired one: true
     *      Acquired two: false
     *      Acquired third: true
     */
    // <---------------------------------->


    /**
     *
     * example 3
     * @throws InterruptedException
     */
    public void trainExampleThird() throws InterruptedException {
        RateLimiter lt = RateLimiter.create(2);

        // trying to acquire 12 permits
        System.out.println("Acquired one: " + lt.tryAcquire(12));

        System.out.println("Acquired two: " + lt.tryAcquire());
    }

    // <---------------------------------->
    /*
     *  the output is:
     *      Acquired one true
     *      Acquired two false
     */
    // <---------------------------------->

    /**
     *
     * example 4
     * The behaviour of the second example is expected too because to successfully get the single permit on the second "Acquire" we need to wait around 6 seconds (= 12 / 2)
     *
     * Trying to acquire the last permit waiting less than 6 seconds would fail, that is why lt.tryAcquire() in example 3 returns false.
     * @throws InterruptedException
     */
    public void trainExampleFour() throws InterruptedException {
        RateLimiter lt = RateLimiter.create(2);

        // trying to acquire 12 permits
        System.out.println("Acquired one: " + lt.tryAcquire(12));

        // waiting 12 / 2 seconds in order to be able to get the second permit
        Thread.sleep(6000);
        System.out.println("Acquired two: " + lt.tryAcquire());
    }

    // <---------------------------------->
    /*
     *  the output is:
     *      Acquired one true
     *      Acquired two true
     */
    // <---------------------------------->


    @Test
    public void trainExampleFive() {
        RateLimiter limiter = RateLimiter.create(12);
        System.out.println(limiter.tryAcquire());
        System.out.println(limiter.tryAcquire(500, MILLISECONDS));
        System.out.println(limiter.tryAcquire(500, MILLISECONDS));
        System.out.println(limiter.tryAcquire(500, MILLISECONDS));
        System.out.println(limiter.tryAcquire(500, MILLISECONDS));
    }

    // <---------------------------------->
    /*
     *  the output is:
     *     true
     *     true
     *     true
     *     true
     *     true
     */
    // <---------------------------------->

}
