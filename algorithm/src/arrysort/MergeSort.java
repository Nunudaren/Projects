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
//	�� a ������a[l...m]�� a[m+1...h]�������򣬽����Ǻϲ���һ�������
	public static void merge(int a[], int l, int m, int h){
		int[] b = new int[a.length];
		int i,j,k;
		for(k = l; k <= h; k++){    
			b[k] = a[k];
		}
		for(i = l, j = m + 1, k = i; i <= m && j <= h; k++){ //��������������Ԫ�ذ���С�����˳��鲢������a��
			if(b[i] <= b[j]){	//�Ƚ�b�����������е�Ԫ��
				a[k] = b[i++];  //����Сֵ���Ƶ�a��
			}else{
				a[k] = b[j++];
			}
		}
//		�Բ��ȳ���������鲢ʱ
		while(i <= m) a[k++] = b[i++];  //����һ����δ����꣬����
		while(j <= h) a[k++] = b[j++];
	}

}
