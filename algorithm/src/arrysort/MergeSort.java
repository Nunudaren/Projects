package arrysort;

import java.util.Arrays;

public class MergeSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[] = {49,38,65,97,76,13,27};
		mergeSort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
	}
	public static void mergeSort(int a[], int l, int h){
		if(l < h){
			int m = (l + h) / 2;
			mergeSort(a, l, m);
			mergeSort(a, m + 1, h);
			merge(a, l, m, h);
		}
	}
//	表 a 的两段a[l...m]和 a[m+1...h]各自有序，将它们合并成一个有序表
	public static void merge(int a[], int l, int m, int h){
		int[] b = new int[a.length];
		int i,j,k;
		for(k = l; k <= h; k++){    
			b[k] = a[k];
		}
		for(i = l, j = m + 1, k = i; i <= m && j <= h; k++){ //将左右两个段中元素按从小到大的顺序归并到数组a中
			if(b[i] <= b[j]){	//比较b的左右两段中的元素
				a[k] = b[i++];  //将较小值复制到a中
			}else{
				a[k] = b[j++];
			}
		}
//		对不等长的两个组归并时
		while(i <= m) a[k++] = b[i++];  //若第一个表未检测完，复制
		while(j <= h) a[k++] = b[j++];
	}

}
