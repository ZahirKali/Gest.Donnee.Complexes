package tdb;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;

public class TDBModel {
	private static Model tdbModel;

	
	public TDBModel(){
		String directory = "D:/GIT/Gest.Donnee.Complexes/src/tdb/model";
		Dataset ds = TDBFactory.createDataset(directory) ;
        tdbModel = ds.getDefaultModel();
	}
	public static Model getTdbModel() {
		return tdbModel;
	}

	public void setTdbModel(Model tdbModel) {
		this.tdbModel = tdbModel;
	}
}
