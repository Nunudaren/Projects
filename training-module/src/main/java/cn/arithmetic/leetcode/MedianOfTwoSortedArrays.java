package cn.arithmetic.leetcode;

public class MedianOfTwoSortedArrays {
	public static void main(String[] args) {
		int[] num1 = { 1, 2, 3, 4, 5 };
		int[] num2 = { 1, 1, 1, 1, 2 };
		double m = findMedianSortedArrays01(num1, num2);
		System.out.println(m);
	}

	public static double findMedianSortedArrays01(int[] nums1, int[] nums2) {
		int m = nums1.length;
		int n = nums2.length;

		if (m > n) {
			return findMedianSortedArrays01(nums2, nums1);
		}

		int i = 0, j = 0, imin = 0, imax = m, half = (m + n + 1) / 2;
		double maxLeft = 0, minRight = 0;
		while (imin <= imax) {
			i = (imin + imax) / 2;
			j = half - i;
			if (j > 0 && i < m && nums2[j - 1] > nums1[i]) {
				imin = i + 1;
			} else if (i > 0 && j < n && nums1[i - 1] > nums2[j]) {
				imax = i - 1;
			} else {
				if (i == 0) {
					maxLeft = (double) nums2[j - 1];
				} else if (j == 0) {
					maxLeft = (double) nums1[i - 1];
				} else {
					maxLeft = (double) Math.max(nums1[i - 1], nums2[j - 1]);
				}
				break;
			}
		}
		if ((m + n) % 2 == 1) {
			return maxLeft;
		}
		if (i == m) {
			minRight = (double) nums2[j];
		} else if (j == n) {
			minRight = (double) nums1[i];
		} else {
			minRight = (double) Math.min(nums1[i], nums2[j]);
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
