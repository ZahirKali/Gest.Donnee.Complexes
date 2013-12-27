import java.io.FileNotFoundException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;


public class D2R_Un
{
     public static final String NL = System.getProperty("line.separator") ;
   public static void main(String[] args) throws FileNotFoundException
   {
        
        //creer le modele D2RQ
        Model d2rqModel = new ModelD2RQ("file:map.n3");
        String region ="http://www.lirmm.fr/region";
        String commune ="http://www.lirmm.fr/commune";
        String dep ="http://www.lirmm.fr/departement";
        String loc ="http://www.lirmm.fr/localite";
        String imp ="http://www.lirmm.fr/impot";
        
        String c = "PREFIX c: <"+commune+">" ; 
        String r = "PREFIX r: <"+region+">" ;
        String d = "PREFIX d: <"+dep+">" ;
        String l = "PREFIX l: <"+loc+">" ;
        String i = "PREFIX i: <"+imp+">" ;
                
//1 Donner les noms des communes et de leurs departements d’appartenance
         String queryString = c	 +NL+ d+NL+
                 "SELECT ?nomcommune ?nomDep " 
                 + "WHERE {?c c:nccC ?nomcommune ."
                 + "?d d:nccD ?nomDep ."
                 + "?c c:communeDansDep ?d }" ;
         
         
//2 Donner les noms des communes de la region Languedoc-Roussillon
         String queryString1 = c +NL+ r+ NL+
                 "SELECT ?nomcommune  " 
                 + "WHERE {?c c:nccC ?nomcommune ."
                 + "?r r:nccR ?nomReg ."
                 + "?c c:communeDansReg ?r  ."
                 + " FILTER (?nomReg=\"Languedoc-Roussillon\") }";
        
        
        
//3 Donner le nombre de communes par d ́epartement
//       String queryString2 = c +NL+d+NL+
//                 "SELECT ?nomDep (COUNT(?nomcommune) as ?NbbCommune) "
//                 +"WHERE {?c c:nccC ?nomcommune ."
//                 +"?d d:nccD ?nomDep ."
//                 +"?c c:communeDansDep ?d } "
//                 + "GROUPE BY ?nomDep";
//         
         String queryString2 = c + NL + d + NL +
        		 "SELECT  ?nomDep (COUNT(?nomcommune) as ?NbbCommune) "  
				  + "WHERE {?c c:nccC ?nomcommune ."
				  +"?d d:nccD ?nomDep ."
				  +"?c c:communeDansDep  ?d}" 
				  +"GROUP BY ?nomDep";
        
        
//4 Donner le nom des communes qui correspondent `a des chef lieux de region
         String queryString3 = c +NL+r+NL+
                 "SELECT ?nomcommune "
                 +"WHERE {?c c:nccC ?nomcommune ."
                 +"?c c:communeDansDep ?r . "
                 +"?c c:cl ?Cheflieu ."
                 +"FILTER (?Cheflieu=\"4\") }";
        
        
// 5 Donner le nom des localit´es soumises `a l’ISF ainsi que le nom de leur d´epartement et de leur r´egion
         // d’appartenance
        String queryString9 = c+ NL+ d+NL+ r +NL+ l+ i+ NL+
                "SELECT ?commune ?dep ?region "
                +"WHERE {?c c:nccC ?commune ." 
                +"?d d:nccD ?dep ."
                +"?c c:communeDansDep ?d ."
                +"?r r:nccR ?region ."
                +"?d d:depDansReg ?r ." 
                +"?imp i:impotDansLoc ?c ."
                +"?l c:communeDansLoc ?imp .}" 
                
                + "UNION"  
                
                +"{?d d:nccD ?dep ."
                +"?c c:communeDansDep ?d ."
                +"?r r:nccR ?region ."
                +"?d d:depDansReg ?r ." 
                +"?imp i:impotDansArrond ?a ."
                +"?a c:arrondissementDansLoc ?imp .}"  ; 

 //6 Donner les localit soumises `a l’ISF
       
         String queryString4 = l +NL+
                 "SELECT ?localite "
                 +"WHERE {?l l:codeinseeL ?localite }";
         
         
         
 //7 Donner l'impot moyen par localité soumis a l'isf  
         String queryString5 = l + NL + i +NL+
                 "SELECT ?localite ?impoM "
                 +"WHERE {?l l:codeinseeL ?localite ."
                 + "?i i:impotmoyenI ?impoM ."
                 +"?i i:impotDansLoc ?l }";
         
         
 //8 Donner le patrimoine moyen par localite  qui sont soumis a l'isf 
         String queryString6 = l + NL + i +NL+
                 "SELECT ?localite ?patriM "
                 +"WHERE {?l l:codeinseeL ?localite ."
                 + "?i i:patrimoinemI ?patriM ."
                 +"?i i:impotDansLoc ?l }";
        
         
         
 //9 Donner le nombre de redevable par localite  qui sont soumis a l'isf 
         String queryString7 = l + NL + i +NL+
                 "SELECT ?localite ?redevable "
                 +"WHERE {?l l:codeinseeL ?localite ."
                 + "?i i:nbredevableI ?redevable ."
                 +"?i i:impotDansLoc ?l }";
      
     
// affichage de la requete sparql
         System.out.println(queryString3);
                 
         Query query = QueryFactory.create(queryString3);
           
         // afficher la requete
        // query.serialize(new IndentedWriter(System.out,true)) ;
         System.out.println() ;
      
         QueryExecution qexec = QueryExecutionFactory.create(query, d2rqModel) ;
      

         System.out.println("Les elements du modele sont: ") ;

         try {
            
             ResultSet rs = qexec.execSelect() ;
             ResultSetFormatter.out(System.out, rs, query);

         }
         finally
         {
                         qexec.close() ;
         }
        
        
   }
}
