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
		System.out.println("服务器启动,绑定12345端口号");
		
		while(true) {
			final Socket socket = server.accept();
			new Thread() {
				public void run() {
					try {
						InputStream is = socket.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						PrintStream ps = new PrintStream(socket.getOutputStream());
						// 3.读取文件名
						String fileName = br.readLine();
						File dir = new File("upload");
						dir.mkdir();
						
						File file = new File(dir,fileName);
						// 4.判断文件是否存在, 将结果发回客户端
						if(file.exists()) {
							ps.println("存在");
							socket.close();
							return;
						}else {
							ps.println("不存在");
						}
						String ip = socket.getInetAddress().getHostAddress();
						System.out.println(ip + fileName + "上传开始");
						long start = System.currentTimeMillis();
						// 7.定义FileOutputStream, 从网络读取数据, 存储到本地
						FileOutputStream fos = new FileOutputStream(file);
						int len;
						byte[] arr = new byte[1024];
						while((len = is.read(arr)) != -1) {
							fos.write(arr, 0, len);
						}
						
						fos.close();
						socket.close();
						long end = System.currentTimeMillis();
						System.out.println("上传耗时" + (end- start) + "毫秒");
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			}.start();
		}
		
		
		
		
		
	}

}
