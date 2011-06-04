package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

public class EchoHandler extends AbstractHandler {
	private Socket socket;
	public EchoHandler(Socket socket)
	{
		this.socket=socket;
	}
	
	@Override
	public void run() {
		InputStreamReader in;
		try {
			in = new InputStreamReader(socket.getInputStream(), "utf8");
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(),"utf8");
			char[] buffer = new char[1024];
			int count;
			while((count = in.read(buffer)) != -1)
			{	
				out.write(buffer,0,count);
				out.flush();
			}
		} catch(SocketException e) {
			//e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
