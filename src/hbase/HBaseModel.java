package hbase;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class HBaseModel {
	private static Model hbaseModel;
	private static HBaseModel singleton;
	private static final String NS = "http://localhost:9000/hbase#";
	
	public static HBaseModel getInstance(){
		if(singleton == null){
			return singleton = new HBaseModel();
		}
		return singleton;
	}
	
	private HBaseModel(){
		CreateModel();
	}
	public static Model getHbaseModel() {
		return hbaseModel;
	}

	
	public static void setHbaseModel(Model hbaseModel) {
		HBaseModel.hbaseModel = hbaseModel;
	}

	public void CreateModel(){
		if(hbaseModel == null){
			hbaseModel = ModelFactory.createDefaultModel();
		}
	}
	
	public Resource CreateResource(String resourceName){
		if(hbaseModel.containsResource(hbaseModel.createResource(NS+resourceName))){
		return hbaseModel.getResource(resourceName);
		}
		return hbaseModel.createResource(NS+resourceName);
	}
	
	
	public Property CreateProperty(String propertyName){
		return hbaseModel.createProperty(NS+propertyName);
	}
	
	public Literal CreateLiteral(String literal){
		return hbaseModel.createLiteral(literal);
	}
	
	public void AddTriple(Resource subject, Property predicate, Literal object){
		hbaseModel.add(subject, predicate, object);
	}
	
	public static void ToConsole(){
        hbaseModel.write(System.out,"N-TRIPLE");
	}
}
