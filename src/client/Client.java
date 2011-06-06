package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import common.PerfCounter;
import common.PerfCounterGroup;

public class Client {

	protected static PerfCounterGroup group = new PerfCounterGroup("CLIENT");
	protected static PerfCounter beginRequest = new PerfCounter(group, "BEGIN",
			true);
	protected static PerfCounter errorRequest = new PerfCounter(group, "ERROR",
			true);
	protected static PerfCounter successRequest = new PerfCounter(group,
			"SUCCESS", true);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String host = args[0];
		final int port = Integer.parseInt(args[1]);
		final int threadCount = Integer.parseInt(args[2]);
		final int commandCount = Integer.parseInt(args[3]);
		final int sampleCount = Integer.parseInt(args[4]);

		ExecutorService xec = Executors.newFixedThreadPool(threadCount);
		for (int i = 0; i < threadCount; i++) {
			xec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						for (int i = 0; i < commandCount; i++) {
							try {
								beginRequest.incCount();

								Socket socket = new Socket(host, port);
								try {
									InputStreamReader in = new InputStreamReader(
											new BufferedInputStream(socket
													.getInputStream(), socket
													.getSendBufferSize()),
											"utf8");
									OutputStreamWriter out = new OutputStreamWriter(
											new BufferedOutputStream(socket
													.getOutputStream(), socket
													.getReceiveBufferSize()),
											"utf8");
									char[] buffer = new char[1024];
									int count;

									String request = "Hello World";

									for (int j = 0; j < sampleCount; j++) {
										out.write(request);
										out.flush();

										StringBuilder sb = new StringBuilder();
										while (sb.length() < request.length()
												&& (count = in.read(buffer)) != -1) {
											sb.append(buffer, 0, count);
										}
									}
									successRequest.incCount();
								} finally {
									socket.close();
								}
							} catch (SocketException e) {
								errorRequest.incCount();
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
		}
		try {
			xec.awaitTermination(-1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
