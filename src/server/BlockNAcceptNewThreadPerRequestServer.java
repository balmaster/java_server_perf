package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BlockNAcceptNewThreadPerRequestServer extends AbstractServer implements Runnable {

	public BlockNAcceptNewThreadPerRequestServer(int port,
			AbstractHandlerFactory handlerFactory,int acceptThreadCount) {
		super(port, handlerFactory);
		// TODO Auto-generated constructor stub
		this.acceptThreadCount = acceptThreadCount;
	}
	
	private int acceptThreadCount;
	
	private ServerSocket listSocket;

	@Override
	public void execute() throws Exception {
		listSocket = new ServerSocket(getPort());
		ExecutorService xec = Executors.newFixedThreadPool(acceptThreadCount);
		for(int i=0;i<acceptThreadCount;i++)
		{
			xec.execute(this);
		}
		xec.awaitTermination(-1,TimeUnit.SECONDS);
	}

	@Override
	public void run() {
		while (true) {
			
			try {
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
