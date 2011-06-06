package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

public class EchoHandler extends AbstractHandler {
	private Socket socket;
	public EchoHandler(Socket socket)
	{
		this.socket=socket;
	}
	
	@Override
	public void run() {
		try {
			beginRequest.incCount();
			InputStreamReader in = new InputStreamReader(
					new BufferedInputStream(socket.getInputStream(),socket.getReceiveBufferSize()), "utf8");
			OutputStreamWriter out = new OutputStreamWriter(
					new BufferedOutputStream(socket.getOutputStream(),socket.getSendBufferSize()),"utf8");
			char[] buffer = new char[1024];
			int count;
			while((count = in.read(buffer)) != -1)
			{	
				out.write(buffer,0,count);
				out.flush();
			}
			successRequest.incCount();
		} catch(SocketException e) {
			//e.printStackTrace();
			errorRequest.incCount();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
