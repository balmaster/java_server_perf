package server;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		try {
			//new NewThreadPerRequestServer(port, new EchoHandlerFactory()).execute();
			//new BlockNAcceptNewThreadPerRequestServer(port, new EchoHandlerFactory(),8).execute();
			new BlockNAcceptMHandleServer(port, new EchoHandlerFactory(),2,10).execute();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
