//
//import org.neo4j.graphdb.*;
//import org.neo4j.graphdb.index.Index;
//import org.neo4j.graphdb.index.IndexManager;
//import org.neo4j.kernel.EmbeddedGraphDatabase;
//
//import java.util.Iterator;
//
///**
// * @author Nordine
// */
//public class Main {
//    
//	//Chemin du serveur neo4j
//    private static final String DB_PATH = "db/neo4j";
//    
//    private GraphDatabaseService graphDB;
//    private Index<Node> users;
//
//
//	public Main() {
//		//creation d'une instance de graphe
//        graphDB = new EmbeddedGraphDatabase(DB_PATH);
//        IndexManager indexManager = graphDB.index();
//        users = indexManager.forNodes("users");
//    }
//
//    private void createNode() {
//        Transaction tx = graphDB.beginTx();
//
//        try {
//            // Creation des Noeuds
//            Node martin = graphDB.createNode();
//            martin.setProperty("name", "Martin");
//            // Indexation de martin
//            users.add(martin, "name", martin.getProperty("name"));
//
//            Node romain = graphDB.createNode();
//            romain.setProperty("name", "Romain");
//            Node matthieu = graphDB.createNode();
//            matthieu.setProperty("name", "Matthieu");
//            Node lois = graphDB.createNode();
//            lois.setProperty("name", "Lois");
//            Node sebastien = graphDB.createNode();
//            sebastien.setProperty("name", "Sebastien");
//            Node brice = graphDB.createNode();
//            brice.setProperty("name", "Brice");
//
//            // Creation des Relations
//            martin.createRelationshipTo(romain, RelTypes.FRIEND);
//            martin.createRelationshipTo(matthieu, RelTypes.FRIEND);
//            martin.createRelationshipTo(lois, RelTypes.FRIEND);
//            martin.createRelationshipTo(sebastien, RelTypes.FRIEND);
//            martin.createRelationshipTo(lois, RelTypes.FRIEND);
//            romain.createRelationshipTo(lois, RelTypes.FRIEND);
//            romain.createRelationshipTo(sebastien, RelTypes.FRIEND);
//            matthieu.createRelationshipTo(romain, RelTypes.FRIEND);
//            matthieu.createRelationshipTo(sebastien, RelTypes.FRIEND);
//            lois.createRelationshipTo(brice, RelTypes.FRIEND);
//            brice.createRelationshipTo(lois, RelTypes.FRIEND);
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
//    private void testQuery() {
//        Node martin = users.get("name", "Martin").getSingle();
//        Traverser traverser = martin.traverse(Traverser.Order.DEPTH_FIRST, StopEvaluator.END_OF_GRAPH, new ReturnableEvaluator() {
//            @Override
//            public boolean isReturnableNode(TraversalPosition traversalPosition) {
//                Iterable<Relationship> it = traversalPosition.currentNode().getRelationships(Direction.OUTGOING, RelTypes.FRIEND);
//                int i = 0;
//                for (Relationship relationship : it) {
//                    i++;
//                }
//                return !traversalPosition.isStartNode() && i >= 2;
//            }
//        }, RelTypes.FRIEND, Direction.OUTGOING);
//        for (Node node : traverser) {
//            System.out.println(node.getProperty("name"));
//        }
//    }
//    
//
//    private void shutDownDB(){
//        graphDB.shutdown();
//    }
//
//    public static void main(String[] args) {
//        Main main = new Main();
//        System.out.println("noeud create");
//        main.createNode();
//       main.testQuery();
//        
//        main.shutDownDB();
//    }
//}