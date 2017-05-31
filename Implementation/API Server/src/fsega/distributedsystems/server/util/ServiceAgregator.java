package fsega.distributedsystems.server.util;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

import fsega.distributedsystems.server.util.exceptions.NoSuchServiceException;

public class ServiceAgregator {
	private static Logger logger = Logger.getLogger(ServiceAgregator.class.getName());
	private static List<CachedResult> cachedResults = new ArrayList<CachedResult>();
	
	private static String fakeCompMeth1(String symbol1, String beginDate, String endDate, String exchange1, String exchange2) {
		return "10|aceasta valoare este cool";
	}
	
	private static String fakeCompMeth2(String symbol1, String symbol2, String beginDate, String endDate, String exchange) {
		return "20|aceasta valoare este si mai cool";
	}
	
	private ServiceAgregator() {
		
	}
	
	public static String getDataFromUrl(int cacheExpiration, ParsedUrl parsedUrl) throws NoSuchServiceException {
		if (cacheExpiration != 0) {
			try {
				CachedResult cachedResult = getCachedResult(parsedUrl);
				logger.info("Found cached result. Won't talk to services");
				return cachedResult.getCompMethodResult();
			} catch (Exception e) {
				// simply grab the a new result from the services
				logger.warning("No cached result was found" + cachedResults.size());
			}
		}
		
		String result = null;
		String serviceName = parsedUrl.getServiceName();
		
		if (!serviceName.equals("S1") && !serviceName.equals("S2")) {
			throw new NoSuchServiceException(String.format("Cannot provide results for service \"%s\"", serviceName));
		}
		
		switch (parsedUrl.getServiceName()) {
		case "S1":
			result = fakeCompMeth1(parsedUrl.getSymbol1(), parsedUrl.getBeginDate(), parsedUrl.getEndDate(), parsedUrl.getExtra(), parsedUrl.getExchange());
			break;
		case "S2":
			result = fakeCompMeth2(parsedUrl.getSymbol1(), parsedUrl.getExtra(), parsedUrl.getBeginDate(), parsedUrl.getEndDate(), parsedUrl.getExchange());
			break;
		}
		
		if (result != null && cacheExpiration != 0) {
			cachedResults.add(new CachedResult(cacheExpiration, parsedUrl, result));
		}
		
		return result;
	}
	
	private static CachedResult getCachedResult(ParsedUrl parsedUrl) throws Exception {
		List<CachedResult> cachedResultsCopy = new ArrayList<>(cachedResults);
		
		for (CachedResult cachedResult : cachedResultsCopy) {
			if (cachedResult.getParsedUrl().equals(parsedUrl)) {
				if (cachedResult.isExpired()) {
					cachedResults.remove(cachedResult);
					break;
				}
				return cachedResult;
			}
		}
		
		throw new Exception("No valid cached result is available");
	}
}
