package arrysort;

import java.util.Arrays;

import org.junit.Test;

public class HeapSort2 {
	
	@Test
	public void test() {
		int arr[] = { 53, 17, 78, 9, 45, 65, 87, 32};
		HeapSort(arr, arr.length);
		System.out.println(Arrays.toString(arr));
		
	}
	
	
	
	public void HeapSort(int A[], int len) {
		BuildMaxHeap(A, len);   //��ʼ������
		
		for (int i = len - 1; i >= 0; i--) {    //n-1�˵Ľ����ͽ��ѵĹ���
			
			//��������еĶ���Ԫ��,�����ֵ
		    System.out.println(Arrays.toString(A));  
		    
		    //���Ѷ�����ֵ�Ͷѵ׵�Ԫ�ؽ�����ע��:���鴫����ֵ���ݣ�����ֵ�ĸ������������ã���˶��βεĸı䲻��Ӱ��ʵ�ε�ֵ��
			// Swap(A[i], A[0]);
			
			int temp = A[0];
			A[0] = A[i];
			A[i] = temp;
			
			AdjustDown(A, 0, i - 1);      //������ʣ���i-1��Ԫ������ɶ�
		}
	}


	private void BuildMaxHeap(int[] A, int len) {
		for(int i = len / 2 - 1; i >= 0; i--) {   //��i = [n/2] ~ 1, ���������
			AdjustDown(A, i, len - 1);
		}
	}
	
//С����
	private void AdjustDown(int[] A, int k, int lastIndex) {   //lastIndex=len-1;�����������±�
		//��Ԫ��i���½��е���
		int temp = A[k];         //��A[k]�ݴ�
		for(int i = 2 * k + 1; i <= lastIndex; i = 2 * i + 1) {   //��key�ϴ���ӽڵ�����ɸѡ
			if(i < lastIndex && A[i] < A[i + 1]) {
				i++;                           //ȡkey�ϴ���ӽڵ���±�
			}
			if(temp >= A[i])
				break;                //ɸѡ����
			else {
				A[k] = A[i]; //��A[i]������˫�׽ڵ���
				k = i;      //�޸�kֵ���Ա��������ɸѡ
			}
		}
		A[k] = temp;    //����ɸѡ�ڵ��ֵ�������յ�λ��
	}
	
	
	
	private void Swap(int i, int j) {
		int temp = i;
		i = j;
		j = temp;
	}
}
