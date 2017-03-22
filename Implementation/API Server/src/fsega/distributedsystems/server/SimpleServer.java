package fsega.distributedsystems.server;

import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// http://tutorials.jenkov.com/java-multithreaded-servers
public class SimpleServer implements Runnable {
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
		startServer();
	}
	
	private void startServer() {
		openServerSocket();
		
		System.out.println(getFormattedEvent("Server started", false));
		
		while (serverIsRunning) {
			Socket clientSocket = null;
			
			try {
				clientSocket = serverSocket.accept();
				System.out.println(getFormattedEvent("Established connection", true));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			threadPool.execute(new SimpleWorker(clientSocket));
		}
	}
	
	public void stopServer() {
		serverIsRunning = false;
		
		if (serverSocket == null) {
			return;
		}
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.threadPool.shutdown();
		System.out.println(getFormattedEvent("Server stopped", false));
	}
	
	private void openServerSocket() {
		serverIsRunning = true;
		
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getFormattedEvent(String event, boolean insertStartingSymbol) {
		String formatted = String.format("%s at %s", event, getCurrentTime());
		
		if (insertStartingSymbol) {
			formatted = "[*] " + formatted;
		}
		
		return formatted;
	}
	
	private String getCurrentTime() {
		return ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
	}
}
