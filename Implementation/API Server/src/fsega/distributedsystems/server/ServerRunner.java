package fsega.distributedsystems.server;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.FileHandler;

import fsega.distributedsystems.server.util.NumericInterval;
import fsega.distributedsystems.server.util.TextLogFormatter;


// http://tutorials.jenkov.com/java-multithreaded-servers/
public class ServerRunner {
	private static Logger logger = Logger.getLogger(trimSubpackage(ServerRunner.class.getPackage().getName()));
	private static final String logFilePattern = "serverLog.log";
	
	public static void main(String[] args) throws SecurityException, IOException {
		FileHandler fileHandler = new FileHandler(logFilePattern, 10240, 1, false);
		fileHandler.setFormatter(new TextLogFormatter());
		logger.addHandler(fileHandler);
		
		int portIndex = findArgIndex(args, "--port");
		int threadsIndex = findArgIndex(args, "--threads");
		int cacheExpirationIndex = findArgIndex(args, "--cacheExpiration");
		
		int port = getCommandLineArgumentValue(args, portIndex, new NumericInterval<Integer>(0, 65535), 8080);
		int threads =  getCommandLineArgumentValue(args, threadsIndex, new NumericInterval<Integer>(1, 128), 32);
		int cacheExpiration = getCommandLineArgumentValue(args, cacheExpirationIndex, new NumericInterval<Integer>(0, Integer.MAX_VALUE), 0);
		
		SimpleServer server = SimpleServer.getInstance(port, threads, cacheExpiration);
		
		// if it's running in the development environment, the server will stop by itself after X seconds
		if (isDevEnvironment()) {
			new Thread(server).start();
			
			try {
				Thread.sleep(10000000);
			} catch (InterruptedException e) {
				e.printStackTrace();				
			}
			server.stopServer();
			
		} else {
			// http://zguide.zeromq.org/java:interrupt
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					server.stopServer();
				}
			}));
			
			server.run();
		}
	}
	
	private static boolean isDevEnvironment() {
		// http://stackoverflow.com/questions/2468059/how-to-detect-that-code-is-running-inside-eclipse-ide
		return System.getenv("eclipse463") != null;
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
	
	private static String trimSubpackage(String packageName) {
		return packageName.substring(0, packageName.lastIndexOf('.'));
	}
}
