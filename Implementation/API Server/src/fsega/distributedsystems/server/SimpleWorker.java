package fsega.distributedsystems.server;

import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;

// http://tutorials.jenkov.com/java-multithreaded-servers
public class SimpleWorker implements Runnable {
	private Socket clientSocket;
	
	public SimpleWorker(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		processClientRequest();
	}

	private void processClientRequest() {
		if (clientSocket == null) {
			return;
		}
		
		String header = "HTTP/1.1 200 OK\n\n";
		String message = "hello world";
		try (OutputStream outputStream = clientSocket.getOutputStream()) {
			outputStream.write((header + String.format("<head><body>%s</body></head>", message)).getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
