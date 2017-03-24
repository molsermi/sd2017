package fsega.distributedsystems.server;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;

import fsega.distributedsystems.server.helpers.HttpContentType;
import fsega.distributedsystems.server.helpers.HttpStatusCode;
import fsega.distributedsystems.server.helpers.HttpResponse;
import fsega.distributedsystems.server.helpers.HttpRequest;

// http://tutorials.jenkov.com/java-multithreaded-servers/
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
		
		try (HttpRequest httpRequest = new HttpRequest(clientSocket.getInputStream());
			 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream())) {
			
			System.out.println(httpRequest.getRequestedUrl());
			writer.print(new HttpResponse(HttpStatusCode.Http200, HttpContentType.Text, "hello world"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
