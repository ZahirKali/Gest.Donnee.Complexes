package launcher;

import hbase.hbaseTable;
import tdb.*;
public class Main {
	
public static void main(String[] args) throws Exception{
	/**** TDB *****/
//	tdbToModel tdb = tdbToModel.getInstance();
//	tdb.toConsoleRDF();
//	
//	tdb.DsClose();
	
	/**** HBASE *****/
	hbaseTable hbt = new hbaseTable();
	hbt.deleteTable("candidat");
}
}
