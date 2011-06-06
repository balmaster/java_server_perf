package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BlockNAcceptMHandleServer extends AbstractServer implements
		Runnable {

	public BlockNAcceptMHandleServer(int port,
			AbstractHandlerFactory handlerFactory, int acceptThreadCount,
			int handleThreadCount) {
		super(port, handlerFactory);
		// TODO Auto-generated constructor stub
		this.acceptThreadCount = acceptThreadCount;
		this.handleThreadCount = handleThreadCount;
		acceptPool = Executors.newFixedThreadPool(acceptThreadCount);
		handlePool = Executors.newFixedThreadPool(handleThreadCount);

	}

	private final int acceptThreadCount;
	private final int handleThreadCount;

	private ServerSocket listSocket;

	private final ExecutorService acceptPool;
	private final ExecutorService handlePool;

	@Override
	public void execute() throws Exception {
		listSocket = new ServerSocket(getPort());
		for (int i = 0; i < acceptThreadCount; i++) {
			acceptPool.execute(this);
		}
		acceptPool.awaitTermination(-1, TimeUnit.SECONDS);
	}

	@Override
	public void run() {
		while (true) {

			try {
				final Socket socket = listSocket.accept();
				handlePool.execute(new Runnable() {

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
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
