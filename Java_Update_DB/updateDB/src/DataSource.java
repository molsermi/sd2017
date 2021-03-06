import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class DataSource {

	public static void main(String[] args) {
		// symbol will receive the name of the xlsx file
		// the xlsx file must be saved in the folder UpdateDB
		final String symbol="F";
		HashMap<Integer, List<String>> map = readFromXlsx(symbol);
		System.out.println(map);
		post(map, symbol);

	}

	/** 
	 * Inserts the data in DB.
	 * @param map with the values from xlsx file
	 * @param symbol name of the xlsx file
	 */
	public static void post(HashMap<Integer, List<String>> map, final String symbol) {
		//Creates the connection
		Connection connection = getConnection();
		try {
			final int mapSize=map.size();
			for(int i=1; i<=mapSize;i++){
				List<String> list = new ArrayList<>();
				list=map.get(i);
				
				//Parse of the string into date
				String source=list.get(0);
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				java.sql.Date d = new java.sql.Date(format.parse(source).getTime());
				
				//StringBuffer with the float values from the xlsx file
				StringBuffer insert = new StringBuffer();
				for (int index = 1; index < 7; index++) {
					insert.append(","+list.get(index));
				}
				insert.append(",'"+symbol+"'");
				
				//Gets the last ID inserted into DB
				Statement statementId = (Statement) connection.createStatement();
				String query = "Select * from historicaldata.data where iddata = (select max(iddata) from historicaldata.data);";
				ResultSet res = statementId.executeQuery(query);
				res.next();
				int lastId = res.getInt("iddata");
				
				//Prepare statement for Insert into DB
				PreparedStatement statement = (PreparedStatement)connection.prepareStatement("INSERT INTO data (iddata,date,open,high,low,close,volume,adjclose,symbol) VALUES (" + (lastId+1)+ ",'" + d + "'" + insert + ")");
				 statement.getLastInsertID();
				 statement.executeUpdate();
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		} finally {
			System.err.println("Insert done!");
		}

	}

	/**
	 * Creates connection with the Local DB.
	 * @return the connection
	 */
	public static Connection getConnection() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/historicaldata";
			String username = "root";
			String password = "mocaapp";

			Class.forName(driver);
			Connection connection = DriverManager.getConnection(url, username, password);
			System.err.println("Connected!");

			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads from the xlsx file and creates a HashMap with the read values.
	 * @param xlsx
	 * @return HashMap with the values that will be inserted in DB
	 */
	public static HashMap<Integer, List<String>> readFromXlsx(String xlsxFile) {
		try {
			FileInputStream file = new FileInputStream(new File(xlsxFile + ".xlsx"));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			//Formatter for date
			DataFormat df = workbook.createDataFormat();
			CellStyle dateStyle = workbook.createCellStyle();
			dateStyle.setDataFormat(df.getFormat("dd/MM/yyyy"));

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			int keyMap = 0;
			HashMap<Integer, List<String>> resultContent = new HashMap<Integer, List<String>>();
			
			while (rowIterator.hasNext()) {
				List<String> list = new ArrayList<>();
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = ((Row) row).cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					//Every csv has the first row filled with the name of the columns
					if (cell.getRowIndex() != 0) {
						// In the first column is the date value
						// We use the date formatter
						if (cell.getColumnIndex() == 0) {
							cell.setCellStyle(dateStyle);
						}

						DataFormatter formatter = new DataFormatter();
						String cellContent = formatter.formatCellValue(cell);
						list.add(cellContent);

					}
				}
				// We don't add the first row (the name of the columns)
				if (keyMap != 0) {
					list.add(xlsxFile);
					resultContent.put(keyMap, list);
				}
				keyMap++;
			}
			file.close();
			return resultContent;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
