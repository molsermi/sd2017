package fsega.distributedsystems.server.util;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fsega.distributedsystems.server.util.exceptions.ParameterNotFoundException;

public class ParsedUrl {
	// parameters should be non-static fields prefixed with parameterPrefix
	private String serviceName;
	private String p_beginDate;
	private String p_endDate;
	
	private String p_extra;
	private String p_symbol1;
	private String p_exchange;
	
	private static String parameterPatternTemplate = "(?<=%s\\=)\\w+(?=(&|$))";
	private static List<String> parameters = new ArrayList<String>();
	private static final String parameterPrefix = "p_";
	private static List<Pattern> parameterPatterns;

	static {
		// http://stackoverflow.com/a/3422435
		Field[] fields = ParsedUrl.class.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (field.getName().startsWith(parameterPrefix)) {
				parameters.add(fieldName.replaceFirst(parameterPrefix, "").toLowerCase());
			}
		}
	}
	
	public ParsedUrl(String serviceName, String beginDate, String endDate, String symbol1, String extra, String exchange) {
		this.serviceName = serviceName;
		this.p_beginDate = beginDate;
		this.p_endDate = endDate;
		
		this.p_extra = extra;
		this.p_symbol1 = symbol1;
		this.p_exchange = exchange;
	}
	
	public ParsedUrl(String serviceName, String[] parameterValues) {
		this(serviceName, parameterValues[0], parameterValues[1], parameterValues[2], parameterValues[3], parameterValues[4]);
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public String getBeginDate() {
		return p_beginDate;
	}
	
	public String getEndDate() {
		return p_endDate;
	}
	
	public String getSymbol1() {
		return p_symbol1;
	}
	
	public String getExtra() {
		return p_extra;
	}

	public String getExchange() {
		return p_exchange;
	}
	
	// url should be something like: S1?begindate=xx&enddate=xx&symbol1=xx&symbol2=xx&exchange=xx
	public static ParsedUrl fromRequestUrl(String url) throws ParameterNotFoundException {
		int serviceNameEndIndex = url.indexOf('?');
		String serviceName = url.substring(0, serviceNameEndIndex);
		
		String parametersOnlyUrl = url.substring(serviceNameEndIndex + 1);
		
		return new ParsedUrl(serviceName, extractParameterValues(parametersOnlyUrl));
	}
	
	private static String[] extractParameterValues(String parametersOnlyUrl) throws ParameterNotFoundException {
		List<String> extractedParameterValues = new ArrayList<>(parameters.size());
		
		if (parameterPatterns == null) {
			parameterPatterns = new ArrayList<Pattern>();
			
			for (String parameter : parameters) {
				parameterPatterns.add(Pattern.compile(String.format(parameterPatternTemplate, parameter)));
			}
		}
		
		int i = 0;
		for (Pattern pattern : parameterPatterns) {
			Matcher matcher = pattern.matcher(parametersOnlyUrl);
			if (!matcher.find()) {
				throw new ParameterNotFoundException("Something went wrong when trying to parse parameter " + parameters.get(i));
			}
			
			extractedParameterValues.add(matcher.group());
			i++;
		}
		
		return extractedParameterValues.toArray(new String[0]);
	}

	public boolean equals(ParsedUrl other) {
		return serviceName.equals(other.serviceName) && p_beginDate.equals(other.getBeginDate()) &&
			   p_endDate.equals(other.getEndDate()) && p_symbol1.equals(other.getSymbol1()) &&
			   p_extra.equals(other.getExtra()) && p_exchange.equals(other.getExchange());
	}
}
