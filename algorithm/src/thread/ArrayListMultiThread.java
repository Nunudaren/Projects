package thread;

import java.util.Vector;



public class ArrayListMultiThread {
	static Vector<Integer> al = new Vector<Integer>(10);
	public static class AddThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 10000; i++) {
				al.add(i);
			}
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread t1 = new Thread(new AddThread());
		Thread t2 = new Thread(new AddThread());
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(al.size());
	}

}
