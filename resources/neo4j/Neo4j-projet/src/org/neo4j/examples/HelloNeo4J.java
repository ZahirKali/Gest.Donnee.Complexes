/**
* Licensed to Neo Technology under one or more contributor
* license agreements. See the NOTICE file distributed with
* this work for additional information regarding copyright
* ownership. Neo Technology licenses this file to you under
* the Apache License, Version 2.0 (the "License"); you may
* not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.neo4j.examples;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class HelloNeo4J {
   private static final String DB_PATH = "/home/nordine/neo4j-community-1.9.5/";

   String myString;
   GraphDatabaseService graphDb;
   Node myFirstNode;
   Node mySecondNode;
   Relationship myRelationship;

   private static enum RelTypes implements RelationshipType
   {
       KNOWS
   }
   
   public static void main( final String[] args )
   {
   
       HelloNeo4J myNeoInstance = new HelloNeo4J();
       myNeoInstance.createDb();
       myNeoInstance.removeData();
       myNeoInstance.shutDown();
       
   }
   
   void createDb()
   {
       graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );

       Transaction tx = graphDb.beginTx();
       try
       {
           myFirstNode = graphDb.createNode();
           myFirstNode.setProperty( "name", "Duane Nickull, I Braineater" );
           mySecondNode = graphDb.createNode();
           mySecondNode.setProperty( "name", "Randy Rampage, Annihilator" );

           myRelationship = myFirstNode.createRelationshipTo( mySecondNode, RelTypes.KNOWS );
           myRelationship.setProperty( "relationship-type", "knows" );
           
           myString = ( myFirstNode.getProperty( "name" ).toString() )
                      + " " + ( myRelationship.getProperty( "relationship-type" ).toString() )
                      + " " + ( mySecondNode.getProperty( "name" ).toString() );
           System.out.println(myString);

           tx.success();
       }
       finally
       {
           tx.finish();
       }
   }
   
   void removeData()
   {
       Transaction tx = graphDb.beginTx();
       try
       {
           myFirstNode.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
           System.out.println("Removing nodes...");
           myFirstNode.delete();
           mySecondNode.delete();
           tx.success();
       }
       finally
       {
           tx.finish();
       }
   }
   
   void shutDown()
   {
       graphDb.shutdown();
       System.out.println("graphDB shut down.");   
   }   
}