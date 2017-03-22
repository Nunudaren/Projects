
public class ReverseInteger {
	public static void main(String[] args) {
		int x = 123;
		reverse(x);
		int y = -123;
		System.out.println(reverse01(y));
	}
	public static void reverse(int x){ //负数不行
		System.out.println(Integer.parseInt(new StringBuffer(String.valueOf(x)).reverse().toString()));
	}
	public static int reverse01(int x){
		int result = 0 ;
		while( x != 0){
			int tail = x % 10;
			int newResult = result * 10 + tail;
			if((newResult - tail) / 10 != result){ // a universal way to check whether exists overflow
				return 0;                           //if exists, newResult not a whole value, (newResult -tail ) / 10 != result;
			}
			result = newResult;
			x = x / 10;
		}
		return (int) result;
	}
}
