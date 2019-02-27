package cn.arithmetic.knapsack;

import java.util.Scanner;

public class KnapAlg {
	
	static int n, i, C, s;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入背包的最大容量：");
		C = sc.nextInt();
		System.out.println("物品数量：");
		n = sc.nextInt();
		
		int w[] = new int[n];
		int v[] = new int[n];
		int x[] = new int[n];
		System.out.println("请分别输入物品的重量：");
		for(i = 0; i < n; i++) {
			w[i] = sc.nextInt();
		}
		System.out.println("请分别输入物品的价值：");
		for(i = 0; i < n; i++) {
			v[i] = sc.nextInt();
		}
		
		s = Knap(n,w,v,x,C);
		System.out.println("最大物品价值为：");
		System.out.println(s);
	}
	
	

	/*
	 * 假设前n个物品，总承重为j,背包的最大价值为BestPrice[n][j]. 物品的重量为w[i],价值为v[i] 该问题的状态迁移方程如下：
	 * (1): BestPrice[i][0] = BestPrice[0][j] = 0; (2): BestPrice[i][j] =
	 * BestPrice[i - 1][j] , j < w[i] (3): BestPrice[i][j] =
	 * Max{BestPrice[i-1][j], BestPrice[i-1][j - w[i]] + v[i]}
	 */

	/**
	 * 
	 * @param n
	 *            (1 <= i <= n) 物品的个数
	 * @param w
	 *            [i] 物品的重量
	 * @param v
	 *            [i] 物品的价值
	 * @param x
	 *            [i] 标记被选中物品
	 * @param C
	 *            背包的总承重量
	 * @return
	 */
	public static int Knap(int n, int w[], int v[], int x[], int C) {
		int BestPrice[][] = new int[n + 1][C + 1];
		int i, j;
		for (i = 0; i <= n; i++) {
			BestPrice[i][0] = 0;
		}
		for (j = 0; j <= C; j++) {
			BestPrice[0][j] = 0;
		}
		for (i = 1; i <= n; i++) {
			for (j = 1; j <= C; j++) {
				if (j < w[i - 1])  //注意 w的下标是从0开始的；
					BestPrice[i][j] = BestPrice[i - 1][j]; //注意i和j的下标只能从1开始（0-1=-1）
				else
					BestPrice[i][j] = Math.max(BestPrice[i - 1][j],
							BestPrice[i - 1][j - w[i-1]] + v[i-1]);
			}
		}
		j = C;
		for (i = n; i > 0; i--) {
			if (BestPrice[i][j] > BestPrice[i - 1][j]) {
				x[i-1] = 1;
				j = j - w[i-1];
			} else
				x[i-1] = 0;
		}
		System.out.println("选中的物品为：");
		for (i = 0; i < n; i++) {
			System.out.print(x[i] + " ");
		}
		System.out.println();
		return BestPrice[n][C];
	}
	
}
