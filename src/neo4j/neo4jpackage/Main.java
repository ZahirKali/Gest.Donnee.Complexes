package neo4jpackage;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class Main {
    /*
     * Main
     */
    public static void main( final String[] args )
    {
    	Model m = ModelFactory.createDefaultModel();
    	//System.out.println("1");
    	Neo4jCreateGraph n = new Neo4jCreateGraph();
    	//System.out.println("2");
    	n.createNode();
    	//System.out.println("3");
    	n.printNeoFriends(m);
    	n.shutDown();
    }

}
