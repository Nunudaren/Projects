
public class JoinTest {
	public static void main(String[] args) {
	Thread t = new Thread(new ThreadImp());
	t.start();
//	try{
//		t.join(1000);
		if(t.isAlive())
			System.out.println("t has not finished");
		else
			System.out.println("t has finished");
		System.out.println("joinFinished");
//	}catch(InterruptedException e){
//		e.printStackTrace();
//	}
}
}
class ThreadImp implements Runnable {
	public void run(){
		try{
			System.out.println("Begin ThreadImp");
			Thread.sleep(5000);
			System.out.println("End ThreadImp");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}