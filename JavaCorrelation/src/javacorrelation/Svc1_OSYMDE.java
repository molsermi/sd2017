package javacorrelation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/* Serviciul 1 se refera la compararea aceluiasi titlu(simbol) pe doua piete diferite 
(OSYMDE => O = one, SYM = symbol, D = different, E = exchanges).*/

public class Svc1_OSYMDE {
    
    /* Parametrii din cadrul metodei getSvc1 sunt introdusi de catre user. Astfel, pentru determinarea 
    corelatiei a unui titlu pe doua piete diferite utilizatorului i se vor solicita urmatoarele info :
    denumirea titlului de analizat (nameSym), limita inferioara si superioara a intervalului de tim de analizat
    (startDate, endDate), denumirea celor doua piete (exc1, exc2). */
    
    public static double getSvc1(String nameSym, Date startDate, Date endDate, String exc1, String exc2) throws ParseException
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
         ps1.setString(3, nameSym);
         ps1.setString(4, exc1);
         ResultSet rs1 = ps1.executeQuery();

         while (rs1.next()) {
             closeS1.add(rs1.getFloat(7));
         }
         
         PreparedStatement ps2 = conn.prepareStatement("select * from Symbols where date between ? and ? " +
                                                     "and nameSym = ? and idExc in (select idExc from Exchanges where nameExc = ?)");
         ps2.setDate(1, sqlStartDate);
         ps2.setDate(2, sqlEndDate);
         ps2.setString(3, nameSym);
         ps2.setString(4, exc2);
         ResultSet rs2 = ps2.executeQuery();

         while (rs2.next()) {
             closeS2.add(rs2.getFloat(7));
         }
        
      } catch (Exception e) {
         e.printStackTrace();
      }
        System.out.print("Valorile close ale " + nameSym + " pe piata din " + exc1);
        for(int i=0;i<closeS1.size();i++)
        {
        System.out.print(": " + closeS1.get(i) + ", ");
       }  
        System.out.print("\nValorile close ale " + nameSym + " pe piata din " + exc2);
        
        for(int i=0;i<closeS1.size();i++)
        {
        System.out.print(": " + closeS2.get(i) + ", ");
    }
        
    double pearsonCoefficient = PearsonCorrelation.getCorrelation(closeS1,closeS2);
        return pearsonCoefficient;
    }
   
}
