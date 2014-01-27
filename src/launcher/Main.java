package launcher;


import globalModel.GlobalModel;
import hbase.HBaseModel;
import hbase.HBaseUtils;

import org.apache.hadoop.hbase.client.HTable;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import tdb.TDBUtils;
public class Main {
	private static final String prefix = ""
			+ "PREFIX Hcandidat: <http://localhost:9000/hbase#> "
			+ "PREFIX geo: <http://rdf.insee.fr/geo/>";
	

	private static final String TMontpDepCode =prefix +
			"Select ?s ?o where { ?s geo:code_departement \"34\" .} LIMIT 15";

	
	private static final String Hquery =prefix +
										"Select * where { ?s ?p ?o} LIMIT 15";
	
	private static final String HcandidateName =prefix +
			"Select distinct ?o where { ?s Hcandidat:infoCandidatNom_du_candidat ?o}";
	
	private static final String HhollandeVoicesNumberInMontp =prefix +
			"Select distinct ?VoteOfficeNumber ?voiceNumber where {"
			+ " ?s Hcandidat:infoCandidatNom_du_candidat \"HOLLANDE\" ."
			+ " ?s Hcandidat:infoCandidatNombre_de_voix_du_candidat ?voiceNumber ."
			+ " ?s Hcandidat:infoLocalisationNom_de_la_commune \"Montpellier\" ."
			+ "?s Hcandidat:infoLocalisationN_de_bureau_de_vote ?VoteOfficeNumber "
			+ "}";
	
	
	private static final String THfranceDep =prefix +
			"Select ?departement where { ?s geo:nom ?departement ."
			+ "}";
	
	private static final String THCommuneCodeOf34 =prefix +
			"Select ?dep ?chef where { ?dep geo:chef-lieu ?chef ."
			+ "?chef geo:nom \"Montpellier\"@fr ."
			+ "}";
	
	
	private static final String HbaseTdb =prefix +
			"Select distinct ?VoteOfficeNumber ?voiceNumber ?o where {"
			+ " ?s Hcandidat:infoCandidatNom_du_candidat \"HOLLANDE\" ."
			+ " ?s Hcandidat:infoCandidatNombre_de_voix_du_candidat ?voiceNumber ."
			+ " ?s Hcandidat:infoLocalisationNom_de_la_commune \"Montpellier\" ."
			+ "?s Hcandidat:infoLocalisationN_de_bureau_de_vote ?VoteOfficeNumber ."
			+ "?a geo:nom ?o ."
			+ "}";
	
	public static void main(String[] args) throws Exception{
		Model results = ModelFactory.createDefaultModel();
		
		/**** TDB *****/
	TDBUtils tdb = new TDBUtils();
	tdb.runQuery(THCommuneCodeOf34);
	
	
	/**** HBASE *****/
//	HBaseUtils hb = new HBaseUtils();
//	hb.ToConsole();
//	hb.runQuery(HhollandeVoicesNumberInMontpier);	
		
		/**** MODEL GLOBAL *****/
//	GlobalModel gmodel = GlobalModel.getInstance();	
//	
//	gmodel.runQuery(HbaseTdb);
	
}
}
