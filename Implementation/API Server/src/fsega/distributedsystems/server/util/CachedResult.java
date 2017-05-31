package fsega.distributedsystems.server.util;

public class CachedResult {
	// the amount of time in seconds for which the cached result is valid
	private int expiration;
	
	private long timestamp;
	private ParsedUrl parsedUrl;
	private String compMethodResult;
	
	public CachedResult(int expiration, ParsedUrl parsedUrl, String compMethodResult) {
		this.parsedUrl = parsedUrl;
		this.expiration = expiration;
		this.compMethodResult = compMethodResult;
		
		this.timestamp = System.currentTimeMillis();
	}
	
	public ParsedUrl getParsedUrl() {
		return parsedUrl;
	}
	
	public String getCompMethodResult() {
		return compMethodResult;
	}
	
	public boolean isExpired() {
		long currentTimestamp = System.currentTimeMillis();
		return currentTimestamp - timestamp > expiration * 1000;
	}
}