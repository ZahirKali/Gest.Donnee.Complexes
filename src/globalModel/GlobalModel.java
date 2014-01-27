package globalModel;

import hbase.HBaseModel;
import hbase.HBaseUtils;
import tdb.TDBModel;
import tdb.TDBUtils;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class GlobalModel {
	private static GlobalModel instance;
	private static Model GModel;
	
	private GlobalModel(){
		TDBUtils tdb = new TDBUtils();
		HBaseUtils hb = new HBaseUtils();
		hb.ToHBModel("candidat");
		CreateModel(HBaseModel.getInstance(), TDBModel.getInstance());
	}

	public static GlobalModel getInstance(){
		if(instance ==null){
			return instance = new GlobalModel();
		}
		return instance;
	}
	
	public void CreateModel(final HBaseModel hbm, final TDBModel tdbm){
		if(GModel == null){
			GModel = ModelFactory.createDefaultModel();
		}
		if(GModel.isEmpty()){
			GModel = hbm.getHbaseModel();
			GModel.add(tdbm.getTdbModel());
			System.out.println("MODELS MERGED.....");
		}
	}

	public static Model getGModel() {
		return GModel;
	}

	public static void setGModel(Model gModel) {
		GModel = gModel;
	}
	public static void ToConsole(){
		GModel.write(System.out,"N-TRIPLE");
	}
	 public void runQuery(String query){
		   Query q = QueryFactory.create(query) ;
		    QueryExecution qexec = QueryExecutionFactory.create(query, GModel) ;
		    ResultSet results = qexec.execSelect() ;
		    ResultSetFormatter.out(results) ;	
      }
}
