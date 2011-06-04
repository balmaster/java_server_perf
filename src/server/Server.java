package server;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		try {
			new NewThreadPerRequestServer(port, new EchoHandlerFactory()).execute();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
