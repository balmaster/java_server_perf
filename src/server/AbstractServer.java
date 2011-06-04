package server;

public abstract class AbstractServer {
	private int port;
	public int getPort() {
		return port;
	}
	private void setPort(int port) {
		this.port = port;
	}
	
	private AbstractHandlerFactory handlerFactory; 
	
	public AbstractHandlerFactory getHandlerFactory() {
		return handlerFactory;
	}
	private void setHandlerFactory(AbstractHandlerFactory handleFactory) {
		this.handlerFactory = handleFactory;
	}
	public AbstractServer(int port,AbstractHandlerFactory handlerFactory)
	{
		setPort(port);
		setHandlerFactory(handlerFactory);
	}
	
	public abstract void execute() throws Exception;
}
