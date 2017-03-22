
public class MyThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread thread = new Thread(new Runnable(){
			public void run(){
				System.out.println("thread go to sleep");
				try{
					Thread.sleep(500);
					System.out.println("thread finish");
				}catch(InterruptedException e){
					System.out.println("thread is interrupted!");
				}
			}
		});
		thread.start();
		thread.interrupt();
	}

}
