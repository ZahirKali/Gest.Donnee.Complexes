import org.openjena.atlas.io.IndentedWriter;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.vocabulary.RDF;	

public class D2R_Un
{
	 public static final String NL = System.getProperty("line.separator") ;
   public static void main(String[] args)
   {
         
         //creer le modele D2RQ
        Model d2rqModel = new ModelD2RQ("file:mapN3.n3");
        String virus ="http://www.lirmm.fr/virus#";
     	String prolog_e = "PREFIX virus: <"+virus+">" ;
        String prolog_r = "PREFIX rdf: <"+RDF.getURI()+">" ;
        
         // requete SPARQL
         String queryString = prolog_e + NL + prolog_r + NL +
             "SELECT ?virus  ?transmission "  
             + "WHERE {?virus rdf:type virus:Virus ." +
             " ?virus virus:Virus_Transmission ?transmission}" ;
         
         Query query = QueryFactory.create(queryString) ;
         // afficher la requete
         query.serialize(new IndentedWriter(System.out,true)) ;
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
