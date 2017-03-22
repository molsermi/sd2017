package fsega.distributedsystems.server;

import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

// http://tutorials.jenkov.com/java-multithreaded-servers/singlethreaded-server.html
public class SimpleServer implements Runnable {
//	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	private ServerSocket serverSocket;
	private boolean serverIsRunning;
//	private Thread currentThread;
	private int serverPort;
	
	private SimpleServer() {
		this(8080);
	}
	
	private SimpleServer(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public static SimpleServer getInstance() {
		return new SimpleServer();
	}
	
	public static SimpleServer getInstance(int serverPort) {
		return new SimpleServer(serverPort);
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
			
			try {
				processClientRequest(clientSocket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	private void processClientRequest(Socket clientSocket) throws IOException {
		if (clientSocket == null) {
			return;
		}
		
		String header = "HTTP/1.1 200 OK\n\n";
		String message = "hello world";
		try (OutputStream outputStream = clientSocket.getOutputStream()) {
			outputStream.write((header + String.format("<head><body>%s</body></head>", message)).getBytes());
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
