

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.QueryParam;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

	public Map<String, Object> getClichedMessage(@QueryParam("symbol") String symbol,
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) throws Exception{

			ReadJson readJson = new ReadJson();
			JSONObject obj = readJson.getJson(symbol, startDate, endDate);
			readJson.parseJson(obj);
			
			Map<String, Object> jsonMap = new HashMap<>();
			jsonMap.put("results", readJson.getObjects());
			return jsonMap;
	}
	
	public static void main(String[] args) throws Exception{
		//in case of existing credentials, we set extra parameters: username&password
//		String Symbol = "YHOO";
//		String startDate = "2016-05-05";
//		String endDate = "2017-05-05";
//        URL url = new URL("http://localhost:8090/getYahooFinanceData?symbol=" + Symbol + 
//        		"&startDate=" + startDate + "&endDate=" + endDate);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Accept", "application/json");
//        InputStream inputStream = conn.getInputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> jsonMap = mapper.readValue(inputStream, Map.class);
//		ArrayList array = (ArrayList) jsonMap.get("results");
        ReadJson test = new ReadJson();
        // symbol , startDate, endDate
        JSONObject obj= test.getJson("NOKIA.HE", "2016-01-02", "2017-01-02");
        test.parseJson(obj);
        ArrayList<DataEntry> array = new ArrayList();
        array= test.getObjects();
        
		DataClass mySQLUpdate = new DataClass("jdbc:mysql://localhost:3306/historicaldata", "com.mysql.jdbc.Driver", "MySQL");
		DataClass postgreSQLUpdate = new DataClass("jdbc:postgresql://localhost:5432/historicaldata", "org.postgresql.Driver", "PostgreSQL", "postgres", "postgresdb");
		DataClass msSQLServerUpdate = new DataClass("jdbc:sqlserver://EMMA-PC\\SQLEXPRESS;databaseName=historicaldata;integratedSecurity=true", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "SQLServer");
		
		for (DataEntry object : array) {
			String date = object.getDate(); 
			Double open = getDouble(object.getOpen());
			Double high = getDouble(object.getHigh());
			Double low = getDouble(object.getLow());
			Double close = getDouble(object.getClose());
			Double volume = getDouble(object.getVolume());
			Double adjClose = getDouble(object.getAdjClose());
			String symbol  = object.getSymbol();
			mySQLUpdate.insertData(date, open, high, low, close, volume, adjClose, symbol);
			postgreSQLUpdate.insertData(date, open, high, low, close, volume, adjClose, symbol);
			msSQLServerUpdate.insertData(date, open, high, low, close, volume, adjClose, symbol);
			//System.out.println(object);
		} 
		
		
		mySQLUpdate.closeConnection();
		postgreSQLUpdate.closeConnection();
		msSQLServerUpdate.closeConnection();
	}
	
	private static Double getDouble(String value) {
		if (value != null)
			return Double.parseDouble(value);
		else
			return null;
	}

}
