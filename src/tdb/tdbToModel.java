package tdb;

import com.hp.hpl.jena.query.Dataset ;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;


public class tdbToModel {
	private static Model tdbModel;
	private static tdbToModel singleton;
	
	
	public tdbToModel(){
		CreateTdbModel();
	}
	
	public static tdbToModel getInstance(){
		if (singleton == null){
			singleton = new tdbToModel();
		}
		return singleton;
	}
	
	private void CreateTdbModel(){
		final String rdf_file = "file:///D:/GIT/Gest.Donnee.Complexes/resources/tdb/departement.rdf";
        String directory = "D:/GIT/Gest.Donnee.Complexes/src/tdb/model" ;
        Dataset ds = TDBFactory.createDataset(directory) ;
        tdbModel = ds.getDefaultModel() ;
        FileManager.get().readModel( tdbModel, rdf_file );
	}
	
	public void toConsole(){
    	System.out.println("Objets des triplets du modele TDB ");
    	System.out.println(" ========================== ");
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
	
	public void toConsoleJena(){
		tdbModel.write(System.out, "RDF/XML");
	}
	}
