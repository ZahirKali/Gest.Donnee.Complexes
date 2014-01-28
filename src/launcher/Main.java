
package launcher;


import globalModel.Queries;
import tdb.TDBUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
public class Main {
	
	public static void main(String[] args) throws Exception{
		Model results = ModelFactory.createDefaultModel();
		
		/**** D2RQ *****/
//		D2RQUtils d2rq = new D2RQUtils();
		
		/**** Neo4j *****/
//		Neo4jUtils neo4j = new Neo4jUtils();
//		neo4j.runQuery(Queries.Ntout);
			
		/**** TDB *****/
	TDBUtils tdb = new TDBUtils();
	tdb.toConsoleRDF();
	tdb.runQuery(Queries.TMontpDepCode);
	
	/**** HBASE *****/
//	HBaseUtils hb = new HBaseUtils();
//	hb.runQuery(Queries.HbaseTdb);	
		
		/**** MODEL GLOBAL *****/
//	GlobalModel gmodel = GlobalModel.getInstance();	
//	
//	gmodel.runQuery(HbaseTdb);
		
		/**** INF MODEL *****/
//	INFModel inf = new INFModel();
//	inf.runQuery(Queries.HbaseTdb);
		
}
}
