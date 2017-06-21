package fsega.distributedsystems.server.util;

import org.json.JSONObject;

/**
 * Builds JSON from parsed computational method results
 * @author sidf
 *
 */
public class OutputBuilder {
//	public static String getJsonForUrl(String url) throws ParameterNotFoundException, NoSuchServiceException {
//		final JSONObject jsonObject = new JSONObject();
//		
//		ParsedUrl parsedUrl = ParsedUrl.fromRequestUrl(url);
//		
//		String serviceResult = ServiceAgregator.getDataFromUrl(parsedUrl);
//		String[] splitResult = serviceResult.split("\\|");
//		
//		String coefficient = splitResult[0];
//		String interpretation = splitResult[1];
//		
//		String serviceName = parsedUrl.getServiceName();
//		
//		jsonObject.put("SERVICE_NAME", serviceName);
//		jsonObject.put("SERVICE_CODE", serviceName);
//		
//		jsonObject.put("START_DATE", parsedUrl.getBeginDate());
//		jsonObject.put("EXCHANGE", parsedUrl.getExchange());
//		jsonObject.put("END_DATE", parsedUrl.getEndDate());
//		jsonObject.put("SYM1", parsedUrl.getSymbol1());
//		jsonObject.put("SYM2", parsedUrl.getSymbol2());
//		
//		jsonObject.put("CORRELATION_COEFFIECIENT", coefficient);
//		jsonObject.put("INTERPRETATION", interpretation);
//		
//		return jsonObject.toString();
//	}
	
	/**
	 * Generates a JSON string from the given parameters
	 * @param parsedUrl data used to build the JSON string
	 * @param serviceResult string that represents the 'coefficient' in the generated JSON
	 * @return a string, representing the JSON serialized parameters
	 */
	public static String getJsonForServiceResult(ParsedUrl parsedUrl, String serviceResult) {
		final JSONObject jsonObject = new JSONObject();
		
		// String[] splitResult = serviceResult.split("\\|");
		
		//String coefficient = splitResult[0];
		//String interpretation = splitResult[1];
		
		String coefficient = serviceResult;
		
		String serviceName = parsedUrl.getServiceName();
		
		jsonObject.put("SERVICE_NAME", serviceName);
		jsonObject.put("SERVICE_CODE", serviceName);
		
		jsonObject.put("START_DATE", parsedUrl.getBeginDate());
		jsonObject.put("EXCHANGE", parsedUrl.getExchange());
		jsonObject.put("END_DATE", parsedUrl.getEndDate());
		jsonObject.put("SYM1", parsedUrl.getSymbol1());
		jsonObject.put("EXTRA", parsedUrl.getExtra());
		
		jsonObject.put("CORRELATION_COEFFIECIENT", coefficient);
		
		// jsonObject.put("INTERPRETATION", interpretation);
		
		return jsonObject.toString();
	}
}
