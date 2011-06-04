package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NewThreadPerRequestServer extends AbstractServer {

	public NewThreadPerRequestServer(int port,
			AbstractHandlerFactory handlerFactory) {
		super(port, handlerFactory);
		// TODO Auto-generated constructor stub
	}

	private ServerSocket listSocket;

	@Override
	public void execute() throws Exception {
		listSocket = new ServerSocket(getPort());
		while (true) {
			final Socket socket = listSocket.accept();
			new Thread(new Runnable() {

				@Override
				public void run() {
					AbstractHandler handler = getHandlerFactory()
							.createHandler(socket);
					try {
						handler.run();
					} finally {
						try {
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).run();
		}

	}

}
