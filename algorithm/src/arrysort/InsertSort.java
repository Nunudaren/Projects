package arrysort;

import java.util.Arrays;

public class InsertSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[] = {3,1,5,7,2,4,9,6};
		insertSort(arr, arr.length);
		System.out.println(Arrays.toString(arr));
	}

	public static void insertSort(int[] a, int n) {
		int i, j;
		for (i = 1; i < n; i++) {
			if (a[i] < a[i - 1]) {
				int temp = a[i];
				j = i;
				
//				j>=1 必须先于temp<a[j-1]判断；否则会出现数组访问角标越界的情况（j-1=-1）
				
				while ( j >= 1 && temp < a[j - 1]) { 
					a[j] = a[j - 1];
					j--;
				}
				a[j] = temp;
			}
		}
	}

}
