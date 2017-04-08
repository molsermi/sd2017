package fsega.distributedsystems.server;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fsega.distributedsystems.server.util.HttpContentType;
import fsega.distributedsystems.server.util.HttpRequest;
import fsega.distributedsystems.server.util.HttpResponse;
import fsega.distributedsystems.server.util.HttpStatusCode;

// http://tutorials.jenkov.com/java-multithreaded-servers/
public class SimpleWorker implements Runnable {
	private Socket clientSocket;
	private String clientIpAddress;
	private static Logger logger = Logger.getLogger(SimpleWorker.class.getName());
	
	public SimpleWorker(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.clientIpAddress = clientSocket.getInetAddress().getHostAddress();
	}
	
	@Override
	public void run() {
		processClientRequest();
	}

	private void processClientRequest() {
		try (HttpRequest httpRequest = new HttpRequest(clientSocket.getInputStream());
			 PrintWriter responseWriter = new PrintWriter(clientSocket.getOutputStream())) {

			logger.info(String.format("%s sent: %s", clientIpAddress, httpRequest.getRequestedMethodLine()));
			responseWriter.print(new HttpResponse(HttpStatusCode.Http200, HttpContentType.Text, "hello world"));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Couldn't properly process client request", e);
		}
	}
	
}
