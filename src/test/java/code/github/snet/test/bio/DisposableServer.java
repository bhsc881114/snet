package code.github.snet.test.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * simple demo,just accept one connect
 * 
 * @author bhsc881114
 */
public class DisposableServer {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(5678);
		// synchronous & block
		Socket client = server.accept();

		// 相比较与nio的buffer形式，以前的io确实麻烦不少
		// 要获取到socket的InputStream和OutputStream
		BufferedReader in = new BufferedReader(new InputStreamReader(
				client.getInputStream()));
		PrintWriter out = new PrintWriter(client.getOutputStream());

		while (true) {
			String str = in.readLine();
			System.out.println(str);
			out.println("has receive...." + str);
			out.flush();
			if (str.equals("end"))
				break;
		}
		client.close();
		server.close();
	}
}
