package fsega.distributedsystems.server.util;

/**
 * Builds a standard HTTP response, with the given HTTP status code, content type and body
 * @author sidf
 *
 */
public class HttpResponse {
	private String response;
	
	public HttpResponse(HttpStatusCode statusCode, HttpContentType contentType, String body) {
		StringBuilder responseSb = new StringBuilder();
		
		responseSb.append(String.format("HTTP/1.1 %s\r\n", statusCode.getStatusCode()));
		responseSb.append(String.format("Content-Type: %s\r\n", contentType.getContentType()));
		responseSb.append("\r\n\r\n");
		responseSb.append(body);
		
		response = responseSb.toString();
	}
	
	@Override
	public String toString() {
		return response;
	}
}
