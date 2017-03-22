package arrysort;
//·ÖÖÎ²ßÂÔ

public class MaxSubArray {
	private static final int INT_MIN = 0;
	public static int max_l, max_r;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {31,-41,59,26,-53,58,97,-93,-23,84};
		System.out.printf("the maxsubarray: %d\n", maxSubArray(arr, 0, arr.length-1));
		System.out.println("arr[" + max_l + "..." + max_r + "]");
		System.out.printf("the maxsubarray: %d\n", maxSubArray2(arr, 0, arr.length - 1));
	}
	public static int cross_maxSubArray(int[] arr, int low, int m, int high){
		int i, sum = 0, l_maxsum = 0, r_maxsum = 0;
		for(i = m; i >= low; i--){
			sum += arr[i];
			if(sum > l_maxsum)  {
				l_maxsum = sum;
				max_l = i;
			}
		}
		sum = 0;
		for(i = m + 1; i <= high; i++){
			sum += arr[i];
			if(sum > r_maxsum) {
				r_maxsum = sum;
				max_r = i;
			}
		}
		return l_maxsum + r_maxsum;
	}
	public static int maxSubArray(int[] arr, int low, int high){
		if(low == high) return arr[low];
		if(low > high) return 0;
		
		int m = (low + high) / 2;
		int l_maxsum = 0, r_maxsum = 0, c_maxsum = 0;
		l_maxsum = maxSubArray(arr, low, m);
		r_maxsum = maxSubArray(arr, m + 1, high);
		c_maxsum = cross_maxSubArray(arr, low, m, high);
		if(l_maxsum >= r_maxsum && l_maxsum >= c_maxsum) return l_maxsum;
		else if(r_maxsum >= l_maxsum && r_maxsum >= c_maxsum) return r_maxsum;
		else return c_maxsum;
	}
	public static int maxSubArray2(int[] a, int l, int r){
		int i, temp = 0, summax = INT_MIN;
		for(i = l; i <= r; i++){
			temp += a[i];
			if(temp > summax) summax = temp;
			if(temp < 0) temp = 0;
		}
		return summax;
	}
}
