package string;
//½£Ö¸offer
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isNumeric("1.2e-2"));
	}

	public static boolean isNumeric(String str) {
		String string = str;
		return string.matches("[\\+-]?[0-9]*(\\.[0-9]*)?([eE][\\+-]?[0-9]+)?");
	}
}
