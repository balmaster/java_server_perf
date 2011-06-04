package server;

import java.net.Socket;

public class EchoHandlerFactory extends AbstractHandlerFactory {

	@Override
	public AbstractHandler createHandler(Socket socket) {
		return new EchoHandler(socket);
	}

}
