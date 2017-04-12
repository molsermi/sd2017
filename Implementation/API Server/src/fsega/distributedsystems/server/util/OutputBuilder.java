package fsega.distributedsystems.server.util;

import org.json.JSONObject;

import fsega.distributedsystems.server.util.exceptions.NoSuchServiceException;

public class OutputBuilder {
	// TODO: talk to services, get back objects and turn them into JSON
	public static String getJsonForUrl(String url) throws Exception {
		final JSONObject jsonObject = new JSONObject();
		
		if (url.equals("S1")) {
			jsonObject.put("VALUE", "3.14");
		} else if (url.equals("S2")) {
			jsonObject.put("VALUE", "14.3");
		} else{ 
			throw new NoSuchServiceException(String.format("Cannot provide results for service %s", url));
		}
		
		jsonObject.put("SERVICE_NAME", url);
		return jsonObject.toString();
	}
}
