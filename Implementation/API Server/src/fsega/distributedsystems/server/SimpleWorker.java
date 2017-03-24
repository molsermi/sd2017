package fsega.distributedsystems.server;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;

import fsega.distributedsystems.server.helpers.HttpContentType;
import fsega.distributedsystems.server.helpers.HttpStatusCode;
import fsega.distributedsystems.server.helpers.HttpResponse;

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

		try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream())) {
			writer.print(new HttpResponse(HttpStatusCode.Http200, HttpContentType.Text, "hello world"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
