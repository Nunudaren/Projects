import java.util.Scanner;


public class Main {
	static int hurt = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		        System.out.println("�����빥������R��������̨������x1, y1, x2, y2, x3, y3,���˵�����x0, y0");
		        Scanner in = new Scanner(System.in);
		         
		        double R = in.nextInt();
		        double x1 = in.nextInt();
		        double y1 = in.nextInt();
		        double x2 = in.nextInt();
		        double y2 = in.nextInt();
		        double x3 = in.nextInt();
		        double y3 = in.nextInt();
		        double x0 = in.nextInt();
		        double y0 = in.nextInt();
		         
		         
		        injure(x1, y1, x0, y0, R);
		        injure(x2, y2, x0, y0, R);
		        injure(x3, y3, x0, y0, R);
		         
		        System.out.println("�ܹ��ܵ����˺��ǣ�" + hurt + "X");
		         
		    }
		     
		    public static int injure(double x, double y, double x0, double y0, double r) {
		        if (Math.sqrt(Math.pow(x - x0, 2) + Math.pow(y - y0, 2)) <= r) {
		            hurt++;
		        }
		        return hurt;
		    }
		     
}
