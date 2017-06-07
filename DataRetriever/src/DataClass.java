 
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DataClass {
	Connection conn;
	Statement st;
	ResultSet result; 
	String sgbdName;
	//if there are no credential set for db
	public DataClass(String url, String className, String sgbdName){
		this(url, className, sgbdName, null, null);
	}
	//if there are credential set for db
	public DataClass(String url, String className, String sgbdName, String username, String password){
		this.sgbdName = sgbdName;
		if(username == null){
			username = "root";
		}
		if(password == null){
			password = "";
		}
		try{
			//name of the driver user for connection
			Class.forName(className);
			//establishing connection
			conn = DriverManager.getConnection(url, username, password);
			st = conn.createStatement();
			System.out.println(sgbdName + " database connection established");
			
		}
		catch (Exception e) {
			System.err.println("Cannot connect to " + sgbdName +  " database server"); 
            System.err.println(e.getMessage());
		}
	}
	//inserting test data into the databases
	public void insertData(String date, Double open, Double high, Double low, Double close, Double volume, Double adjClose, String symbol){
		try{
			String query = "";
//			if(sgbdName == "SQLServer"){
//				query = "INSERT INTO data (date,openm,high,low,closem,volume,adjclose,symbol) "
//						+ " VALUES (getdate(),'" + open + "','" + high + "','" + low + "','" + close + "','" + volume + "','" + adjClose + "','" + symbol + "')";
//			}
			if(sgbdName == "SQLServer"){
				query = "INSERT INTO data (date,openm,high,low,closem,volume,adjclose,symbol) "
						+ " VALUES (getdate()," + open + "," + high + "," + low + "," + close + "," + volume + "," + adjClose + ",'" + symbol + "')";
			}
//			else{
//				query = "INSERT INTO data (date,open,high,low,close,volume,adjclose,symbol) "
//					+ " VALUES (current_date,'" + open + "','" + high + "','" + low + "','" + close + "','" + volume + "','" + adjClose + "','" + symbol + "')";
//			}
			else{
			query = "INSERT INTO data (date,open,high,low,close,volume,adjclose,symbol) "
				+ " VALUES (current_date," + open + "," + high + "," + low + "," + close + "," + volume + "," + adjClose + ",'" + symbol + "')";
		}
			st.executeUpdate(query);
			System.out.println("Data has been inserted into " + sgbdName + " database");
		}
		catch (Exception e) {
			System.err.println("Cannot insert data into " + sgbdName + " database");
			System.err.println(e.getMessage());
		}
	}
	//closing connection
	public void closeConnection(){
		if (conn != null) {
          try {
              conn.close();
              System.out.println(sgbdName + " database connection terminated");
          } 
          catch (Exception e) {
          	System.err.println(e.getMessage());
          } 
		}
	}
}

