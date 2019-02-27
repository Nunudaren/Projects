package cn.data_structure.arrysort;

import java.util.Arrays;

import org.junit.Test;

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



class HeapSort2 {

	@Test
	public void test() {
		int arr[] = { 53, 17, 78, 9, 45, 65, 87, 32};
		HeapSort(arr, arr.length);
		System.out.println(Arrays.toString(arr));

	}



	public void HeapSort(int A[], int len) {
		BuildMaxHeap(A, len);   //初始化建堆

		for (int i = len - 1; i >= 0; i--) {    //n-1趟的交换和建堆的过程

			//并输出堆中的顶部元素,即最大值
			System.out.println(Arrays.toString(A));

			//将堆顶部的值和堆底的元素交换（注意:数组传参是值传递，传的值的副本而不是引用，因此对形参的改变不会影响实参的值）
			// Swap(A[i], A[0]);

			int temp = A[0];
			A[0] = A[i];
			A[i] = temp;

			AdjustDown(A, 0, i - 1);      //整理，把剩余的i-1个元素整理成堆
		}
	}


	private void BuildMaxHeap(int[] A, int len) {
		for(int i = len / 2 - 1; i >= 0; i--) {   //从i = [n/2] ~ 1, 反复整理堆
			AdjustDown(A, i, len - 1);
		}
	}

	//大顶堆
	private void AdjustDown(int[] A, int k, int lastIndex) {   //lastIndex=len-1;即数组最后的下标
		//将元素i向下进行调整
		int temp = A[k];         //将A[k]暂存
		for(int i = 2 * k + 1; i <= lastIndex; i = 2 * i + 1) {   //沿key较大的子节点向下筛选
			if(i < lastIndex && A[i] < A[i + 1]) {
				i++;                           //取key较大的子节点的下标
			}
			if(temp >= A[i])
				break;                //筛选结束
			else {
				A[k] = A[i]; //将A[i]调整到双亲节点上
				k = i;      //修改k值，以便继续向下筛选
			}
		}
		A[k] = temp;    //将被筛选节点的值放入最终的位置
	}



	private void Swap(int i, int j) {
		int temp = i;
		i = j;
		j = temp;
	}
}


