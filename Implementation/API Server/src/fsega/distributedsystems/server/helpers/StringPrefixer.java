package fsega.distributedsystems.server.helpers;

public class StringPrefixer {
	private static final String exceptionPrefix = "An exception occured while ";
	
	private StringPrefixer() {
		
	}
	
	public static String getFullString(String messageSuffix) {
		return exceptionPrefix + messageSuffix;
	}
}
