package tdb;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;


public class TDBUtils {
	private static Model tdbModel ;
	static TDBUtils singleton;
	
	public TDBUtils(){
		tdbModel = TDBModel.getInstance().getTdbModel();
		CreateOrReadTdbModel();
	}
	
	public Model getTdbModel(){
		return tdbModel;
	}
	
	private void CreateOrReadTdbModel(){
		if(tdbModel.isEmpty()){
			System.out.print("Creating model...");
			final String rdf_file_dep = "/home/sosso/GIT/Gest.Donnee.Complexes/resources/tdb/departement.rdf";
			final String rdf_file_reg = "/home/sosso/GIT/Gest.Donnee.Complexes/resources/tdb/region.rdf";
			
	        FileManager.get().readModel( tdbModel, rdf_file_dep );
	        FileManager.get().readModel( tdbModel, rdf_file_reg );
	        System.out.println(" OK");
		}
	}
	
	
	public void toConsole(){
    	System.out.println("Objets des triplets du modele TDB ");
    	System.out.println(" ================================= ");
    	StmtIterator stmt_i = tdbModel.listStatements();
    	while (stmt_i.hasNext())
    	{ 
    		Statement stmt = stmt_i.nextStatement();
    		RDFNode o = stmt.getObject();
    		if (o instanceof Resource)
	    	{ 
    			Resource or = (Resource) o; 
    			if (or.isAnon()) // noeud annonyme
    				System.out.println(" Noeud anonyme "+or.getId());
    			else
    				System.out.println(" Nom objet "+or.getLocalName());
    		}
    		else if (o.isLiteral())
	    	{	Literal ol = (Literal) o;
	    		System.out.println(" Valeur litteral "+ol.getLexicalForm());}
	    	}
	}
	
	public void toConsoleRDF(){
		tdbModel.write(System.out, "RDF/XML");
	}
	
	public void runQuery(String query){
		   Query q = QueryFactory.create(query) ;
		    QueryExecution qexec = QueryExecutionFactory.create(query, tdbModel) ;
		    ResultSet results = qexec.execSelect() ;
		    ResultSetFormatter.out(results) ;
	}
	}