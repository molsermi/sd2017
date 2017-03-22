package fsega.distributedsystems.server;

// http://tutorials.jenkov.com/java-multithreaded-servers/
public class ServerRunner {
	public static void main(String[] args) {
		int portIndex = findArgIndex(args, "--port");
		int threadsIndex = findArgIndex(args, "--threads");
		
		int port = getCommandLineArgumentValue(args, portIndex, 8080);
		int threads =  getCommandLineArgumentValue(args, threadsIndex, 32);
		
		SimpleServer server = SimpleServer.getInstance(port, threads);
		Thread thread = new Thread(server);
		thread.start();
		
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		server.stopServer();
	}
	
	private static int getCommandLineArgumentValue(String[] args, int itemIndex, int defaultValue) {
		if (itemIndex != -1 && itemIndex + 1 < args.length) {
			return Integer.valueOf(args[itemIndex + 1]);
		}
		
		return defaultValue;
	}
	
	private static int findArgIndex(String[] args, String item) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(item)) {
				return i;
			}
		}
		
		return -1;
	}
}
