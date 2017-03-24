package fsega.distributedsystems.server;

import fsega.distributedsystems.server.helpers.NumericInterval;

// http://tutorials.jenkov.com/java-multithreaded-servers/
public class ServerRunner {
	public static void main(String[] args) {
		int portIndex = findArgIndex(args, "--port");
		int threadsIndex = findArgIndex(args, "--threads");
		
		int port = getCommandLineArgumentValue(args, portIndex, new NumericInterval<Integer>(0, 65535), 8080);
		int threads =  getCommandLineArgumentValue(args, threadsIndex, new NumericInterval<Integer>(1, 64), 32);
		
		System.out.printf("Port: %d%nThread count: %d%n", port, threads);
		
		SimpleServer server = SimpleServer.getInstance(port, threads);
		Thread thread = new Thread(server);
		thread.start();
		
		try {
			Thread.currentThread().sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		server.stopServer();
	}
	
	private static int getCommandLineArgumentValue(String[] args, int itemIndex, NumericInterval<Integer> interval, 
												   int defaultValue) {
		if (itemIndex != -1 && itemIndex + 1 < args.length && args[itemIndex + 1].matches("^\\d+$")) {
			int value = Integer.valueOf(args[itemIndex + 1]);
			return interval.isInRange(value) ? value : defaultValue;
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
