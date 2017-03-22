package cn.net.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * 按照主贴里的写法,在isConnectable()里注册OP_READ,程序下次就会进入isReadable(),在isReadable()里又注册OP_WRITE, 
 * 这样就把OP_READ给覆盖掉了,程序下次就进入isWritable(),在isWritable()又注册了OP_READ,下一次就进入isReadable()....
 * 如此反复循环, 所以主贴代码是对的.
 * @author Andy
 *
 */

public class NIOServer {

	/* 标识数字 */
	private int flag = 0;
	/* 缓冲区大小 */
	private int BLOCK = 4096;
	/* 接收数据缓冲区 */
	private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	/* 发送数据缓冲区 */
	private ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	private Selector selector;

	public NIOServer(int port) throws IOException {
		// 打开服务器套接字通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 服务器配置为非阻塞模式
		serverSocketChannel.configureBlocking(false);
		// 检索与此通道关联的服务器套接字
		ServerSocket serverSocket = serverSocketChannel.socket();
		// 进行服务器的绑定
		serverSocket.bind(new InetSocketAddress(port));
		// 通过open()方法找到Selector
		selector = Selector.open();
		// 注册到selector,等待连接
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server Start----8888");
	}

	/**
	 * 监听
	 * 
	 * @throws IOException
	 */
	private void listen() throws IOException {
		while (true) {
			// 选择一组键，并且相应的通道已经打开
			selector.select(); //返回int值，当有已注册的事件发生时，select()返回值大于0
			//取得所有已准备好的选择键
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			//使用迭代器对选择键进行轮询
			Iterator<SelectionKey> iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				//删除掉当前将要处理的选择键，这一步是必要的
				iterator.remove();   
				handleKey(selectionKey);
			}
		}
	}

	/**
	 * 处理请求
	 * 
	 * @param selectionKey
	 * @throws IOException
	 */
	private void handleKey(SelectionKey selectionKey) throws IOException {
		// 接收请求
		ServerSocketChannel server = null;
		SocketChannel client = null;
		String receiveText;
		String sendText;
		int count = 0;

		// 测试该键的通道是否已经准备好接收新的套接字连接
		if (selectionKey.isAcceptable()) {
			// 返回为之创建此键的通道
			server = (ServerSocketChannel) selectionKey.channel();
			// 接收到此通道套接字的连接
			// 此方法返回的套接字通道（如果有）将处于阻塞模式
			client = server.accept();
			// 配置为非阻塞模式
			client.configureBlocking(false);
			// 注册到selector,等待连接
			client.register(selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {
			// 返回为之创建此键的通道
			client = (SocketChannel) selectionKey.channel();
			// 将缓冲区清空以备下次读取
			receivebuffer.clear();
			// 读取服务器发送来的数据到缓冲区中
			count = client.read(receivebuffer);
			if (count > 0) {
				receiveText = new String(receivebuffer.array(), 0, count);
				System.out.println("服务器端接收客户端数据：" + receiveText);
				client.register(selector, SelectionKey.OP_WRITE);
			}
		} else if (selectionKey.isWritable()) {
			// 将缓冲区清空以备下次写入
			sendbuffer.clear();
			// 返回为之创建此键的通道
			client = (SocketChannel) selectionKey.channel();
			sendText = "message from server--" + flag++;
			// 向缓冲区中输入数据
			sendbuffer.put(sendText.getBytes());
			// 将缓冲区各标志复位
			// 因为向里面put了数据标志被改变，要想从中读取数据发向服务器，就要复位
			sendbuffer.flip();
			// 输出到通道
			client.write(sendbuffer);
			System.out.println("服务器端向客户端发送数据：" + sendText);
			client.register(selector, selectionKey.OP_READ);

		}
	}

	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		int port = 8888;
		NIOServer server = new NIOServer(port);
		server.listen();
	}

}
