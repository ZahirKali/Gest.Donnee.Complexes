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
import org.apache.hadoop.hbase.avro.generated.HBase;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class hbaseTable {
	private static HTable candidatTable;
	private final String partie01 = "/home/sosso/GIT/Gest.Donnee.Complexes/resources/hbase/partie01.txt";
	private final String partie02 = "/home/sosso/GIT/Gest.Donnee.Complexes/resources/hbase/partie02.txt";
	private static boolean areFilesLoaded = false;
	
	
	public static HTable getTable(){
		return candidatTable;
	}
	
	private static Configuration conf = null;
    /**
     * Initialization
     */
    static {
        conf = HBaseConfiguration.create();
    }
    
    public static Configuration getConf(){
    	return conf;
    }
 
    /**
     * Create a table
     */
    public static void creatTable(String tableName, String[] familys) throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        
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
            System.out.print(new String(kv.getRow()) + " " );
            System.out.print(new String(kv.getFamily()) + ":" );
            System.out.print(new String(kv.getQualifier()) + " " );
            System.out.print(kv.getTimestamp() + " " );
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
	
	public void createCandidateTable() {
		String[] familys = {"numTour","infoLocalisation","infoElecteurs","infoCandidat"};
		try {
			creatTable("candidat", familys);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(){
		loadFile(partie01, "candidat");
		loadFile(partie02, "candidat");
	}
	
	private void loadFile(String file, String tableName){
		
		try{
			candidatTable = new HTable(conf, tableName);
			InputStream ips=new FileInputStream(file); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			int counter = 0;
			while ((ligne=br.readLine())!=null){ 
				String[] arr = ligne.split(";");
				Put put = new Put(arr[1].getBytes());
				put.add("numTour".getBytes(), "N tour".getBytes(), Bytes.toBytes(arr[0]));
				put.add("infoLocalisation".getBytes(), "Code departement".getBytes(), Bytes.toBytes(arr[1]));
				put.add("infoLocalisation".getBytes(), "Code de la commune".getBytes(), Bytes.toBytes(arr[2]));
				put.add("infoLocalisation".getBytes(), "Nom de la commune".getBytes(), Bytes.toBytes(arr[3]));
				put.add("infoLocalisation".getBytes(), "N de circonscription Lg".getBytes(), Bytes.toBytes(arr[4]));
				put.add("infoLocalisation".getBytes(), "N de canton".getBytes(), Bytes.toBytes(arr[5]));
				put.add("infoLocalisation".getBytes(), "N de bureau de vote".getBytes(), Bytes.toBytes(arr[6]));
				
				put.add("infoElecteurs".getBytes(), "Inscrits".getBytes(), Bytes.toBytes(arr[7]));
				put.add("infoElecteurs".getBytes(), "Votants".getBytes(), Bytes.toBytes(arr[8]));
				put.add("infoElecteurs".getBytes(), "Exprimes".getBytes(), Bytes.toBytes(arr[9]));
				
				put.add("infoCandidat".getBytes(), "N de depot du candidat".getBytes(), Bytes.toBytes(arr[10]));
				put.add("infoCandidat".getBytes(), "Nom du candidat".getBytes(), Bytes.toBytes(arr[11]));
				put.add("infoCandidat".getBytes(), "Prenom du candidat".getBytes(), Bytes.toBytes(arr[12]));
				put.add("infoCandidat".getBytes(), "Code nuance du candidat".getBytes(), Bytes.toBytes(arr[13]));
				put.add("infoCandidat".getBytes(), "Nombre de voix du candidat".getBytes(), Bytes.toBytes(arr[14]));
				
				candidatTable.put(put);				
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
}
