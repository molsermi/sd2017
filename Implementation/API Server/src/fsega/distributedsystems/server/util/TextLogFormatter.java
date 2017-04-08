package fsega.distributedsystems.server.util;

import java.util.Date;
import java.util.logging.Handler;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class TextLogFormatter extends Formatter {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	@Override
	public String format(LogRecord record) {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(String.format("%s %s %s%n", dateFormat.format(record.getMillis()), 
										   record.getSourceClassName(), record.getSourceMethodName()));
		stringBuilder.append(String.format("%s %s%n", record.getLevel(), record.getMessage()));
		
		if (record.getThrown() != null) {
			Throwable thrown = record.getThrown();
			stringBuilder.append(String.format("%s: %s%n", thrown.getClass().getTypeName(), thrown.getMessage()));
			for (StackTraceElement stackTraceElement : thrown.getStackTrace()) {
				stringBuilder.append(String.format("\t at %s%n", stackTraceElement));
			}
		}
		
		stringBuilder.append(String.format("%n"));
		
		return stringBuilder.toString();
	}

	
	public String getHead(Handler h) {
		return String.format("Log started at %s %n%n", getCurrentDateString());
	}
	
    public String getTail(Handler h) {
        return String.format("Log ended at %s %n%n", getCurrentDateString());
    }
    
    private String getCurrentDateString() {
    	return dateFormat.format(new Date());
    }
}
