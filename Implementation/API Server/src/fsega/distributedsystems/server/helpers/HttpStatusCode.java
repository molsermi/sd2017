package fsega.distributedsystems.server.helpers;

public enum HttpStatusCode {
	Http200 ("200 OK"),
	Http404 ("404 Not Found");
	
	private final String statusCode;
	
	private HttpStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
}
