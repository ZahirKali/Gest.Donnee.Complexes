//package neo4jpackage;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//
//
//import org.neo4j.graphdb.Direction;
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.Node;
//import org.neo4j.graphdb.Path;
//import org.neo4j.graphdb.RelationshipType;
//import org.neo4j.graphdb.Transaction;
//import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//import org.neo4j.graphdb.traversal.Evaluators;
//import org.neo4j.graphdb.traversal.TraversalDescription;
//import org.neo4j.graphdb.traversal.Traverser;
//import org.neo4j.kernel.Traversal;
//import org.neo4j.kernel.Uniqueness;
//import org.neo4j.kernel.impl.util.FileUtils;
//
//import com.hp.hpl.jena.rdf.model.Model;
//import com.hp.hpl.jena.rdf.model.ModelFactory;
//import com.hp.hpl.jena.rdf.model.ModelFactoryBase;
//import com.hp.hpl.jena.rdf.model.Property;
//import com.hp.hpl.jena.rdf.model.Resource;
//
///**
// * @author Nordine, sofiane
// */
//
//public class Neo4jCreateGraph {
//    
//	private static final String DB_PATH = "/home/nordine/workspace/java/Neo4j-projet/GraphDb";
//	private long NodeId;
//	
//    GraphDatabaseService graphDb;
//    Node firstNode;
//    Node secondNode;
//    
//  
//    /*
//     * Methode to clear DB
//     */
//    private void clearDb()
//    {
//        try
//        {
//            FileUtils.deleteRecursively( new File( DB_PATH ) );
//        }
//        catch ( IOException e )
//        {
//            throw new RuntimeException( e );
//        }
//    }
//    
//    
//    /*
//     * Methode to remove DB
//     */
//    public void removeData()
//    {
//    	System.out.println("Neo4jCreateGraph.removeData()");
//    	Transaction tx = graphDb.beginTx();
//        try 
//        {
//            // START SNIPPET: removingData
//            // let's remove the data
//            firstNode.getSingleRelationship( RelTypes.VOISIN, Direction.OUTGOING ).delete();
//            firstNode.delete();
//            secondNode.delete();
//            // END SNIPPET: removingData
//
//            tx.success();
//        } catch (Exception e) {
//            tx.failure();
//        } finally {
//            tx.finish();
//        }
//    }
//    
//
//    /*
//     * Methode to shut down DB
//     */
//    void shutDown()
//    {
//        
//        System.out.println( "Shutting down database ..." );
//        graphDb.shutdown();
//      
//    }
//    
//	
//    /*
//     * Methode Get the First Node
//     */
//	private Node getFirst() { 
//		return graphDb.getNodeById(NodeId)
//		.getSingleRelationship( RelTypes.DONNEE, Direction.OUTGOING)
//		.getEndNode();
//		}
//	
//	
//	/*
//	 * Methode Get the relation graphe 
//	 */
//	private static Traverser getAmis(Node firstNode) {
//		TraversalDescription td = Traversal.description()
//		.breadthFirst()
//		.relationships(RelTypes.VOISIN, Direction.OUTGOING)
//		.evaluator(Evaluators.excludeStartPosition())
//		.uniqueness(Uniqueness.NODE_GLOBAL);
//		return td.traverse(firstNode);
//		}
//	
//	
//	/*
//	 * Methode print Graph to RDF Model
//	 */
//	public void printNeoFriends(Model m) {
//		String taxe ="http://www.taxe-foncier.com#";
//		m.setNsPrefix("taxe-foncier", taxe);
//		
//		
//		
//		
//		//Property Name = m.createProperty(taxe+"Name");
//		Property Base = m.createProperty(taxe+"Basenette");
//		Property Taux = m.createProperty(taxe+"Taux");
//		
//		Node firstNode = getFirst();
//		//Resource sujet = m.createResource(taxe+"taxe");
//		//Resource Region = m.createResource(taxe+"Region");
//
//		Traverser friendsTraverser = getAmis(firstNode); // noeuds parcourus
//		for (Path friendPath : friendsTraverser) {
//			
//			
//			Resource NameRegion = m.createResource(taxe+friendPath.endNode().getProperty( "name" ));
//			Resource BaseNetteRegion = m.createResource(taxe+friendPath.endNode().getProperty( "Basenette" ));
//			Resource TauxRegion = m.createResource(taxe+friendPath.endNode().getProperty( "taux" ));
//			
//			//Region.addProperty(Name, NameRegion);
//			
//			//NameRegion.addProperty(Name, sujet);
//			NameRegion.addProperty(Base, BaseNetteRegion);
//			NameRegion.addProperty(Taux, TauxRegion);
//			
//			
//			 
//				
//				}
//		 m.write(System.out, "RDF/XML");
//		 
//			try {       
//				FileOutputStream outStream = new FileOutputStream("Neo4jToRdf.rdf");
//			
//				//exporte le resultat dans un fichier
//				m.write(outStream, "RDF/XML");
//				outStream.close();
//			}
//			catch (FileNotFoundException e) {System.out.println("File not found");}
//			catch (IOException e) {System.out.println("IO problem");}
//
//		 
//		 
//		}
//    
//   
//	
//	/*
//	 * Methode to Create Node
//	 */
//    public  void createNode() {
//    	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
//    	Transaction tx = graphDb.beginTx();
//    	
//        try {
//        	System.out.println("Neo4jCreateGraph.createNode()");
//        
//        	Node TaxeFoncier = graphDb.createNode(); // création du point daccès au graphe
//    		NodeId = TaxeFoncier.getId();
//    		
//    		
//    		Node Region = graphDb.createNode(); // création du 2éme point 
//    		Region.setProperty("name", "Region"); 
//    		TaxeFoncier.createRelationshipTo(Region, RelTypes.DONNEE);
//
//        	
//            // Creation des Noeuds
//            Node ALSACE  = graphDb.createNode();
//            ALSACE.setProperty("name", "ALSACE");
//            ALSACE.setProperty("Basenette", "1 948 196 507");
//            ALSACE.setProperty("taux", "2,01");
//            Region.createRelationshipTo(ALSACE, RelTypes.VOISIN);
//            
//     
//            Node AQUITAINE  = graphDb.createNode();
//            AQUITAINE.setProperty("name", "AQUITAINE");
//            AQUITAINE.setProperty("Basenette", "3 203 377 570");
//            AQUITAINE.setProperty("taux", "3,17");
//            
//            Node AUVERGNE  = graphDb.createNode();
//            AUVERGNE.setProperty("name", "AUVERGNE");
//            AUVERGNE.setProperty("Basenette", "1 237 218 606");
//            AUVERGNE.setProperty("taux", "5,29");
//            
//            Node BASSE_NORMANDIE  = graphDb.createNode();
//            BASSE_NORMANDIE.setProperty("name", "BASSE_NORMANDIE");
//            BASSE_NORMANDIE.setProperty("Basenette", "1 289 602 827");
//            BASSE_NORMANDIE.setProperty("taux", "3,17");
//             
//            Node BOURGOGNE  = graphDb.createNode();
//            BOURGOGNE.setProperty("name", "BOURGOGNE");
//            BOURGOGNE.setProperty("Basenette", "1 552 084 680");
//            BOURGOGNE.setProperty("taux", "3,65");
//            
//            Node BRETAGNE  = graphDb.createNode();
//            BRETAGNE.setProperty("name", "BRETAGNE");
//            BRETAGNE.setProperty("Basenette", "2 823 263 276");
//            BRETAGNE.setProperty("taux", "2,97");
//            
//            Node CENTRE  = graphDb.createNode();
//            CENTRE.setProperty("name", "CENTRE");
//            CENTRE.setProperty("Basenette", "2 289 668 205");
//            CENTRE.setProperty("taux", "3,47");
//            
//            Node CHAMPAGNE_ARDENNE  = graphDb.createNode();
//            CHAMPAGNE_ARDENNE.setProperty("name", "CHAMPAGNE_ARDENNE");
//            CHAMPAGNE_ARDENNE.setProperty("Basenette", "1 153 211 196");
//            CHAMPAGNE_ARDENNE.setProperty("taux", "3,63");
//            
//            Node CORSE  = graphDb.createNode();
//            CORSE.setProperty("name", "CORSE");
//            CORSE.setProperty("Basenette", "308 862 547");
//            CORSE.setProperty("taux", "1,02");
//            
//            Node FRANCHE_COMTE  = graphDb.createNode();
//            FRANCHE_COMTE.setProperty("name", "FRANCHE_COMTE");
//            FRANCHE_COMTE.setProperty("Basenette", "1 072 564 789");
//            FRANCHE_COMTE.setProperty("taux", "3,92");
//            
//         
//            Node HAUTE_NORMANDIE  = graphDb.createNode();
//            HAUTE_NORMANDIE.setProperty("name", "HAUTE_NORMANDIE");
//            HAUTE_NORMANDIE.setProperty("Basenette", "1 651 522 593");
//            HAUTE_NORMANDIE.setProperty("taux", "4,42");
//            
//            Node ILE_DE_FRANCE  = graphDb.createNode();
//            ILE_DE_FRANCE.setProperty("name", "ILE_DE_FRANCE");
//            ILE_DE_FRANCE.setProperty("Basenette", "21 197 652 584");
//            ILE_DE_FRANCE.setProperty("taux", "1,27");
//            
//            Node LANGUEDOC_ROUSSILLON  = graphDb.createNode();
//            LANGUEDOC_ROUSSILLON.setProperty("name", "LANGUEDOC_ROUSSILLON");
//            LANGUEDOC_ROUSSILLON.setProperty("Basenette", "2 691 245 822");
//            LANGUEDOC_ROUSSILLON.setProperty("taux", "4,86");
//            
//            Node LIMOUSIN  = graphDb.createNode();
//            LIMOUSIN.setProperty("name", "LIMOUSIN");
//            LIMOUSIN.setProperty("Basenette", "689 185 081");
//            LIMOUSIN.setProperty("taux", "4,10");
//            
//            Node LORRAINE  = graphDb.createNode();
//            LORRAINE.setProperty("name", "LORRAINE");
//            LORRAINE.setProperty("Basenette", "2 132 022 588");
//            LORRAINE.setProperty("taux", "2,73");
//            
//            Node MIDI_PYRENEES  = graphDb.createNode();
//            MIDI_PYRENEES.setProperty("name", "MIDI_PYRENEES");
//            MIDI_PYRENEES.setProperty("Basenette", "2 780 934 460");
//            MIDI_PYRENEES.setProperty("taux", "4,72");
//            
//            Node NORD_PAS_DE_CALAIS  = graphDb.createNode();
//            NORD_PAS_DE_CALAIS.setProperty("name", "NORD_PAS_DE_CALAIS");
//            NORD_PAS_DE_CALAIS.setProperty("Basenette", "2 931 855 359");
//            NORD_PAS_DE_CALAIS.setProperty("taux", "3,83");
//            
//            Node PAYS_DE_LA_LOIRE  = graphDb.createNode();
//            PAYS_DE_LA_LOIRE.setProperty("name", "PAYS_DE_LA_LOIRE");
//            PAYS_DE_LA_LOIRE.setProperty("Basenette", "3 103 305 386");
//            PAYS_DE_LA_LOIRE.setProperty("taux", "2,66");
//            
//            Node PICARDIE  = graphDb.createNode();
//            PICARDIE.setProperty("name", "PICARDIE");
//            PICARDIE.setProperty("Basenette", "1 540 503 620");
//            PICARDIE.setProperty("taux", "4,26");
//            
//            Node POITOU_CHARENTES = graphDb.createNode();
//            POITOU_CHARENTES.setProperty("name", "POITOU_CHARENTES");
//            POITOU_CHARENTES.setProperty("Basenette", "1 521 199 183");
//            POITOU_CHARENTES.setProperty("taux", "3,32");
//            
//            Node PROVENCE_ALPES_COTE_D_AZUR = graphDb.createNode();
//            PROVENCE_ALPES_COTE_D_AZUR.setProperty("name", "PROVENCE_ALPES_COTE_D_AZUR");
//            PROVENCE_ALPES_COTE_D_AZUR.setProperty("Basenette", "6 129 538 058");
//            PROVENCE_ALPES_COTE_D_AZUR.setProperty("taux", "2,36");
//            
//            Node RHONE_ALPES = graphDb.createNode();
//            RHONE_ALPES.setProperty("name", "RHONE_ALPES");
//            RHONE_ALPES.setProperty("Basenette", "7 396 610 646");
//            RHONE_ALPES.setProperty("taux", "2,12");
//            
//           
//
//            // Creation des Relations
//            BRETAGNE.createRelationshipTo(PAYS_DE_LA_LOIRE, RelTypes.VOISIN);
//            BRETAGNE.createRelationshipTo(BASSE_NORMANDIE, RelTypes.VOISIN);
//            BASSE_NORMANDIE.createRelationshipTo(HAUTE_NORMANDIE, RelTypes.VOISIN);
//            BASSE_NORMANDIE.createRelationshipTo(PAYS_DE_LA_LOIRE, RelTypes.VOISIN);
//            PAYS_DE_LA_LOIRE.createRelationshipTo(POITOU_CHARENTES, RelTypes.VOISIN);
//            PAYS_DE_LA_LOIRE.createRelationshipTo(CENTRE, RelTypes.VOISIN);
//            HAUTE_NORMANDIE.createRelationshipTo(CENTRE, RelTypes.VOISIN);
//            HAUTE_NORMANDIE.createRelationshipTo(PICARDIE, RelTypes.VOISIN);
//            HAUTE_NORMANDIE.createRelationshipTo(ILE_DE_FRANCE, RelTypes.VOISIN);
//            ILE_DE_FRANCE.createRelationshipTo(CENTRE, RelTypes.VOISIN);
//            ILE_DE_FRANCE.createRelationshipTo(PICARDIE, RelTypes.VOISIN);
//            ILE_DE_FRANCE.createRelationshipTo(CHAMPAGNE_ARDENNE, RelTypes.VOISIN);
//            ILE_DE_FRANCE.createRelationshipTo(BOURGOGNE, RelTypes.VOISIN);
//            CENTRE.createRelationshipTo(LIMOUSIN, RelTypes.VOISIN);
//            CENTRE.createRelationshipTo(AUVERGNE, RelTypes.VOISIN);
//            POITOU_CHARENTES.createRelationshipTo(AQUITAINE, RelTypes.VOISIN);
//            POITOU_CHARENTES.createRelationshipTo(LIMOUSIN, RelTypes.VOISIN);
//            AQUITAINE.createRelationshipTo(MIDI_PYRENEES, RelTypes.VOISIN);
//            AQUITAINE.createRelationshipTo(LIMOUSIN, RelTypes.VOISIN);
//            MIDI_PYRENEES.createRelationshipTo(LANGUEDOC_ROUSSILLON, RelTypes.VOISIN);
//            MIDI_PYRENEES.createRelationshipTo(AUVERGNE, RelTypes.VOISIN);
//            MIDI_PYRENEES.createRelationshipTo(LIMOUSIN, RelTypes.VOISIN);
//            LANGUEDOC_ROUSSILLON.createRelationshipTo(PROVENCE_ALPES_COTE_D_AZUR, RelTypes.VOISIN);
//            LANGUEDOC_ROUSSILLON.createRelationshipTo(RHONE_ALPES, RelTypes.VOISIN);
//            LANGUEDOC_ROUSSILLON.createRelationshipTo(AUVERGNE, RelTypes.VOISIN);
//            PROVENCE_ALPES_COTE_D_AZUR.createRelationshipTo(RHONE_ALPES, RelTypes.VOISIN);
//            RHONE_ALPES.createRelationshipTo(FRANCHE_COMTE, RelTypes.VOISIN);
//            RHONE_ALPES.createRelationshipTo(BOURGOGNE, RelTypes.VOISIN);
//            RHONE_ALPES.createRelationshipTo(AUVERGNE, RelTypes.VOISIN);
//            FRANCHE_COMTE.createRelationshipTo(ALSACE, RelTypes.VOISIN);
//            FRANCHE_COMTE.createRelationshipTo(LORRAINE, RelTypes.VOISIN);
//            LORRAINE.createRelationshipTo(ALSACE, RelTypes.VOISIN);
//            LORRAINE.createRelationshipTo(CHAMPAGNE_ARDENNE, RelTypes.VOISIN);
//            CHAMPAGNE_ARDENNE.createRelationshipTo(PICARDIE, RelTypes.VOISIN);
//            PICARDIE.createRelationshipTo(NORD_PAS_DE_CALAIS, RelTypes.VOISIN);
//            
//          
//            /*
//             * Cette dernier liaisan Va permet par incidence grance au relation definit avant. 
//             * Nous faire parcourir dans le graph 
//             */
//            Region.createRelationshipTo(BRETAGNE, RelTypes.VOISIN);
//          
//            //Ne pas oblier de mettre les liaison entre le 2 iem noeud d'entre et 
//            // les regions sinon pas d'accée au region 
//            
//            
//           
//
//            tx.success();
//        } catch (Exception e) {
//            tx.failure();
//        } finally {
//            tx.finish();
//        }
//    }
//    
//   
//	
//
//
//}