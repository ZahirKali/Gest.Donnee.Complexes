package D2R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class D2RQUtils {
	private Model d2rqModel;
	private Connection conn;
	private Statement stmt;
	
	
	public D2RQUtils(){
		String region ="http://www.lirmm.fr/region";
        
        
        
        String r = "PREFIX r: <"+region+">" ;
        
        
       
         String queryString = r +
                 "Select * where {?s ?o ?p }";
         
		d2rqModel = new ModelD2RQ("file:/home/sosso/GIT/Gest.Donnee.Complexes/src/D2R/mappingRegion.n3");
		runQuery(queryString);
	}
	
	public void connect() throws ClassNotFoundException, SQLException{
		Class.forName ("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/gmin332", "root", "");
		try {
			stmt = conn.createStatement();			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public void runQuery(String queryString){
        Query query = QueryFactory.create(queryString) ;
        System.out.println() ;
        QueryExecution qexec = QueryExecutionFactory.create(query, d2rqModel) ;
        System.out.println("Les elements du modele : ") ;
        try {
            ResultSet rs = qexec.execSelect() ;
            ResultSetFormatter.out(System.out, rs, query);
        }
        finally
        {
            qexec.close() ;
        }
      }
	
	
}
