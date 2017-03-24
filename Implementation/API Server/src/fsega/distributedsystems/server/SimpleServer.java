package fsega.distributedsystems.server;

import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import fsega.distributedsystems.server.helpers.StringPrefixer;

// http://tutorials.jenkov.com/java-multithreaded-servers/
public class SimpleServer implements Runnable {
	private static Logger logger = Logger.getLogger(SimpleServer.class.getName());
	private ExecutorService threadPool;
	private ServerSocket serverSocket;
	private boolean serverIsRunning;
	private int serverPort;
	
	private SimpleServer() {
		this(8080, 32);
	}
	
	private SimpleServer(int serverPort, int threadPoolSize) {
		this.serverPort = serverPort;
		this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
	}
	
	public static SimpleServer getInstance() {
		return new SimpleServer();
	}
	
	public static SimpleServer getInstance(int serverPort, int threadPoolSize) {
		return new SimpleServer(serverPort, threadPoolSize);
	}
	
	@Override
	public void run() {
		serverLoop();
	}
	
	private void serverLoop() {
		openServerSocket();
		
		while (serverIsRunning) {
			Socket clientSocket = null;
			
			try {
				clientSocket = serverSocket.accept();
				logger.info(String.format("Connection established with %s", clientSocket.getInetAddress().getHostAddress()));
			} catch (IOException e) {
				logger.log(Level.SEVERE, StringPrefixer.getFullString("trying to establish a connection"), e);
				continue;
			}
			
			threadPool.execute(new SimpleWorker(clientSocket));
		}
	}
	
	public void stopServer() {
		serverIsRunning = false;
		
		try {
			serverSocket.close();
			logger.info("Server stopped");
		} catch (IOException e) {
			serverIsRunning = true;
			logger.log(Level.SEVERE, StringPrefixer.getFullString("trying to stop the server"), e);
			return;
		}
		
		this.threadPool.shutdown();
		
	}
	
	private void openServerSocket() {
		serverIsRunning = true;
		
		try {
			serverSocket = new ServerSocket(serverPort);
			logger.info(String.format("Server is listening on port %d", serverPort));
		} catch (IOException e) {
			serverIsRunning = false;
			logger.severe(e.getMessage());
		}
	}

//	private String getFormattedEvent(String event, boolean insertStartingSymbol) {
//		String formatted = String.format("%s at %s", event, getCurrentTime());
//		
//		if (insertStartingSymbol) {
//			formatted = "[*] " + formatted;
//		}
//		
//		return formatted;
//	}
	
//	private String getCurrentTime() {
//		return ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
//	}
}
