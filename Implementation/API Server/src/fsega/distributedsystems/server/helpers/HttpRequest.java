package fsega.distributedsystems.server.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpRequest implements AutoCloseable {
	private String requestedUrl;
	private BufferedReader reader;
	
	public HttpRequest(InputStream clientInputStream) {
		String line = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(clientInputStream));
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("GET")) {
					requestedUrl = extractUri(line);
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String extractUri(String line) {
		String[] lineArray = line.split("\\s");
		return lineArray[1];
	}
	
	public String getRequestedUrl() {
		return requestedUrl;
	}

	@Override
	public void close() throws IOException {
		if (reader != null) {
			// will also close the underlying socket
			// http://stackoverflow.com/questions/484925/does-closing-the-bufferedreader-printwriter-close-the-socket-connection
			reader.close(); 
		}
	}
	
//	private static String extractRequestedResouce(String line) {
//		int lastSlashIndex = line.lastIndexOf('/');
//		return line.substring(lastSlashIndex);
//	}
}
 