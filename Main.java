
public class Main {

	public static void main(String[] args) {
		//in case of existing credentials, we set extra parameters: username&password
		DataClass mySQLUpdate = new DataClass("jdbc:mysql://localhost:3306/historicaldata", "com.mysql.jdbc.Driver", "MySQL");
		mySQLUpdate.insertData();
		mySQLUpdate.closeConnection();
		DataClass postgreSQLUpdate = new DataClass("jdbc:postgresql://localhost:5432/historicaldata", "org.postgresql.Driver", "PostgreSQL", "postgres", "postgresdb");
		postgreSQLUpdate.insertData();
		postgreSQLUpdate.closeConnection();
		DataClass msSQLServerUpdate = new DataClass("jdbc:sqlserver://EMMA-PC\\SQLEXPRESS;databaseName=historicaldata;integratedSecurity=true", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "SQLServer");
		msSQLServerUpdate.insertData();
		msSQLServerUpdate.closeConnection();
		
		
	}

}
