package consumer$producer;

import java.util.concurrent.ArrayBlockingQueue;

//使用阻塞队列实现的生产者-消费者模式:
/**
 * 使用非阻塞队列的时候有一个很大问题就是：它不会对当前线程产生阻塞，
 * 那么在面对类似消费者-生产者的模型时，就必须额外地实现同步策略以及线程间唤醒策略，
 * 这个实现起来就非常麻烦。但是有了阻塞队列就不一样了，它会对当前线程产生阻塞，
 * 比如一个线程从一个空的阻塞队列中取元素，此时线程会被阻塞直到阻塞队列中有了元素。
 * 当队列中有元素后，被阻塞的线程会自动被唤醒（不需要我们编写代码去唤醒）。
 * 这样提供了极大的方便性。
 * @author Andy
 *阻塞队列的实现原理:事实它和我们用Object.wait()、Object.notify()和非阻塞队列实现生产者-消费者的思路类似，
 *只不过它把这些工作一起集成到了阻塞队列中实现.可以查看ArrayBlockingQueue put和take的源码。
 */
public class Test2 {
    private int queueSize = 10;
    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(queueSize);
     
    public static void main(String[] args)  {
        Test2 test = new Test2();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();
         
        producer.start();
        consumer.start();
    }
     
    class Consumer extends Thread{
         
        @Override
        public void run() {
            consume();
        }
         
        private void consume() {
            while(true){
                try {
                    queue.take();
                    System.out.println("从队列取走一个元素，队列剩余"+queue.size()+"个元素");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
     
    class Producer extends Thread{
         
        @Override
        public void run() {
            produce();
        }
         
        private void produce() {
            while(true){
                try {
                    queue.put(1);
                    System.out.println("向队列取中插入一个元素，队列剩余空间："+(queueSize-queue.size()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}