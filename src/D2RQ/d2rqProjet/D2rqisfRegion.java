package d2rqProjet;

import java.io.FileNotFoundException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;


public class D2rqisfRegion {

	 public static final String NL = System.getProperty("line.separator") ;
	   public static void main(String[] args) throws FileNotFoundException
	   {
	        
	        //creer le modele D2RQ
	        Model d2rqModel = new ModelD2RQ("file:mappingRegion.n3");
	        String region ="http://www.lirmm.fr/region";
	        
	        
	         
	        String r = "PREFIX r: <"+region+">" ;
	        
	        
	       
	         String queryString = r	 + NL + 
	                 "SELECT DISTINCT ?nomregion  ?isfregion "
	         		+ "WHERE {?n r:nomRegion ?nomregion ." 
	         		+ "?i r:isfmoyenRegion ?isfregion }";
	         		
	        
	        
	        
	     // affichage de la requete sparql
	         System.out.println(queryString);
	                 
	         Query query = QueryFactory.create(queryString);
	           
	         // afficher la requete
	        // query.serialize(new IndentedWriter(System.out,true)) ;
	         System.out.println() ;
	      
	         QueryExecution qexec = QueryExecutionFactory.create(query, d2rqModel) ;
	      

	         System.out.println("Les elements du modele sont: ") ;

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
