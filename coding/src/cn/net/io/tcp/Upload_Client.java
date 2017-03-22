package cn.net.io.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Upload_Client {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws Exception {
		// 1.提示输入要上传的文件路径, 验证路径是否存在以及是否是文件夹
		File file = getFile();
		// 2.发送文件名到服务端
		Socket socket = new Socket("127.0.0.1", 12345);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream ps = new PrintStream(socket.getOutputStream());
		ps.println(file.getName());
		
		// 5.接收结果, 如果存在给予提示, 程序直接退出
		String result = br.readLine();
		if("存在".equals(result)) {
			System.out.println("文件已经存在,请不要重复上传");
			socket.close();
			return;
		}
		
		// 6.如果不存在, 定义FileInputStream读取文件, 写出到网络
		FileInputStream fis = new FileInputStream(file);
		int len;
		byte[] arr = new byte[1024];
		
		while((len = fis.read(arr))!= -1) {
			ps.write(arr, 0, len);
		}
		
		fis.close();
		socket.close();
		System.out.println("文件上传完毕");
	}
	
	public static File getFile() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入一个文件路径:");
		while(true) {
			String line = sc.nextLine();
			File file = new File(line);
			if(!file.exists()) {
				System.out.println("您输入的文件路径不存在,请重新输入:");
			}else if(file.isDirectory()) {
				System.out.println("您输入的是文件夹路径,请输入一个文件路径");
			}else {
				return file;
			}
		}
	}

}
