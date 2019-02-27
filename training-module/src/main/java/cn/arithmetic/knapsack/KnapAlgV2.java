package cn.arithmetic.knapsack;

public class KnapAlgV2 {
	//所有的物品
	private Knapsack[] bags;
	//物品的数量；
	private int n;
	//背包总承重量
	private int totalWeight;
	//第一维：当前第几个物品；第二维：当前背包的承重量；值：当前背包的最大价值
	private int[][] bestValues;
	//最终背包中最大价值
	private int bestValue;
	
	public KnapAlgV2(Knapsack[] bags, int totalWeight) {
		this.bags = bags;
		this.totalWeight = totalWeight;
		this.n = bags.length;
		if(bestValues == null) {
			//考虑0的状态+1，防止数组角标越界
			bestValues = new int[n + 1][totalWeight + 1];
		}
	}
	
	public void solve() {
		//遍历所有的物品
		for(int i = 0; i <= n; i++) {
			//遍历背包的承重
			for(int j = 0; j <= totalWeight; j++) {
				//当背包不放入物品或承重为0时，其最大价值均为0
				if(i == 0 || j == 0) {
					bestValues[i][j] = 0;
				} else {
					//如果第i个物品重量大于总的承重量，则最优解存在于前i-1个背包中
					if(j < bags[i - 1].getWeight()) {
						bestValues[i][j] = bestValues[i - 1][j];
					}else {
						//如果第i个物品不大于总承重量，则最优解要么是包含第i个背包的最优解
						//要么是不包含第i个背包的最优解，取两者的最大值
						int weight = bags[i - 1].getWeight();
						int value = bags[i - 1].getValue();
						bestValues[i][j] = Math.max(bestValues[i - 1][j], bestValues[i - 1][j - weight] + value);
					}
				}
			}
		}
		bestValue = bestValues[n][totalWeight];
	}
	
	public int getBestValues() {
		return bestValue;
	}
	
	
	
	public static void main(String[] args) {
		Knapsack[] bags = new Knapsack[] { new Knapsack(2,13), new Knapsack(1,10), new Knapsack(3, 24), 
				new Knapsack(2, 15), new Knapsack(4, 28), new Knapsack(5, 33),
	            new Knapsack(3, 20), new Knapsack(1, 8)};
		int totalWeight = 12;
		KnapAlgV2 problem = new KnapAlgV2(bags, totalWeight);
		
		problem.solve();
		System.out.println(problem.getBestValues());
	}
}
