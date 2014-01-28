package D2R;

import java.sql.*;

import com.hp.hpl.jena.vocabulary.RDF;
class Conn {
  public static void main (String[] args) throws Exception
  {
   Class.forName ("com.mysql.jdbc.Driver");

   Connection conn = DriverManager.getConnection
     ("jdbc:mysql://localhost/gmin332", "root", "");
                        
   try {
     Statement stmt = conn.createStatement();
     try {
       ResultSet rset = stmt.executeQuery("select * from regionisf");
       try {
         while (rset.next())
           System.out.println (rset.getString(1));
         System.out.println ("REUSSIIIII"); 
         System.out.println(RDF.getURI());
       } 
       finally {
          try { rset.close(); } catch (Exception ignore) {}
       }
     } 
     finally {
       try { stmt.close(); } catch (Exception ignore) {}
     }
   } 
   finally {
     try { conn.close(); } catch (Exception ignore) {}
   }
  }
}