package fsega.distributedsystems.server;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fsega.distributedsystems.server.util.ParsedUrl;
import fsega.distributedsystems.server.util.HttpRequest;
import fsega.distributedsystems.server.util.HttpResponse;
import fsega.distributedsystems.server.util.OutputBuilder;
import fsega.distributedsystems.server.util.HttpStatusCode;
import fsega.distributedsystems.server.util.HttpContentType;
import fsega.distributedsystems.server.util.ServiceAgregator;

// http://tutorials.jenkov.com/java-multithreaded-servers/
public class SimpleWorker implements Runnable {
	private Socket clientSocket;
	private int cacheExpiration;
	private String clientIpAddress;
	private static Logger logger = Logger.getLogger(SimpleWorker.class.getName());
	
	public SimpleWorker(Socket clientSocket, int cacheExpiration) {
		this.clientSocket = clientSocket;
		this.cacheExpiration = cacheExpiration;
		this.clientIpAddress = clientSocket.getInetAddress().getHostAddress();
	}
	
	@Override
	public void run() {
		processClientRequest();
	}
	
	/**
	 * This method reads the HTTP request, gets it parsed, gets a result from the 
	 * the service agregator, gets it serialized, then builds it a HTTP response based on the serialized data,
	 * which is then written to client as a HTTP response
	 */
	private void processClientRequest() {
		try (HttpRequest httpRequest = new HttpRequest(clientSocket.getInputStream());
			 PrintWriter responseWriter = new PrintWriter(clientSocket.getOutputStream())) {

			logger.info(String.format("%s sent: %s", clientIpAddress, httpRequest.getRequestLine()));
			
			String requestedUrl = httpRequest.getRequestedUrl();
			
			// ignore favicon request
			if (requestedUrl.equals("favicon.ico")) {
				return;
			}
			
			HttpResponse httpResponse = null;
			String jsonOutput = null;
			
			try {
				ParsedUrl parsedUrl = ParsedUrl.fromRequestUrl(requestedUrl);
				String serviceResult = ServiceAgregator.getDataFromUrl(cacheExpiration, parsedUrl);
				
				jsonOutput = OutputBuilder.getJsonForServiceResult(parsedUrl, serviceResult);
				httpResponse = new HttpResponse(HttpStatusCode.Http200, HttpContentType.Json, jsonOutput);
				
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Couldn't get JSON result", e);
				httpResponse = new HttpResponse(HttpStatusCode.Http404, HttpContentType.Text, 
												String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage()));
			}
			
			responseWriter.print(httpResponse);
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Couldn't properly process client request", e);
		}
	}
}
