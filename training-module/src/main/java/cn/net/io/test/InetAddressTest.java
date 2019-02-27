package cn.net.io.test;

import java.io.IOException;
import java.net.InetAddress;

public class InetAddressTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if(args.length > 0){
			String host = "google.com";
			InetAddress[] addresses = InetAddress.getAllByName(host);
			for (InetAddress a : addresses) {
				System.out.println(a);
			}
		}else{
			InetAddress localHostAddress = InetAddress.getLocalHost();
			System.out.println(localHostAddress);
		}
	}

}
