package globalModel;

public class Queries {
	public static final String prefix = ""
			+ "PREFIX candidat: <http://localhost:9000/hbase#> "
			+ "PREFIX geo: <http://rdf.insee.fr/geo/>"
			+ "PREFIX taxe: <http://www.taxe-foncier.com#>";
	
	/********** Requete TDB **********/
	public static final String TDepCode =prefix +
			"Select ?nom_departement ?code_departement where { ?dep geo:code_departement ?code_departement ."
										+ "?dep geo:nom ?nom_departement }";

	
	/********** Requete HBase **********/
	public static final String HMontpDepCode =prefix +
			"Select ?dep ?codeDep where { ?dep candidat:infoLocalisationCode_departement ?codeDep .} LIMIT 15";
	
	public static final String Hquery =prefix +
										"Select * where { ?s ?p ?o} LIMIT 15";
	
	public static final String HcandidateName =prefix +
			"Select distinct ?nom_candidat where { ?s candidat:infoCandidatNom_du_candidat ?nom_candidat }";
	

	/********** Requete TDB-HBase **********/
	public static final String THCommuneNameOf34 =prefix +
			"Select distinct ?nomDepTDB  ?nomHB where {"
			+ " ?dep geo:code_departement \"34\" ."
			+ " ?dep geo:nom ?nomDepTDB ."
			+ " ?d candidat:infoLocalisationNom_de_la_commune ?nomHB ."
			+ "}";

	/********** Requete TDB-HBase-Neo4j **********/
	public static final String THNCommuneNameOf34 =prefix +
			"Select distinct ?region_neo4j ?dep_tdb  ?commune_hbase where { "
			+ " ?reg taxe:Name \"LANGUEDOC_ROUSSILLON\" ."
			+ " ?reg taxe:Name ?region_neo4j ."
			+ " ?dep geo:code_departement \"34\" ."
			+ " ?dep geo:nom ?dep_tdb ."
			+ " ?d candidat:infoLocalisationNom_de_la_commune ?commune_hbase ."
			+ "}";
	

	public static final String HbaseTdb =prefix +
			"Select distinct ?VoteOfficeNumber ?voiceNumber ?o where {"
			+ " ?s candidat:infoCandidatNom_du_candidat \"HOLLANDE\" ."
			+ " ?s candidat:infoCandidatNombre_de_voix_du_candidat ?voiceNumber ."
			+ " ?s candidat:infoLocalisationNom_de_la_commune \"Montpellier\" ."
			+ "?s candidat:infoLocalisationN_de_bureau_de_vote ?VoteOfficeNumber ."
			+ "?a geo:nom ?o ."
			+ "}";
	
	/********** Requete Neo4j **********/
	public static final String Ntout =prefix +
			"Select ?region ?taux where { "
			+ " ?s taxe:Name ?region ."
			+ " ?s taxe:Taux ?taux} ";
	
	/********** Requete Neo4j-HBase **********/
	public static final String NHhollandeInMontp =prefix +
			"Select ?name ?last_name ?VoteOfficeNumber ?voiceNumber where { "
			+ " ?s taxe:Name \"LANGUEDOC_ROUSSILLON\" ."
			+ " ?s taxe:Taux ?taux ."
			+ " ?cand candidat:infoLocalisationNom_de_la_commune \"Montpellier\" ."
			+ " ?cand candidat:infoCandidatNom_du_candidat \"HOLLANDE\" ."
			+ " ?cand candidat:infoCandidatNom_du_candidat ?name ."
			+ " ?cand candidat:infoCandidatPrenom_du_candidat ?last_name ."
			+ " ?cand candidat:infoLocalisationN_de_bureau_de_vote ?VoteOfficeNumber ."
			+ " ?cand candidat:infoCandidatNombre_de_voix_du_candidat ?voiceNumber } ";


	public static final String NHhollandeInMontpWhithTaux =prefix +
			"Select ?name ?last_name ?VoteOfficeNumber ?voiceNumber ?taux where { "
			+ " ?s taxe:Name \"LANGUEDOC_ROUSSILLON\" ."
			+ " ?s taxe:Taux ?taux ."
			+ " ?cand candidat:infoLocalisationNom_de_la_commune \"Montpellier\" ."
			+ " ?cand candidat:infoCandidatNom_du_candidat \"HOLLANDE\" ."
			+ " ?cand candidat:infoCandidatNom_du_candidat ?name ."
			+ " ?cand candidat:infoCandidatPrenom_du_candidat ?last_name ."
			+ " ?cand candidat:infoLocalisationN_de_bureau_de_vote ?VoteOfficeNumber ."
			+ " ?cand candidat:infoCandidatNombre_de_voix_du_candidat ?voiceNumber } ";
	
}
