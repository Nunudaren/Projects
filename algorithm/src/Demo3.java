public class Demo3 {
	public static void main(String[] args) {
		/*
		 * int[] arr = {33,22,11,44,55,66}; int num = getIndex(arr,88);
		 * System.out.println(num);
		 */

		int[] arr = { 11, 22, 33, 44, 55, 66, 77 };
		int index = getIndex2(arr, 10);
		System.out.println(index);
		int a = 3;
		int b = 3;
		int c =  a > b ? a : b ;
		System.out.println(c);
	}

	/*
	 * �������������е�Ԫ�� 1,��ȷ����ֵ����,int 2,��ȷ�����б�,int[] arr,int num
	 */

	public static int getIndex(int[] arr, int num) {
		for (int x = 0; x < arr.length; x++) {
			if (num == arr[x]) {
				return x;
			}
		}

		return -1;
	}

	/*
	 * ������������ 1,����ֵ����,int 2,�����б�,int[] arr,int num                    ���ֲ��ҷ�
	 */

	public static int getIndex2(int[] arr, int num) {
		int min = 0; // ��С�Ǳ�ֵ
		int max = arr.length - 1; // ���Ǳ�ֵ
		int mid = (min + max) / 2; // �м�Ǳ�ֵ

		while (num != arr[mid]) {
			if (num < arr[mid]) {
				max = mid - 1;
			} else if (num > arr[mid]) {
				min = mid + 1;
			}

			mid = (min + max) / 2; // ������min�ı仹��max�ı�mid����ı�
			if (min > max) {
				return -1;
			}
		}

		return mid;
	}
}
