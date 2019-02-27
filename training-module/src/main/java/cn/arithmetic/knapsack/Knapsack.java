package cn.arithmetic.knapsack;

public class Knapsack {
	
	private int weight; //物品重量
	private int value;//物品价值
	
	public Knapsack(int weight, int  value) {
		this.weight = weight;
		this.value = value;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}
