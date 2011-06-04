package server;

import java.net.Socket;

public abstract class AbstractHandlerFactory {
	public abstract AbstractHandler createHandler(Socket socket);
}
