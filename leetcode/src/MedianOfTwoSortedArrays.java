public class MedianOfTwoSortedArrays {
	public static void main(String[] args) {
		int[] num1 = { 1, 2, 3, 4, 5 };
		int[] num2 = { 1, 1, 1, 1, 2 };
		int A[] = {1,3};
		int B[] = {2};
//		double m = findMedianSortedArrays01(num1, num2);
		double m1 = findMedianSortedArrays01(A, B);
//		double n = findMedianSortedArrays01(num1, num2);
//		System.out.println(n);
		System.out.println(m1);
	}

	
	/**
	 * What is the use of median?
	 * In statistics, the median is used for dividing a set into two "equal" length subsets, that one subset is always "greater" than the other
	 * @param A
	 * @param B
	 * @return
	 */
	
	public static double findMedianSortedArrays01(int[] A, int[] B) {
		int m = A.length;
		int n = B.length;

		if (m > n) {
			return findMedianSortedArrays01(B, A);
		}

		/*
		 *     	  left_part          |        right_part
				A[0], A[1], ..., A[i-1]  |  A[i], A[i+1], ..., A[m-1]
				B[0], B[1], ..., B[j-1]  |  B[j], B[j+1], ..., B[n-1]
				
			If we can ensure:

			1) len(left_part) == len(right_part)
			2) max(left_part) <= min(right_part)
			
			To ensure these two conditions, we just need to ensure:

			(1) i + j == m - i + n - j (or: m - i + n - j + 1)

    		if n >= m, we just need to set: i = 0 ~ m, j = (m + n + 1)/2 - i
			(2) B[j-1] <= A[i] and A[i-1] <= B[j]
				
		 */
		int i = 0, j = 0, imin = 0, imax = m, half = (m + n + 1) / 2;
		double maxLeft = 0, minRight = 0;
		//二分查找
		while (imin <= imax) {
			i = (imin + imax) / 2;
			j = half - i;
			if (j > 0 && i < m && B[j - 1] > A[i]) {  //条件中不能少了i<m
				//i is too small, must increase it
				imin = i + 1;
			} else if (i > 0 && j < n && A[i - 1] > B[j]) { //条件中不能少了j<n，不然会出现数据角标越界
				//i is too big, must decrease it
				imax = i - 1;
			} else {
				//i is perfect
				if (i == 0) {             //A全部都在rightpart
					maxLeft = (double) B[j - 1];
				} else if (j == 0) {     //B全部为rightpart
					maxLeft = (double) A[i - 1];
				} else {                //leftpart和rightpart具有A和B的存在
					maxLeft = (double) Math.max(A[i - 1], B[j - 1]);
				}
				break;
			}
		}
		if ((m + n) % 2 == 1) {   //长度为奇数
			return maxLeft;
		}   
		
		//长度为偶数
		if (i == m) { //A全部都在左边
			minRight = (double) B[j];
		} else if (j == n) { //B全部在左边
			minRight = (double) A[i];
		} else {  //
			minRight = (double) Math.min(A[i], B[j]);
		}
		return (double) (maxLeft + minRight) / 2;
	}

	
	public static double findMedianSortedArrays02(int A[], int B[]) {
		int n = A.length;
		int m = B.length;
		// the following call is to make sure len(A) <= len(B).
		// yes, it calls itself, but at most once, shouldn't be
		// consider a recursive solution
		if (n > m)
			return findMedianSortedArrays02(B, A);

		// now, do binary search
		int k = (n + m - 1) / 2;
		int l = 0, r = Math.min(k, n); // r is n, NOT n-1, this is important!!
		while (l < r) {
			int midA = (l + r) / 2;
			int midB = k - midA;
			if (A[midA] < B[midB])
				l = midA + 1;
			else
				r = midA;
		}

		// after binary search, we almost get the median because it must be
		// between
		// these 4 numbers: A[l-1], A[l], B[k-l], and B[k-l+1]

		// if (n+m) is odd, the median is the larger one between A[l-1] and
		// B[k-l].
		// and there are some corner cases we need to take care of.
		int a = Math.max(l > 0 ? A[l - 1] : Integer.MIN_VALUE, k - l >= 0 ? B[k
				- l] : Integer.MIN_VALUE);
		if (((n + m) & 1) == 1)
			return (double) a;

		// if (n+m) is even, the median can be calculated by
		// median = (max(A[l-1], B[k-l]) + min(A[l], B[k-l+1]) / 2.0
		// also, there are some corner cases to take care of.
		int b = Math.min(l < n ? A[l] : Integer.MAX_VALUE, k - l + 1 < m ? B[k
				- l + 1] : Integer.MAX_VALUE);
		return (a + b) / 2.0;
	}
	
}
