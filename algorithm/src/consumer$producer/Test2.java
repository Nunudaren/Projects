package consumer$producer;

import java.util.concurrent.ArrayBlockingQueue;

//ʹ����������ʵ�ֵ�������-������ģʽ:
/**
 * ʹ�÷��������е�ʱ����һ���ܴ�������ǣ�������Ե�ǰ�̲߳���������
 * ��ô���������������-�����ߵ�ģ��ʱ���ͱ�������ʵ��ͬ�������Լ��̼߳份�Ѳ��ԣ�
 * ���ʵ�������ͷǳ��鷳�����������������оͲ�һ���ˣ�����Ե�ǰ�̲߳���������
 * ����һ���̴߳�һ���յ�����������ȡԪ�أ���ʱ�̻߳ᱻ����ֱ����������������Ԫ�ء�
 * ����������Ԫ�غ󣬱��������̻߳��Զ������ѣ�����Ҫ���Ǳ�д����ȥ���ѣ���
 * �����ṩ�˼���ķ����ԡ�
 * @author Andy
 *�������е�ʵ��ԭ��:��ʵ����������Object.wait()��Object.notify()�ͷ���������ʵ��������-�����ߵ�˼·���ƣ�
 *ֻ����������Щ����һ�𼯳ɵ�������������ʵ��.���Բ鿴ArrayBlockingQueue put��take��Դ�롣
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
                    System.out.println("�Ӷ���ȡ��һ��Ԫ�أ�����ʣ��"+queue.size()+"��Ԫ��");
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
                    System.out.println("�����ȡ�в���һ��Ԫ�أ�����ʣ��ռ䣺"+(queueSize-queue.size()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}