package fsega.distributedsystems.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Processes HTTP requests
 * @author sidf
 *
 */
public class HttpRequest implements AutoCloseable {
	private String requestLine;
	private String requestedUrl;
	private BufferedReader reader;
	
	public HttpRequest(InputStream clientInputStream) throws IOException {
		String line = null;
		reader = new BufferedReader(new InputStreamReader(clientInputStream));
		
		while ((line = reader.readLine()) != null) {
			// loop through the HTTP request text, until the request line is found (eg.: GET /some/url HTTP/1.1")
			if (line.startsWith("GET")) {
				requestedUrl = extractUri(line);
				requestLine = line;
				break;
			}
		}
	}
	
	/**
	 * Extracts the requested URL from HTTP request lines
	 * @param line a request line from a HTTP request (eg.: GET /some/url HTTP/1.1")
	 * @return a string, representing the requested URL, without the leading slash (eg.: some/url)
	 */
	private static String extractUri(String line) {
		String[] lineArray = line.split("\\s");
		return lineArray[1].replaceFirst("/", "");
	}
	
	public String getRequestedUrl() {
		return requestedUrl;
	}
	
	public String getRequestLine() {
		return requestLine;
	}

	/**
	 * Closes the (HTTP request) input stream. Will also close the underlying client socket as a side effect
	 */
	@Override
	public void close() throws IOException {
		// https://stackoverflow.com/a/484939
		reader.close(); 
	}
}
 