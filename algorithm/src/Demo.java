import java.util.Scanner;


public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		   System.out.println("���������������С�׵ĳ�ʼ����ֵ���Կո�ָ���");
		  while (input.hasNext()) {
		   // ���������
		   int n = input.nextInt();
		   // С�׳�ʼ����ֵ
		   int ability = input.nextInt();
		   // �����������
		   int[] monsters = new int[n];
		   // ����ÿ������ķ�����
		   for (int i = 0; i < n; i++) {
		    monsters[i] = input.nextInt();
		   }
		   // С������
		   for (int i = 0; i < n; i++) {
		    if (ability > monsters[i] || ability == monsters[i]) {
		     ability = ability + monsters[i];
		    } else {
		     ability = ability + GCF(ability, monsters[i]);
		    }
		   }
		   // ������ˣ�������
		   System.out.println(ability);
		  }
		 }
		 public static int GCF(int A,int B){
		  if(B==0){
		   return A;
		  }else
		   return GCF(B,A%B);
		 }
}
