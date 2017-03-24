package fsega.distributedsystems.server;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fsega.distributedsystems.server.helpers.StringPrefixer;
import fsega.distributedsystems.server.helpers.HttpContentType;
import fsega.distributedsystems.server.helpers.HttpStatusCode;
import fsega.distributedsystems.server.helpers.HttpResponse;
import fsega.distributedsystems.server.helpers.HttpRequest;

// http://tutorials.jenkov.com/java-multithreaded-servers/
public class SimpleWorker implements Runnable {
	private Socket clientSocket;
	private static Logger logger = Logger.getLogger(SimpleWorker.class.getName());
	
	public SimpleWorker(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		processClientRequest();
	}

	private void processClientRequest() {
		try (HttpRequest httpRequest = new HttpRequest(clientSocket.getInputStream());
			 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream())) {

			logger.info(String.format("%s sent: %s", clientSocket.getInetAddress().getHostAddress(), 
									  httpRequest.getRequestedMethodLine()));
			writer.print(new HttpResponse(HttpStatusCode.Http200, HttpContentType.Text, "hello world"));
		} catch (IOException e) {
			logger.log(Level.SEVERE, StringPrefixer.getFullString("trying to process a client request"), e);
		}
	}
	
}
