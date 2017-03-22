package cn.net.io.tcp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Upload_Server {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(12345);
		System.out.println("����������,��12345�˿ں�");
		
		while(true) {
			final Socket socket = server.accept();
			new Thread() {
				public void run() {
					try {
						InputStream is = socket.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						PrintStream ps = new PrintStream(socket.getOutputStream());
						// 3.��ȡ�ļ���
						String fileName = br.readLine();
						File dir = new File("upload");
						dir.mkdir();
						
						File file = new File(dir,fileName);
						// 4.�ж��ļ��Ƿ����, ��������ؿͻ���
						if(file.exists()) {
							ps.println("����");
							socket.close();
							return;
						}else {
							ps.println("������");
						}
						String ip = socket.getInetAddress().getHostAddress();
						System.out.println(ip + fileName + "�ϴ���ʼ");
						long start = System.currentTimeMillis();
						// 7.����FileOutputStream, �������ȡ����, �洢������
						FileOutputStream fos = new FileOutputStream(file);
						int len;
						byte[] arr = new byte[1024];
						while((len = is.read(arr)) != -1) {
							fos.write(arr, 0, len);
						}
						
						fos.close();
						socket.close();
						long end = System.currentTimeMillis();
						System.out.println("�ϴ���ʱ" + (end- start) + "����");
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			}.start();
		}
		
		
		
		
		
	}

}
