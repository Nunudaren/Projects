
class Demo1 {
	public static void main(String[] args) {
		int[] arr = {3,2,1,6,5,4};
//		revArray(arr);
//		Arrays.sort(arr);							//��������������,��������
//		System.out.println(Arrays.toString(arr));	//������ת�ַ���
		System.out.println(arrToString(arr));
	}

	/*
	���鷴ת
	����ֵ����void
	�����б�,int[]arr
	*/

	public static void revArray(int[] arr) {
		for (int x = 0;x < arr.length / 2 ;x++ ) {
			int temp = arr[x];
			arr[x] = arr[arr.length - 1 - x];
			arr[arr.length - 1 - x] = temp;
		}

		for (int x = 0;x < arr.length ;x++ ) {
			System.out.print(arr[x] + " ");
		}
	}

	/*
	ģ��toString����������ת���ַ���
	1,����ֵ����String
	2,�����б�,int[] arr
	*/

	public static String arrToString(int[] arr) {	//{3,2,1,6,5,4};
													//[3, 2, 1, 6, 5, 4]
		String str = "[";							//arr.length = 6 x�����ֵ��5
		for (int x = 0;x < arr.length ;x++ ) {
			if (x != arr.length - 1) {
				str = str + arr[x] + ", ";			//str = [3, 
			}else {									//str = [3, 2, 
				str = str + arr[x] + "]";			//str = [3, 2, 1, 6, 5, 
			}										//str = [3, 2, 1, 6, 5, 4]
		}

		return str;
	}
}
