package globalModel;

import hbase.HBaseUtils;
import neo4j.Neo4jUtils;
import tdb.TDBUtils;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

public class INFModel {
	private Model tdbModel;
	private Model hbaseModel;
	private Model neo4jModel;
	 
	private InfModel IModel;
	
	public INFModel(){
		TDBUtils tdb = new TDBUtils();
		tdbModel = tdb.getTdbModel();
		
		HBaseUtils hb = new HBaseUtils();
		hbaseModel = hb.getHBModel().getInstance().getHbaseModel();
		
		
		Neo4jUtils neo4j = new Neo4jUtils();
		neo4j.getNeo4jModel();
		
		CreateInfModel();
	}
	
	public void CreateInfModel(){
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(tdbModel);
		IModel = ModelFactory.createInfModel(reasoner, hbaseModel);
	}
	
	public void ToConsole(){
		IModel.write(System.out,"N-TRIPLE");
	}
	
	 public void runQuery(String query){
		 System.out.println("Quering InfModel...");
		   Query q = QueryFactory.create(query) ;
		    QueryExecution qexec = QueryExecutionFactory.create(query, IModel) ;
		    ResultSet results = qexec.execSelect() ;
		    ResultSetFormatter.out(results) ;	
    }
	
}
