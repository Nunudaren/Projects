package com.niuke.SwordOffer;

import java.util.Stack;

import org.junit.Test;

/*
 * 两个栈实现队列的功能
 */
public class Solution05 {
	Stack<Integer> stack1 = new Stack();
	Stack<Integer> stack2 = new Stack();
	private Integer num;
	
	private void push(int num) {
		stack1.push(num);
	}
	private int pop() {
		if(stack2.empty()){
			while(!stack1.empty()){
				stack2.push(stack1.pop());
			}
		}
		if(!stack2.empty()){
			return stack2.pop();
		}else{
			return -1;
		}
	}
	
	
	@Test
	public void test(){
		int[] arr = {1,2,3,4,5};
		for (int i : arr) {
			push(i);
		}
		System.out.println(stack1.peek());
		
		for(int i = 0; i < arr.length; i++){
			System.out.print(pop() + " ");
		}
		System.out.println(pop());
	}
}
