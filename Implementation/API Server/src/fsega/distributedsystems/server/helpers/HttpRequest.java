package fsega.distributedsystems.server.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpRequest implements AutoCloseable {
	private String requestedUrl;
	private BufferedReader reader;
	private String requestedMethodLine;
	
	public HttpRequest(InputStream clientInputStream) throws IOException {
		String line = null;
		reader = new BufferedReader(new InputStreamReader(clientInputStream));
		
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("GET")) {
				requestedUrl = extractUri(line);
				requestedMethodLine = line;
				break;
			}
		}
	}
	
	private static String extractUri(String line) {
		String[] lineArray = line.split("\\s");
		return lineArray[1];
	}
	
	public String getRequestedUrl() {
		return requestedUrl;
	}
	
	public String getRequestedMethodLine() {
		return requestedMethodLine;
	}

	@Override
	public void close() throws IOException {
		// will also close the underlying socket
		// http://stackoverflow.com/questions/484925/does-closing-the-bufferedreader-printwriter-close-the-socket-connection
		reader.close(); 
	}
	
//	private static String extractRequestedResouce(String line) {
//		int lastSlashIndex = line.lastIndexOf('/');
//		return line.substring(lastSlashIndex);
//	}
}
 