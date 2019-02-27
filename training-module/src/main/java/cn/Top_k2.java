package cn;

import java.util.Iterator;
import java.util.PriorityQueue;

import org.junit.Test;

public class Top_k2 {
	
	@Test
	public void test() {
		int[] arr = {2,5,100,11,7,9,25,62,34,87};
		
		int num = findKthLargest(arr, 4);
		System.out.println(num);
		
	}
	
//	找到第K大整数代码如下
	//PriorityQueue实现了数据结构堆，通过指定comparator字段来表示小顶堆或大顶堆，默认为null，表示自然序（natural ordering）
	//API: PriorityQueue是一个基于优先级堆的无界优先级队列。优先级队列的元素按照其自然顺序进行排序，或者根据构造队列时提供的 Comparator 进行排序，具体取决于所使用的构造方法
	//此队列的头 是按指定排序方式确定的最小元素
	public int findKthLargest(int[] nums, int k) {
		PriorityQueue<Integer> minQueue = new PriorityQueue<>(k); //k is initial capacity. 
		for (int num : nums) {
			if (minQueue.size() < k || num > minQueue.peek())
				minQueue.offer(num);
			if (minQueue.size() > k)
				minQueue.poll();
		}
		System.out.println(minQueue.toString());
		return minQueue.peek();
	}
}
