package code.github.snet.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * simple client
 * 
 * @author bhsc81114
 * 
 */
public class Client {

	public static void main(String[] args) throws Exception {
		/**
		 * 要指定ip和端口 也可以使用bind指定本地的端口，不然是随机分配的，然后再connect
		 */
		Socket socket = new Socket(InetAddress.getLocalHost(), 5678);
		// socket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 5674));
		// socket.connect(new InetSocketAddress(InetAddress.getLocalHost(),
		// 5678));

		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.println("input sth");
			String str = wt.readLine();
			out.println(str);
			out.flush();
			if (str.equals("end")) {
				break;
			}
		}
		socket.close();
	}
}
