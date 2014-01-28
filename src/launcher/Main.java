
package launcher;


import neo4j.Neo4jUtils;
import globalModel.INFModel;
import globalModel.Queries;
import hbase.HBaseUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
public class Main {
	
	public static void main(String[] args) throws Exception{
		Model results = ModelFactory.createDefaultModel();
		
		/**** D2RQ *****/
//		D2RQUtils d2rq = new D2RQUtils();
		

		/**** TDB:nom des depart. et leurs codes *****/
//	TDBUtils tdb = new TDBUtils();
//	tdb.toConsoleRDF();
//	tdb.runQuery(Queries.DepCode);
	
	/**** HBASE: nom des candidats *****/
//	HBaseUtils hb = new HBaseUtils();
//	hb.ToConsole();
//	hb.runQuery(Queries.HcandidateName);	
		
		/**** Neo4j:taux d'impots par regions *****/
//		Neo4jUtils neo4j = new Neo4jUtils();
//		neo4j.ToConsole();
//		neo4j.runQuery(Queries.Ntout);
		

		/**** INF MODEL *****/
//	INFModel inf = new INFModel();
//	inf.ToConsole();
	//Neo4j-Hbase: nombre de vois de Hollande  au 34 par bureaux
//	inf.runQuery(Queries.NHhollandeInMontp);
	
	//TDB-HBase-Neo4j: nombre de vois de Hollande  au 34 par bureaux
//	inf.runQuery(Queries.THNCommuneNameOf34);
	
		
}
}
