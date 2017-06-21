package fsega.distributedsystems.server.util;

import java.util.List;
import java.util.ArrayList;
import java.net.URLDecoder;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.UnsupportedEncodingException;

import fsega.distributedsystems.server.util.exceptions.ParameterNotFoundException;

public class ParsedUrl {
	/**
	 *  parameters for which values are to be extracted from the request line, 
	 *  should be defined here as non-static fields prefixed with {@link ParsedUrl#parameterPrefix}
	 */
	private String serviceName;
	private String p_beginDate;
	private String p_endDate;
	
	private String p_extra;
	private String p_symbol1;
	private String p_exchange;
	
	/**
	 * regex string used to find parameter values in the request line
	 */
	private static String parameterPatternTemplate = "(?<=%s\\=)[\\w\\s-]+(?=(&|$))";
	private static List<String> parameters = new ArrayList<String>();
	private static final String parameterPrefix = "p_";
	
	/**
	 * holds regex patterns for each of the declared parameters
	 * (eg.: "(?<=begindate\\=)[\\w\\s-]+(?=(&|$))"
	 */
	private static List<Pattern> parameterPatterns;

	static {
		// http://stackoverflow.com/a/3422435
		
		/**
		 * finds the defined parameters. Value parsing will be tried on those parameters alone
		 */
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
	
	/**
	 * Gets a {@link ParsedUrl} instance from the provided URL
	 * @param url (eg.: S1?begindate=xx&enddate=xx&symbol1=xx&extra=xx&exchange=xx)
	 * @return a {@link ParsedUrl} instance, representing the parsed URL
	 * @throws ParameterNotFoundException if any of the defined parameters cannot be found within the URL
	 */
	public static ParsedUrl fromRequestUrl(String url) throws ParameterNotFoundException {
		int serviceNameEndIndex = url.indexOf('?');
		String serviceName = url.substring(0, serviceNameEndIndex);
		
		String parametersOnlyUrl = null;
		
		try {
			parametersOnlyUrl = URLDecoder.decode(url.substring(serviceNameEndIndex + 1), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		return new ParsedUrl(serviceName, extractParameterValues(parametersOnlyUrl));
	}
	
	private static String[] extractParameterValues(String parametersOnlyUrl) throws ParameterNotFoundException {
		List<String> extractedParameterValues = new ArrayList<>(parameters.size());
		
		if (parameterPatterns == null) {
			parameterPatterns = new ArrayList<Pattern>();
			
			/**
			 *  creates regex patterns for each of the declared parameters
			 *  (eg.: "(?<=begindate\\=)[\\w\\s-]+(?=(&|$))"
			 */
			for (String parameter : parameters) {
				parameterPatterns.add(Pattern.compile(String.format(parameterPatternTemplate, parameter)));
			}
		}
		
		int i = 0;
		/**
		 *  try to extract the value of each parameter from the partial URL
		 *  (eg.: for parameter 'begindate', value = 'xx' )
		 */
		for (Pattern pattern : parameterPatterns) {
			Matcher matcher = pattern.matcher(parametersOnlyUrl);
			if (!matcher.find()) {
				throw new ParameterNotFoundException(String.format("Couldn't parse the mandatory parameter '%s'", parameters.get(i)));
			}
			
			// add each extracted value to the list
			extractedParameterValues.add(matcher.group());
			i++;
		}
		
		return extractedParameterValues.toArray(new String[0]);
	}

	/**
	 * Compares two {@link ParsedUrl} instances
	 * @param other a {@link ParsedUrl} instance used in the comparison
	 * @return true, if this and the provided instance have identical underlying values
	 */
	public boolean equals(ParsedUrl other) {
		return serviceName.equals(other.serviceName) && p_beginDate.equals(other.getBeginDate()) &&
			   p_endDate.equals(other.getEndDate()) && p_symbol1.equals(other.getSymbol1()) &&
			   p_extra.equals(other.getExtra()) && p_exchange.equals(other.getExchange());
	}
}
