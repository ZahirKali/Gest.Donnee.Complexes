package globalModel;

public class Queries {
	public static final String prefix = ""
			+ "PREFIX Hcandidat: <http://localhost:9000/hbase#> "
			+ "PREFIX geo: <http://rdf.insee.fr/geo/>"
			+ "PREFIX taxe: <http://www.taxe-foncier.com#>";
	
	/********** Requete TDB **********/
	public static final String TMontpDepCode =prefix +
			"Select ?s ?o where { ?s geo:code_departement \"34\" .} LIMIT 15";

	
	/********** Requete HBase **********/
	public static final String Hquery =prefix +
										"Select * where { ?s ?p ?o} LIMIT 15";
	
	public static final String HcandidateName =prefix +
			"Select distinct ?o where { ?s Hcandidat:infoCandidatNom_du_candidat ?o}";
	
	public static final String HhollandeVoicesNumberInMontp =prefix +
			"Select distinct ?VoteOfficeNumber ?voiceNumber where {"
			+ " ?s Hcandidat:infoCandidatNom_du_candidat \"HOLLANDE\" ."
			+ " ?s Hcandidat:infoCandidatNombre_de_voix_du_candidat ?voiceNumber ."
			+ " ?s Hcandidat:infoLocalisationNom_de_la_commune \"Montpellier\" ."
			+ "?s Hcandidat:infoLocalisationN_de_bureau_de_vote ?VoteOfficeNumber "
			+ "}";
	
	/********** Requete TDB-HBase **********/
	public static final String HbaseTdb =prefix +
			"Select distinct ?VoteOfficeNumber ?voiceNumber ?o where {"
			+ " ?s Hcandidat:infoCandidatNom_du_candidat \"HOLLANDE\" ."
			+ " ?s Hcandidat:infoCandidatNombre_de_voix_du_candidat ?voiceNumber ."
			+ " ?s Hcandidat:infoLocalisationNom_de_la_commune \"Montpellier\" ."
			+ "?s Hcandidat:infoLocalisationN_de_bureau_de_vote ?VoteOfficeNumber ."
			+ "?a geo:nom ?o ."
			+ "}";
	
	/********** Requete TDB **********/
	public static final String Ntout =prefix +
			"Select * where { ?s taxe:Taux ?o } ";
	
	
}
