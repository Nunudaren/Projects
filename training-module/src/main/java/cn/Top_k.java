package cn;

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;

public class Top_k {
	@Test
	public void test() {
		String[] tel = { "13501953685", "15221620869", "13578452254",
				"1524785624", "13854256875", "15221620869", "13501953683",
				"13501953685", "15221620869", "15000247261", "18254756542",
				"18616396007", "18645236987", "18616396007", "13501953683",
				"16845267895", "13703950406", "15221620869", "13501953683",
				"15234687854", "15221620869", "18254756542", "15221620869",
				"18616396007", "152496885726", "13501953683", "15000247261",
				"15100247261" };
		printTopKAndRank(tel, 4);

	}

	public void printTopKAndRank(String[] arr, int topK) {
		if (arr == null || topK < 1) {
			return;
		}
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < arr.length; i++) {
			String cur = arr[i];
			if (!map.containsKey(cur)) {
				map.put(cur, 1);
			} else {
				map.put(cur, map.get(cur) + 1);
			}
		}
		Node[] heap = new Node[topK];
		int index = 0;

		for (Entry<String, Integer> entry : map.entrySet()) {
			String str = entry.getKey();
			int times = entry.getValue();
			Node node = new Node(str, times);
			if (index != topK) {
				heap[index] = node;
				heapInsert(heap, index++); // 建立大根堆
			} else {
				if (heap[0].getTimes() < node.getTimes()) { // 比较堆顶元素，并调整堆
					heap[0] = node;
					heapify(heap, 0, topK);
				}
			}
		}

		for (int i = index - 1; i != 0; i--) { // 把小根堆的所有元素按词频从大到小排序
			swap(heap, 0, i);
			heapify(heap, 0, i);
		}

		for (int i = 0; i != heap.length; i++) { // 严格按照排名打印k条记录
			if (heap[i] == null) {
				break;
			} else {
				System.out.print("No." + (i + 1) + ": ");
				System.out.print(heap[i].getStr() + ", times: ");
				System.out.println(heap[i].getTimes());
			}
		}
	}

	private void heapInsert(Node[] heap, int index) {
		while (index != 0) { // 自index向上调整堆
			int parent = (index - 1) / 2;
			if (heap[index].getTimes() < heap[parent].getTimes()) {
				swap(heap, parent, index);
				index = parent;
			} else {
				break;
			}
		}
	}

	private void heapify(Node[] heap, int index, int heapSize) {
		int left = index * 2 + 1;
		int right = index * 2 + 2;
		int smallest = index;
		while (left < heapSize) { // 自index向下调整
			if (heap[left].getTimes() < heap[index].getTimes()) {
				smallest = left;
			}
			if (right < heapSize
					&& heap[right].getTimes() < heap[smallest].getTimes()) {
				smallest = right;
			}
			if (smallest != index) {
				swap(heap, smallest, index);
			} else {
				break;
			}
			index = smallest;
			left = index * 2 + 1;
			right = index * 2 + 2;
		}
	}

	private void swap(Node[] heap, int parent, int index) {
		Node temp = heap[parent];
		heap[parent] = heap[index];
		heap[index] = temp;
	}

}

class Node {
	private String str;
	private int times;

	public Node(String s, int t) {
		this.str = s;
		this.times = t;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}
