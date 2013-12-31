package tdb;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;


public class tdbToModel {
	
	private static Model tdbModel ;
	private static Dataset ds;
	static tdbToModel singleton;
	
	private tdbToModel(){
		CreateOrReadTdbModel();
	}
	
	public Model getTdbModel(){
		return tdbModel;
	}
	
	public static synchronized tdbToModel getInstance(){
		if (singleton == null){
			singleton = new tdbToModel();
		}
		return singleton;
	}
	
	private void CreateOrReadTdbModel(){
			final String rdf_file_dep = "/home/sosso/GIT/Gest.Donnee.Complexes/resources/tdb/departement.rdf";
			final String rdf_file_reg = "/home/sosso/GIT/Gest.Donnee.Complexes/resources/tdb/region.rdf";
//			final String rdf_file_geoname = "/home/sosso/GIT/Gest.Donnee.Complexes/resources/tdb/geonames_v3.rdf";
			
			String directory = "D:/GIT/Gest.Donnee.Complexes/src/tdb/model";
			ds = TDBFactory.createDataset(directory) ;
			ds.begin(ReadWrite.READ.WRITE);
			tdbModel = ds.getDefaultModel() ;
			
	        FileManager.get().readModel( tdbModel, rdf_file_dep );
	        FileManager.get().readModel( tdbModel, rdf_file_reg );
//	        FileManager.get().readModel( tdbModel, rdf_file_geoname );
	}
	
	public void DsClose(){
		ds.end();
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
	
	public void toConsoleRDF(){
		tdbModel.write(System.out, "RDF/XML");
	}
	}
