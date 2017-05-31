package fsega.distributedsystems.server;

import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

// http://tutorials.jenkov.com/java-multithreaded-servers/
public class SimpleServer implements Runnable {
	private static Logger logger = Logger.getLogger(SimpleServer.class.getName());
	
	private ExecutorService threadPool;
	private ServerSocket serverSocket;
	private boolean running;
	private int serverPort;
	private int cacheExpiration;
	
	private SimpleServer(int serverPort, int threadPoolSize, int cacheExpiration) {
		this.serverPort = serverPort;
		this.cacheExpiration = cacheExpiration;
		this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
		
		if (cacheExpiration == 0) {
			logger.warning("Cache expiration was set to 0, caching is disabled");
		} else {
			logger.info(String.format("Result caching is enabled. Cached results will expire every %d seconds", cacheExpiration));
		}
	}
	
	public static SimpleServer getInstance(int serverPort, int threadPoolSize, int cacheExpiration) {
		return new SimpleServer(serverPort, threadPoolSize, cacheExpiration);
	}
	
	@Override
	public void run() {
		serverLoop();
	}
	
	private void serverLoop() {
		running = true;
		openServerSocket();
		
		while (running) {
			Socket clientSocket = null;
			
			try {
				clientSocket = serverSocket.accept();
				logger.info("Connection established with " + clientSocket.getInetAddress().getHostAddress());
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Couldn't establish connection", e);
				continue;
			}
			
			threadPool.execute(new SimpleWorker(clientSocket, cacheExpiration));
		}
	}
	
	public void stopServer() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Couldn't properly stop the server", e);
			return;
		}
		
		running = false;
		threadPool.shutdown();
		logger.info("Server stopped");
	}
	
	private void openServerSocket() {
		try {
			serverSocket = new ServerSocket(serverPort);
			logger.info(String.format("Server is listening on port %d", serverPort));
		} catch (IOException e) {
			running = false;
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
