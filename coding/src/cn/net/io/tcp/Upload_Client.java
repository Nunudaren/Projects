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
		// 1.��ʾ����Ҫ�ϴ����ļ�·��, ��֤·���Ƿ�����Լ��Ƿ����ļ���
		File file = getFile();
		// 2.�����ļ����������
		Socket socket = new Socket("127.0.0.1", 12345);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream ps = new PrintStream(socket.getOutputStream());
		ps.println(file.getName());
		
		// 5.���ս��, ������ڸ�����ʾ, ����ֱ���˳�
		String result = br.readLine();
		if("����".equals(result)) {
			System.out.println("�ļ��Ѿ�����,�벻Ҫ�ظ��ϴ�");
			socket.close();
			return;
		}
		
		// 6.���������, ����FileInputStream��ȡ�ļ�, д��������
		FileInputStream fis = new FileInputStream(file);
		int len;
		byte[] arr = new byte[1024];
		
		while((len = fis.read(arr))!= -1) {
			ps.write(arr, 0, len);
		}
		
		fis.close();
		socket.close();
		System.out.println("�ļ��ϴ����");
	}
	
	public static File getFile() {
		Scanner sc = new Scanner(System.in);
		System.out.println("������һ���ļ�·��:");
		while(true) {
			String line = sc.nextLine();
			File file = new File(line);
			if(!file.exists()) {
				System.out.println("��������ļ�·��������,����������:");
			}else if(file.isDirectory()) {
				System.out.println("����������ļ���·��,������һ���ļ�·��");
			}else {
				return file;
			}
		}
	}

}
