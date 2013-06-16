package code.github.snet.test.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * 
 * @author bhsc881114
 */
public class SingleThreadServer {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(5678);
		while (true) {
			Socket client = server.accept();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream());
				while (true) {
					System.out.println(client.getLocalPort());
					String str = in.readLine();
					System.out.println(str);
					out.println("has receive...." + str);
					out.flush();
					if (str.equals("end"))
						break;
				}
				client.close();
			} catch (Exception e) {
			}
		}
	}
}
