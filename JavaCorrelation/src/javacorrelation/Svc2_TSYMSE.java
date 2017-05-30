
package javacorrelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/* Serviciul 2 se refera la compararea a doua tirtluri(simboluri) diferite pe aceeasi piata
(OSYMDE => T = two, SYM = symbol, S = same, E = exchange).*/

public class Svc2_TSYMSE {
 
        
    /* Parametrii din cadrul metodei getSvc2 sunt introdusi de catre user. Astfel, pentru determinarea 
    corelatiei a doua titluri diferite pe aceeasi piata utilizatorului i se vor solicita urmatoarele info :
    denumirea celor doua titluri de analizat (nameSym1, nameSym2), limita inferioara si superioara a intervalului de tim de analizat
    (startDate, endDate), denumirea pietei (exc). */
    
    public static double getSvc2(String nameSym1, String nameSym2, Date startDate, Date endDate, String exc) throws ParseException
    {
                          
         java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime()); 
   
         java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
         
         ArrayList<Float> closeS1 = new ArrayList<Float>();
         ArrayList<Float> closeS2 = new ArrayList<Float>();
         
        try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         Connection conn = java.sql.DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=HistoricalData;user=sa;password=nevertheless");
        
         PreparedStatement ps1 = conn.prepareStatement("select * from Symbols where date between ? and ? " +
                                              "and nameSym = ? and idExc in (select idExc from Exchanges where nameExc = ?)");
         ps1.setDate(1, sqlStartDate);
         ps1.setDate(2, sqlEndDate);
         ps1.setString(3, nameSym1);
         ps1.setString(4, exc);
         ResultSet rs1 = ps1.executeQuery();

         while (rs1.next()) {
             closeS1.add(rs1.getFloat(7));
         }
         
         PreparedStatement ps2 = conn.prepareStatement("select * from Symbols where date between ? and ? " +
                                                     "and nameSym = ? and idExc in (select idExc from Exchanges where nameExc = ?)");
         ps2.setDate(1, sqlStartDate);
         ps2.setDate(2, sqlEndDate);
         ps2.setString(3, nameSym2);
         ps2.setString(4, exc);
         ResultSet rs2 = ps2.executeQuery();

         while (rs2.next()) {
             closeS2.add(rs2.getFloat(7));
         }
        
      } catch (Exception e) {
         e.printStackTrace();
      }
        System.out.print("\n\nValorile close ale " + nameSym1 + " pe piata din " + exc);
        for(int i=0;i<closeS1.size();i++)
        {
        System.out.print(": " + closeS1.get(i) + ", ");
       }  
        System.out.print("\nValorile close ale " + nameSym2 + " pe piata din " + exc);
        
        for(int i=0;i<closeS1.size();i++)
        {
        System.out.print(": " + closeS2.get(i) + ", ");
    }
        
    double pearsonCoefficient = PearsonCorrelation.getCorrelation(closeS1,closeS2);
        return pearsonCoefficient;
    }
}
