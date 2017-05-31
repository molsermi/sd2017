package fsega.distributedsystems.server.util;

public class CachedResult {
	// the amount of time in seconds for which the cached result is valid
	private int expiration;
	
	private long timestamp;
	private ParsedUrl parsedUrl;
	
	private double correlation;
	private String interpretation;
	
	public CachedResult(int expiration, long timestamp, ParsedUrl parsedUrl, double correlation, String interpretation) {
		this.expiration = expiration;
		
		this.timestamp = timestamp;
		this.parsedUrl = parsedUrl;
		this.correlation = correlation;
		this.interpretation = interpretation;
	}
	
	public ParsedUrl getParsedUrl() {
		return parsedUrl;
	}
	
	public double getCorrelation() {
		return correlation;
	}
	
	public String getInterpretation() {
		return interpretation;
	}
	
	public boolean isExpired() {
		long currentTimestamp = System.currentTimeMillis();
		return expiration * 1000 + timestamp > currentTimestamp;
	}
}