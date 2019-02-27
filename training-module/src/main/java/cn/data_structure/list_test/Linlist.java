package cn.data_structure.list_test;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.*;

public class Linlist {
	/*
	 * author by w3cschool.cc Main.java
	 */
	public static void main(String[] args) {
		LinkedList<String> lList = new LinkedList<String>();
		lList.add("1");
		lList.add("8");
		lList.add("6");
		lList.add("4");
		lList.add("5");
		String result = Joiner.on(" ").join(lList);
		System.out.println(result);
		System.out.println(lList);
		lList.subList(2, 4).clear();
//		lList.remove(3);
		System.out.println(lList);


	}

	/**
	 * 测试 数组是引用类型
	 *
	 */
	@Test
	public void testDemo1() {
		int[] array = new int[]{1,2,3};
		System.out.println(array[0]);
		System.out.println(switchTerm(array)[0]);
	}

	public int[] switchTerm(int[] array) {
		int temp = array[0];
		array[0] = array[2];
		array[2] = temp;
		return array;
	}

	public void trainDemoOne() {
		List list = new ArrayList();
		list.add("d");
		list.add(21);
		Integer i = (Integer) list.get(1);

	}
}
