package hbase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class HBaseUtils {
	
        private static HBaseAdmin admin;
        private static Configuration conf;
        private static HTable candidateTable ;
        private static HBaseModel HBModel;
        private final String partie01 = "/home/sosso/GIT/Gest.Donnee.Complexes/resources/hbase/partie01.txt";
        private final String partie02 = "/home/sosso/GIT/Gest.Donnee.Complexes/resources/hbase/partie02.txt";
        private final String partie03 = "/home/sosso/GIT/Gest.Donnee.Complexes/resources/hbase/partie03.txt";    	
		
        public HBaseUtils(){
        	conf = HBaseConfiguration.create();
        	try {
				admin = new HBaseAdmin(conf);
			} catch (MasterNotRunningException | ZooKeeperConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	HBModel = HBaseModel.getInstance();
        	ToHBModel("candidat");
        	load();
        }
        
        public static HTable getTable(){    	
                return candidateTable;
        }
        public static HBaseAdmin getAdmin() {
			return admin;
		}

		public static void setAdmin(HBaseAdmin admin) {
			HBaseUtils.admin = admin;
		}
		public static void setConf(Configuration conf) {
			HBaseUtils.conf = conf;
		}

		public static void setTable(HTable table) {
			HBaseUtils.candidateTable = table;
		}

    
    public static Configuration getConf(){
            return conf;
    }
 
    /**
     * Create a table
     */
    public static void creatTable(String tableName, String[] familys) throws Exception {
    	
    	if (admin.tableExists(tableName)) {
            System.out.println("table already exists!");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            for (int i = 0; i < familys.length; i++) {
                tableDesc.addFamily(new HColumnDescriptor(familys[i]));
            }
            admin.createTable(tableDesc);
            System.out.println("TABLE " + tableName + " CREATED");
        }
    }
 
    /**
     * Delete a table
     */
    public static void deleteTable(String tableName) throws Exception {
        try {
            HBaseAdmin admin = new HBaseAdmin(conf);
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("TABLE " + tableName + " DELETED!");
        } catch (MasterNotRunningException e) {
            e.printStackTrace();
        } catch (ZooKeeperConnectionException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Put (or insert) a row
     */
    public static void  addRecord(String tableName, String rowKey, String family, String column, String value) throws Exception {
       try {
                HTable table = new HTable(conf, tableName);
                Put put = new Put(Bytes.toBytes(rowKey));
                put.add(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
                table.put(put);
                System.out.println("RECORD "+ rowKey + "ADDED TO "+ tableName);
       } catch (Exception e) {
               e.getStackTrace();
       }
    }
 
    /**
     * Delete a row
     */
    public static void delRecord(String tableName, String rowKey) throws IOException {
        HTable table = new HTable(conf, tableName);
        Delete del = new Delete(Bytes.toBytes(rowKey));
        List<Delete> listDel = new ArrayList<Delete>();
        listDel.add(del);
        table.delete(listDel);
        
        System.out.println("RECORD " + rowKey + " DELETED");
    }
 
    /**
     * Get a row
     */
    public static void getOneRecord (String tableName, String rowKey) throws IOException{
        HTable table = new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        Result res = table.get(get);
        KeyValue KV = new KeyValue();
        for(KeyValue kv : res.raw()){
        	System.out.print(new String(kv.getRow()) + " ");
            System.out.print(new String(kv.getFamily()) + ":" );
            System.out.print(new String(kv.getQualifier()) + " " );
            System.out.println(new String(kv.getValue()));
        }
    }
    
    /**
     * Scan (or list) a table
     */
    public static void getAllRecord (String tableName) {
        try{
             HTable table = new HTable(conf, tableName);
             Scan s = new Scan();
             ResultScanner ss = table.getScanner(s);
             for(Result r:ss){
                 for(KeyValue kv : r.raw()){
                    System.out.print(new String(kv.getRow()) + " ");
                    System.out.print(new String(kv.getFamily()) + ":");
                    System.out.print(new String(kv.getQualifier()) + " ");
                    System.out.print(kv.getTimestamp() + " ");
                    System.out.println(new String(kv.getValue()));
                 }
             }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
        public void ToHBModel(String tableName){
        	
        	try{
                HTable table = new HTable(conf, tableName);
                Scan s = new Scan();
                ResultScanner ss = table.getScanner(s);
                	for(Result r:ss){
                        for(KeyValue kv : r.raw()){
                           Resource subject = HBModel.CreateResource(new String(kv.getRow()));
                           Property predicate = HBModel.CreateProperty(new String(kv.getFamily()) + new String(kv.getQualifier()));
                           Literal object = HBModel.CreateLiteral(new String(kv.getValue()));
                           HBModel.AddTriple(subject, predicate, object);
//                           System.out.println(subject.getLocalName() +"  "
//                        		   				+ predicate.getLocalName()+"  "
//                        		   				+ object.getString());
                        }
                  }
           } catch (IOException e){
               e.printStackTrace();	
           }	
        } 
        /*
         * candidate table
         * */
        public void createCandidateTable() {
                String[] familys = {"numTour","infoLocalisation","infoElecteurs","infoCandidat"};
                try {
                        creatTable("candidat", familys);
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
        
        /*
         * load the two files
         * */
        public void load(){
        	try {
				if(admin.tableExists("candidat")){
					loadFile(partie03, "candidat");
				}else {
					createCandidateTable();
					loadFile(partie03, "candidat");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                
        }
        
        /*
         * load file into table
         * */
        private void loadFile(String file, String tableName){                
                try{
                        candidateTable = new HTable(conf, tableName);
                        InputStream ips=new FileInputStream(file); 
                        InputStreamReader ipsr=new InputStreamReader(ips);
                        BufferedReader br=new BufferedReader(ipsr);
                        String ligne;
                        int counter = 0;
                        while ((ligne=br.readLine())!=null){ 
                                String[] arr = ligne.split(";");
                                Put put = new Put((arr[1]+"$"+arr[2]+"$"+arr[4]+"$"+arr[5]+"$"+arr[6]+"$"+arr[11]).getBytes());
                                
                                put.add("numTour".getBytes(), "N_tour".getBytes(), Bytes.toBytes(arr[0]));
                                put.add("infoLocalisation".getBytes(), "Code_departement".getBytes(), Bytes.toBytes(arr[1]));
                                put.add("infoLocalisation".getBytes(), "Code_de_la_commune".getBytes(), Bytes.toBytes(arr[2]));
                                put.add("infoLocalisation".getBytes(), "Nom_de_la_commune".getBytes(), Bytes.toBytes(arr[3]));
                                put.add("infoLocalisation".getBytes(), "N_de_circonscription_Lg".getBytes(), Bytes.toBytes(arr[4]));
                                put.add("infoLocalisation".getBytes(), "N_de_canton".getBytes(), Bytes.toBytes(arr[5]));
                                put.add("infoLocalisation".getBytes(), "N_de_bureau_de_vote".getBytes(), Bytes.toBytes(arr[6]));
                                
                                put.add("infoElecteurs".getBytes(), "Inscrits".getBytes(), Bytes.toBytes(arr[7]));
                                put.add("infoElecteurs".getBytes(), "Votants".getBytes(), Bytes.toBytes(arr[8]));
                                put.add("infoElecteurs".getBytes(), "Exprimes".getBytes(), Bytes.toBytes(arr[9]));
                                
                                put.add("infoCandidat".getBytes(), "N_de_depot_du_candidat".getBytes(), Bytes.toBytes(arr[10]));
                                put.add("infoCandidat".getBytes(), "Nom_du_candidat".getBytes(), Bytes.toBytes(arr[11]));
                                put.add("infoCandidat".getBytes(), "Prenom_du_candidat".getBytes(), Bytes.toBytes(arr[12]));
                                put.add("infoCandidat".getBytes(), "Code_nuance_du_candidat".getBytes(), Bytes.toBytes(arr[13]));
                                put.add("infoCandidat".getBytes(), "Nombre_de_voix_du_candidat".getBytes(), Bytes.toBytes(arr[14]));
                                
                                candidateTable.put(put);                                
                                counter += 1;
                                if (counter % 10000 == 0) {
                                        System.out.println("Imported "+counter+" records");
                                }
                        }
                        br.close(); 
                }                
                catch (Exception e){
                        System.out.println(e.toString());
                }
        }
        
        public void runQuery(String query){
 		   Query q = QueryFactory.create(query) ;
		    QueryExecution qexec = QueryExecutionFactory.create(query, HBModel.getHbaseModel()) ;
		    ResultSet results = qexec.execSelect() ;
		    ResultSetFormatter.out(results) ;	
        }
        
        public void ToConsole(){
        	HBModel.ToConsole();
    	}
}