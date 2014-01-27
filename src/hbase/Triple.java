package hbase;

import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class Triple<First, Seconde, Third> {
	First Subject;
	Seconde Predicate;
	Third Object;
	
	public First getSubject() {
		return Subject;
	}
	public void setSubject(First subject) {
		Subject = subject;
	}
	public Seconde getPredicate() {
		return Predicate;
	}
	public void setPredicate(Seconde predicate) {
		Predicate = predicate;
	}
	public Third getObject() {
		return Object;
	}
	public void setObject(Third object) {
		Object = object;
	}
	
}
