package code.github.snet.test.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 每连接每线程模型
 */
public class MultiThreadServer extends Thread {
	private Socket client;

	public MultiThreadServer(Socket c) {
		this.client = c;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream());
			while (true) {
				System.out.println(client.getLocalPort() + ","
						+ client.getPort() + "," + client.getLocalAddress()
						+ "," + client.getRemoteSocketAddress());
				String str = in.readLine();
				System.out.println(str);
				out.println("has receive....");
				out.flush();
				if (str.equals("end"))
					break;
			}
			client.close();
		} catch (IOException ex) {
		} finally {

		}
	}

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(5678);
		int i = 0;
		while (true) {
			i++;
			if (i > 3) {
				server.close();
			}
			MultiThreadServer mu = new MultiThreadServer(server.accept());
			mu.start();
		}
	}
}