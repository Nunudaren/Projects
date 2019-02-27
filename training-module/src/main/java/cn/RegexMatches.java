package cn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {
	public static void main(String args[]) {

			// 按指定模式在字符串查找
			String line = "This order was placed for QT3000! OK?";
			String pattern = "(.*)(\\d+)(.*)";

			// 创建 Pattern 对象
			Pattern r = Pattern.compile(pattern);

			// 现在创建 matcher 对象
			Matcher m = r.matcher(line);
			if (m.find()) {
				System.out.println("Found value: " + m.group(0));
				System.out.println("Found value: " + m.group(1));
				System.out.println("Found value: " + m.group(2));
				System.out.println("Found value: " + m.group(3));
			} else {
				System.out.println("NO MATCH");
			}
			System.out.println(m.pattern());
		}


	private static String regex = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";

	/**
	 * 判断是否是手机号
	 *
	 * @param mobile 手机号
	 * @return 是否是正确的手机号
	 */
	public static boolean isMobile(String mobile) {
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

}