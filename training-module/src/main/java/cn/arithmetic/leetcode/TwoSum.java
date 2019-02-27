package cn.arithmetic.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TwoSum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[] = {10,2,4,16,8,3};
		System.out.println(Arrays.toString(twoSum2(arr, 19)));
		System.out.printf("%d",20);
	}
	public static int[] twoSum(int[] nums, int target){
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[j] == target - nums[i]){
					return new int[]{i ,j};
					
				}
			}
		}
		throw new IllegalArgumentException("No two sum sloution");
	}
	public static int[] twoSum1(int[] nums, int target){
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i], i);
		}
		for (int i = 0; i < nums.length; i++) {
			int complement = target - nums[i];
			if (map.containsKey(complement) && map.get(complement)!= i){
				return new int[] {i, map.get(complement)};
			}
		}
		throw new IllegalArgumentException("No two sum solution");
	}
	public static int[] twoSum2(int[] nums, int target){
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			int complement = target - nums[i];
			if (map.containsKey(complement)){
				return new int[] {map.get(complement), i};
			}
			map.put(nums[i], i);
		}
		throw new IllegalArgumentException("No two sum sloution");
	}
}
