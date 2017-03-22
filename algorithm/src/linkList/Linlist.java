package linkList;

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
		System.out.println(lList);
		lList.subList(2, 4).clear();
//		lList.remove(3);
		System.out.println(lList);
	}
}
