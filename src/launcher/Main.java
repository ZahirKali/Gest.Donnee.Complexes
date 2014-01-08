package launcher;

import hbase.hbaseTable;

import org.apache.hadoop.hbase.client.HTable;
public class Main {
	
public static void main(String[] args) throws Exception{
	/**** TDB *****/
//	tdbToModel tdb = tdbToModel.getInstance();
//	tdb.toConsoleRDF();
//	
//	tdb.DsClose();
	
	/**** HBASE *****/
	HTable ht = hbaseTable.getTable();
	
}
}
