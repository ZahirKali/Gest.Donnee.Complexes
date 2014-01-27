package tdb;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;

public class TDBModel {
	private static Model tdbModel;
	private static TDBModel instance;
	private static Dataset ds; 
	
	public static TDBModel getInstance(){
		if(instance == null){
			instance = new TDBModel();
		}
		return instance;
	}
	
	private TDBModel(){
		String directory = "/home/sosso/GIT/Gest.Donnee.Complexes/src/tdb/model";
		ds = TDBFactory.createDataset(directory);
		ds.begin(ReadWrite.READ.WRITE);
		
        setTdbModel(ds.getDefaultModel());
	}
	
	public Model getTdbModel() {
		return tdbModel;
	}

	public void setTdbModel(Model tdbModel) {
		this.tdbModel = tdbModel;
	}
	
	public static Dataset getDs() {
		return ds;
	}

	public static void setDs(Dataset ds) {
		TDBModel.ds = ds;
	}

	public void close(){
		ds.close();
	}
}