package fsega.distributedsystems.server.util;

import fsega.distributedsystems.server.util.exceptions.NoSuchServiceException;

public class ServiceAgregator {
	private static String fakeCompMeth1(String symbol1, String beginDate, String endDate, String exchange1, String exchange2) {
		return "10|aceasta valoare este cool";
	}
	
	private static String fakeCompMeth2(String symbol1, String symbol2, String beginDate, String endDate, String exchange) {
		return "20|aceasta valoare este si mai cool";
	}
	
	private ServiceAgregator() {
		
	}
	
	public static String getDataFromUrl(ParsedUrl parsedUrl) throws NoSuchServiceException {
		String result = null;
		String serviceName = parsedUrl.getServiceName();
		
		if (!serviceName.equals("S1") && !serviceName.equals("S2")) {
			throw new NoSuchServiceException(String.format("Cannot provide results for service \"%s\"", serviceName));
		}
		
		switch (parsedUrl.getServiceName()) {
		case "S1":
			return fakeCompMeth1(parsedUrl.getSymbol1(), parsedUrl.getBeginDate(), parsedUrl.getEndDate(), parsedUrl.getExtra(), parsedUrl.getExchange());
		case "S2":
			return fakeCompMeth2(parsedUrl.getSymbol1(), parsedUrl.getExtra(), parsedUrl.getBeginDate(), parsedUrl.getEndDate(), parsedUrl.getExchange());
		}
		
		return result;
	}
}
