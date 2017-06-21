package fsega.distributedsystems.server.util;


public class CachedResult {
	/**
	 * the amount of time in seconds for which the cached result is valid
	 */
	private int expiration;
	
	/**
	 * the time (in milliseconds) at which this cached item was created
	 */
	private long timestamp;
	
	/**
	 * the {@link ParsedUrl} instance that was used to call the computational method which 
	 * provided the raw result
	 */
	private ParsedUrl parsedUrl;
	
	/**
	 * the raw result returned by a computational method
	 */
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
	
	/**
	 * Checks if the cached result is still valid
	 * @return true, if the cached result is no longer valid considering the server's cache expiration settings
	 */
	public boolean isExpired() {
		long currentTimestamp = System.currentTimeMillis();
		return currentTimestamp - timestamp > expiration * 1000;
	}
}