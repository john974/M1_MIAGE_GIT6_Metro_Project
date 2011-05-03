/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetgenielogiciel;

import java.util.LinkedList;
import java.util.*;
/**
 *
 * @author HAMDACITY
 */
public class Reseau {
         private Station startStation;
	private LinkedList <Station> allStations = new LinkedList <Station>();
	private LinkedList<Integer> visitedStations = new LinkedList<Integer>();
        private LinkedList<Ligne> lignes = new LinkedList<Ligne>();

    public Reseau(Station startStation) {
        this.startStation = startStation;
    }

    public LinkedList<Station> getAllStations() {
        return allStations;
    }

    public Station getStartStation() {
        return startStation;
    }

    public LinkedList<Integer> getVisiteStations() {
        return visitedStations;
    }

    public void setAllStations(LinkedList<Station> allStations) {
        this.allStations = allStations;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public void setVisiteStations(LinkedList<Integer> visiteStations) {
        this.visitedStations = visiteStations;
    }
   /*Methode pour ajouter une ligne au reseau */
    public void ajouterLigne (Ligne l){
        lignes.add(l);
    }
public void setReseau(){
    this.allStations.add(startStation);
    visit(startStation);
    for (Adjacent a : this.startStation.getAdjacents()){
        if(!this.visitedStations.contains(a.getNode().getNumero())){
            ajouter(a.getNode());
        }

    }


}
private void ajouter (Station s ){
		visit(s);
		this.allStations.add(s);
		for (Adjacent A : s.getAdjacents()){
			if (!this.visitedStations.contains(A.getNode().getNumero()))
                        {
				ajouter(A.getNode());
                         }
                    }

	}
public void afficherReseau(){
    System.out.println("----------LE RESEAU-------------");
    for (Station s : this.allStations){
           System.out.println("  ===/-/-/ ---------------/-/-/===");
            for(Adjacent a: s.getAdjacents()){

                System.out.println("   d ("+s.getNom()+" , "+a.getNode().getNom()+") = "+a.getCost());
            }
    }


}
private void visit(Station s){
		this.visitedStations.add(s.getNumero());
                
	}

public  LinkedList<Ligne> changerMoins(Station A , Station B,LinkedList<Ligne> lst){

    Iterator ite = this.lignes.iterator();
    boolean trouve =false;
    while(ite.hasNext()&& !trouve){
        LinkedList<Ligne> tmp =new LinkedList<Ligne>();
        Ligne l =(Ligne) ite.next();
        if((l.getListStations().contains(A)) && (l.getListStations().contains(B))){
             lst.add(l);
            // trouve =true;
            return lst;
        }
           /* Les autres cas restent à completer c'est mon travailpour next time
               else if (l.getListStations().contains(A)){
                     lst.add(l);
                    tmp.add(l);
                   //System.out.println("J'ai fait le else");
                    return changerMoins(A,B,tmp);
                 }
                 */

    }
return lst;
}

//public void Dijkstra(Station A , Station B){
//    Station tmp = A;
//    LinkedList<Station> listeClose = new LinkedList<Station>();
//    Iterator i = tmp.adjacents.iterator();
//    while (i.hasNext()){
//        Station s = (Station) i.next();
//        listeClose.add(s);
//
//
//
//    }
//
//}
/*
 Cette methode prend en
 @parametre Station  A (depart)et la Station B(arrivée)
 @return la liste des station se trouvant sur le chemin 
 */
/*
public static LinkedList<Station> moinDeChangement(Station A , Station B){
    LinkedList<Station> ajoute = new LinkedList<Station>();  // cette liste contient l'ensemble des noeuds visités
    LinkedList<Station> traite = new LinkedList<Station>(); //contient les sations traitées 
    LinkedList<Station> monchemin= new LinkedList<Station>();
    monchemin= CourtChemin(A, B, ajoute, traite);
    return monchemin;
}*/

/*
 @param : Station A(depart)
 * @param : Station B (arrivee)
 * @param :ajoute (liste des station visitées)
 * @param : traite( liste des station deja traite)
 * @return : une liste contenant le chemin choisi
 */
/*public static LinkedList<Station> CourtChemin(Station A , Station B, LinkedList<Station> ajoute, LinkedList<Station> traite){
    //Cette methode recherche le  chemin entre A et B en faisant le moins de changement possible
    Chemin c =new Chemin(null,A);
    LinkedList<Chemin> chemin = new LinkedList<Chemin>();
    chemin.add(c);
    Station recupere = A;
    while(!ajoute.contains(B)){ // Nous verifions si la liste ne contient pas la station d'arrivée
       // System.out.println("Je doit aller à "+B.getNom());
        ajoute = ajoutAdjcents(recupere, ajoute,chemin);
       
        if(ajoute !=null){
            recupere = ajoute.removeFirst();
//            System.out.println("Ma recup contient "+recupere.getNom());
            traite.add(recupere);
        }
        }
    Chemin dernier =chemin.removeLast();
        // Iterator j= chemin.iterator();
    while(dernier.current!=B){
        dernier =chemin.removeLast();
//        System.out.println("Le nom du dernier de la liste est "+dernier.current.getNom());
    }
    System.out.println("Le precedent du dernier contient : "+dernier.current.getNom());
    int index= chemin.indexOf(B);
//    System.out.println("Lindex contient"+index);
  
    ListIterator li = chemin.listIterator(index);
    LinkedList<Station> parcours = new LinkedList<Station>();
     parcours.add(dernier.current);
    // li.next() = chemin.getFirst();
    while (li.hasPrevious()){
            Chemin tmp= (Chemin)li;
            Station tmp2 = tmp.current;
            parcours.add(tmp2);

    }
    return parcours ;
   
}*/


/*
 @param : A la station courante
 *@param : s (liste des stations visités
 *@param : c une liste contenant la liste des chemins
 *@return : Une nouvelle liste contenant tous  les adjacents de A non contenus dans s
 */
/*public static LinkedList<Station> ajoutAdjcents(Station A, LinkedList<Station> s, LinkedList<Chemin> c){
    // Cette methode ajoute tous les voisins à la liste des elements à traiter
    Iterator i = A.adjacents.iterator();
    while(i.hasNext()){
        Adjacent ad = (Adjacent)i.next();
        Station t = ad.getNode();
         if(!s.contains(t)){
             // System.out.println("Ah j'ai fait sa "+t.getNom());
            s.add(t);
            Chemin ch = new Chemin (A,t);
            c.add(ch);
            }
    }
   
    return s;
}
*/
//public void MoinsDeChangement(Station A , Station B){
//    Iterator i =A.adjacents.iterator() ;
//    boolean trouve= false;
//    LinkedList<Station> visite = new LinkedList<Station>();
//    LinkedList<Station> ajoute = new LinkedList<Station>();
//    while(i.hasNext()&& ! trouve)
//    {
//        visite.add(A);
//        ajoute.add(A);
//        for(Adjacent a : A.adjacents){
//            ajoute.add(a.getNode());
//        }
//        // MoinsDeChangement( B,A);
//        if((ajoute.contains(B)))
//           {
//             trouve=true;
//             
//           }
//    }
//}
}


