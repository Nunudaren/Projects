import java.util.Scanner;

public class Maxgrade {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int n = sc.nextInt();
			int m = sc.nextInt();
			int[] arr1 = new int[n];
			int[] arr2 = new int[m];
			int k = 0;
			for (int i = 0; i < arr1.length; i++) {
				arr1[i] = sc.nextInt();
			}
			for (int j = 0; j < m; j++) {
				String s = sc.next();
				int a = sc.nextInt();
				int b = sc.nextInt();
				if (s.equals("Q")) {
					if (a > b) {
						int temp = a;
						a = b;
						b = temp;
					}
					int max = arr1[a - 1];
					for (int i = a; i < b; i++) {
						max = (arr1[i] > max) ? arr1[i] : max;
					}
					arr2[k++] = max;
				} else if (s.equals("U")) {
					arr1[a - 1] = b;
				}
			}
			for (int i = 0; i < k; i++) {
				System.out.println(arr2[i]);
			}
		}
	}
}
