package client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String host = args[0];
		final int port = Integer.parseInt(args[1]);
		final int threadCount = Integer.parseInt(args[2]);
		final int commandCount = Integer.parseInt(args[3]);

		for (int i = 0; i < threadCount; i++) {
			Thread clientThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						for (int i = 0; i < commandCount; i++) {
							try {
								Socket socket = new Socket(host, port);

								try {
									InputStreamReader in = new InputStreamReader(
											socket.getInputStream(), "utf8");
									OutputStreamWriter out = new OutputStreamWriter(
											socket.getOutputStream(), "utf8");
									char[] buffer = new char[1024];
									int count;

									String request = "Hello World";

									out.write(request);
									out.flush();

									StringBuilder sb = new StringBuilder();
									while (sb.length() < request.length()
											&& (count = in.read(buffer)) != -1) {
										sb.append(buffer, 0, count);
									}
								} finally {
									socket.close();
								}
							} catch (SocketException e) {

							}
							// System.out.println(sb);
						}

					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			clientThread.run();
			try {
				clientThread.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
