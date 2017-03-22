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
	 * 查找无序数组中的元素 1,明确返回值类型,int 2,明确参数列表,int[] arr,int num
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
	 * 查找有序数组 1,返回值类型,int 2,参数列表,int[] arr,int num                    二分查找法
	 */

	public static int getIndex2(int[] arr, int num) {
		int min = 0; // 最小角标值
		int max = arr.length - 1; // 最大角标值
		int mid = (min + max) / 2; // 中间角标值

		while (num != arr[mid]) {
			if (num < arr[mid]) {
				max = mid - 1;
			} else if (num > arr[mid]) {
				min = mid + 1;
			}

			mid = (min + max) / 2; // 无论是min改变还是max改变mid都会改变
			if (min > max) {
				return -1;
			}
		}

		return mid;
	}
}
