package cn.thread;

import org.junit.Test;


/**
 * blog:http://www.cnblogs.com/Mainz/p/3556430.html#
 * volatile的自增没有原子性；
 * @author Andy
 *
 */
public class TestVolatile {
	private static volatile long longVal = 0;

	private static class LoopVolatile1 implements Runnable {
		public void run() {
			long val = 0;
			while(val < 10000000L) {
				longVal++;
				val++;
			}
		}
	}
	
	private static class LoopVolatile2 implements Runnable {
		public void run() {
			long val = 0;
			while(val < 10000000L) {
				longVal++;
				val++;
			}
		}
	}
	
	@Test
	public void testVolatile() {
		Thread t1 = new Thread(new LoopVolatile1());
		t1.start();
		Thread t2 = new Thread(new LoopVolatile2());
		t2.start();
		
		while(t1.isAlive() || t2.isAlive()) {
		}
		
		System.out.println("final val is: " + longVal);
	}
}
