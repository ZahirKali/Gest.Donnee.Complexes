package launcher;
import tdb.*;
public class Main {
	
public static void main(String[] args){
	tdbToModel tdb = new tdbToModel().getInstance();
	tdb.toConsole();
}

}
