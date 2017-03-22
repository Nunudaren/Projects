package cn.concurrent.com;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
//Semaphore�����������˼Ϊ �ź�����Semaphore���Կ�ͬʱ���ʵ��̸߳�����ͨ�� acquire() ��ȡһ����ɣ����û�о͵ȴ����� release() �ͷ�һ����ɡ�
//����ͨ��һ����������һ��Semaphore�ľ���ʹ�ã�
//��lock���е���
//����һ��������5̨������������8�����ˣ�һ̨����ͬʱֻ�ܱ�һ������ʹ�ã�ֻ��ʹ�����ˣ��������˲��ܼ���ʹ�á���ô���ǾͿ���ͨ��Semaphore��ʵ�֣�
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		int N = 8; // ������
		Semaphore semaphore = new Semaphore(5); // ������Ŀ
		for (int i = 0; i < N; i++)
			new Worker(i, semaphore).start();*/
	
		ExecutorService exec = Executors.newFixedThreadPool(20);
		final SemapDemo demo = new SemapDemo();
		for (int i = 0; i < 20; i++) {
			exec.submit(demo);
		}
	}

	static class Worker extends Thread {
		private int num;
		private Semaphore semaphore;

		public Worker(int num, Semaphore semaphore) {
			this.num = num;
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.println("����" + this.num + "ռ��һ������������...");
				Thread.sleep(2000);
				System.out.println("����" + this.num + "�ͷų�����");
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class SemapDemo implements Runnable{
	final Semaphore semp = new Semaphore(5);

	@Override
	public void run() {
		try {
			semp.acquire();
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getId() + ":done!");
			semp.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}