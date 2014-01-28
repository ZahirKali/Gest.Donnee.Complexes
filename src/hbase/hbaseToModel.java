package hbase;



public class hbaseToModel {
	
	public hbaseToModel() {
		// TODO Auto-generated constructor stub
		hbaseTable hbt = new hbaseTable();
		hbt.createCandidateTable();
		hbt.load();
	}
	
	
	
}
