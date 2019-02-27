package cn.encrpyt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Md5 {
	private static final String EMP_CODE_ = null;
	public static String getMd5(String string) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(string.getBytes());
			byte[] hash = md.digest();
			StringBuffer sb = new StringBuffer();
			int i = 0;
			for (int offset = 0; offset < hash.length; offset++) {
				i = hash[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					sb.append("0");
				}
				System.out.println(i);
				sb.append(Integer.toHexString(i));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	static String PASSWORD = String.valueOf((int) (Math.random() * 1000000));
	
	public static void main(String[] args) {
		System.out.println(PASSWORD);
		String EnPASSWORD = Md5.getMd5("953736");
		System.out.println(EnPASSWORD);
	}

}
