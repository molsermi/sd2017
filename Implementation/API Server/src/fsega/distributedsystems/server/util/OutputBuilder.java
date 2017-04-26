package fsega.distributedsystems.server.util;

import org.json.JSONObject;
import fsega.distributedsystems.server.util.exceptions.NoSuchServiceException;

public class OutputBuilder {
	// TODO: talk to services, get back objects and turn them into JSON
	public static String getJsonForParsedUrl(String url) throws Exception {
		final JSONObject jsonObject = new JSONObject();
		
		ParsedUrl parsedUrl = ParsedUrl.fromRequestUrl(url);
		
		String serviceName = parsedUrl.getServiceName();
		
		if (!(serviceName.equals("S1") || serviceName.equals("S2"))) {
			throw new NoSuchServiceException(String.format("Cannot provide results for request \"%s\"", url));
		}
		
		// TODO call the specified service with the parameters
		
		jsonObject.put("SERVICE_NAME", serviceName);
		
		// TODO use service supplied values
		jsonObject.put("SERVICE_CODE", serviceName);
		jsonObject.put("CORRELATION_COEFFIECIENT", 123);
		
		jsonObject.put("START_DATE", parsedUrl.getBeginDate());
		jsonObject.put("EXCHANGE", parsedUrl.getExchange());
		jsonObject.put("END_DATE", parsedUrl.getEndDate());
		jsonObject.put("SYM1", parsedUrl.getSymbol1());
		jsonObject.put("SYM2", parsedUrl.getSymbol2());
		
		return jsonObject.toString();
	}
}
