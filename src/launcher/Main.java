package launcher;
import tdb.*;
public class Main {
	
public static void main(String[] args){
	tdbToModel tdb = tdbToModel.getInstance();
	tdb.toConsoleRDF();
	
	tdb.DsClose();
}
}
