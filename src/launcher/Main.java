package launcher;
<<<<<<< HEAD

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
	
=======
import tdb.*;
public class Main {
	
public static void main(String[] args){
	tdbToModel tdb = tdbToModel.getInstance();
	tdb.toConsoleRDF();
	
	tdb.DsClose();
>>>>>>> e72e23e1242bfd95025eed3249a571fd45ceb049
}
}
