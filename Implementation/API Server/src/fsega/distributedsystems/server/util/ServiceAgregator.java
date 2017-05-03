package fsega.distributedsystems.server.util;

import fsega.distributedsystems.server.util.exceptions.NoSuchServiceException;

public class ServiceAgregator {
	private static String fakeCompMeth1(String beginDate, String endDate, String symbol1, String symbol2, String exchange) {
		return "10|aceasta valoare este cool";
	}
	
	private static String fakeCompMeth2(String beginDate, String endDate, String symbol1, String symbol2, String exchange) {
		return "20|aceasta valoare este si mai cool";
	}
	
	private ServiceAgregator() {
		
	}
	
	public static String getDataFromUrl(ParsedUrl parsedUrl) throws NoSuchServiceException {
		String result = null;
		String serviceName = parsedUrl.getServiceName();
		
		if (!(serviceName.equals("S1") || serviceName.equals("S2"))) {
			throw new NoSuchServiceException(String.format("Cannot provide results for service \"%s\"", serviceName));
		}
		
		switch (parsedUrl.getServiceName()) {
		case "S1":
			return fakeCompMeth1(parsedUrl.getBeginDate(), parsedUrl.getEndDate(), parsedUrl.getSymbol1(), parsedUrl.getSymbol2(), parsedUrl.getExchange());
		case "S2":
			return fakeCompMeth2(parsedUrl.getBeginDate(), parsedUrl.getEndDate(), parsedUrl.getSymbol1(), parsedUrl.getSymbol2(), parsedUrl.getExchange());
		}
		
		return result;
	}
}
