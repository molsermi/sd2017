package fsega.distributedsystems.server;


// http://tutorials.jenkov.com/java-multithreaded-servers/
public class ServerRunner {
	public static void main(String[] args) {
		SimpleServer server = SimpleServer.getInstance();
		Thread thread = new Thread(server);
		thread.start();
		
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		server.stopServer();
	}
}
