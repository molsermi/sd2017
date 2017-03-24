package fsega.distributedsystems.server.helpers;

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
