package cn.net.io.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		try(Socket s = new Socket("time-A.timefreq.bldrdoc.gov",13);)
		{
			InputStream inStream = s.getInputStream();
			Scanner in = new Scanner(inStream);
			while (in.hasNextLine()) {
				String line = in.nextLine();
				System.out.println(line);
			}
		}
	}

}
