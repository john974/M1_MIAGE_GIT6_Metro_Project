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


}
private void visit(Station s){
		this.visitedStations.add(s.getNumero());
                
	}
/*Cette methode nous affiche les lignes possible pour un trajet donné
public void affichageLigne(LinkedList<Ligne> L){
    //System.out.println("Les lignes Directs  pour ce trajet");
    for(Ligne l : L){
        System.out.println("Prendre la ligne "+l.getNom());

    }
*/
}
/*Cette methode permet de ramener le trajet avec le moins de changement possible dans le circuit
 @param Station de depart A
 @param Station d'arrivée B
Elle affiche un trajet 
 */
public void afficherTrajet(Trajet T){
    int i=0;
    // System.out.println("Croisement dans T"+T.getS().getNom());
    if(T.getLigne1()!=null && T.getLigne2()!=null && T.getS()!=null ){
        System.out.println();
        System.out.println("TRAJET "+" ----- Prendre le "+T.getLigne1().getNom()+" , " +
                        "descendre à la Station "+ T.getS().getNom()+
                       " puis prendre le "+T.getLigne2().getNom() );//+
    } else
         System.out.println("TRAJET Direct "+" ---- Prendre le "+T.getLigne1().getNom());

}
public LinkedList<Trajet> MoinsDeChangement(Station A , Station B){
    LinkedList<Ligne> lst =new LinkedList();
    Iterator ite = this.lignes.iterator();
    LinkedList<Trajet> Tr =new LinkedList<Trajet>();
    Trajet t= new Trajet();
 
    int marqueur =0; /*Entier permettant d'indiquer si on trouve une ligne contenant le point de depart et d'arrivée*/
    while(ite.hasNext())
    {
        Ligne l =(Ligne) ite.next();
        if((l.getListStations().contains(A)) && (l.getListStations().contains(B))){
             lst.add(l);
             t.setLigne1(l);
             Tr.add(t);
             marqueur+=1;        
        }     
    }

    if(Tr.size()>0){
        for(Trajet f :Tr){
        afficherTrajet(f);
        }
    }
//    if(lst !=null){ affichageLigne(lst);}
  if(marqueur ==0 ) /* Dans le cas ou n'avons pas de ligne contenant le point de depart et d'arrivée
                        Nous recherhcons les lignes contenant  le depart(A) ou l'arrivée (B) */
  {
         Iterator ite2 = this.lignes.iterator();// iterateur sur la liste des lignes du reseau
         LinkedList<Ligne> tmpA =new LinkedList<Ligne>();/*liste des lignes qui passent par la station A*/
         LinkedList<Ligne> tmpB =new LinkedList<Ligne>();/*liste des lignes qui passent par la station B*/
        while(ite2.hasNext())
        {
            Ligne l =(Ligne) ite2.next();
            if((l.getListStations().contains(A)))// lignes Contenants le point de départ
            {
               tmpA.add(l); 
            }
            if((l.getListStations().contains(B)) )// lignes Contenants le point d'arrivée 
            {
              tmpB.add(l);          
            }
        }
        LinkedList<Trajet> c = new  LinkedList<Trajet>();// La liste des trajets possibles
        Trajet T=new Trajet();
          if(tmpA!=null && tmpB !=null)
          {
                //int i=0 ; //numerote les  le nombre de trajet
              for (Ligne LA :tmpA)
              {
                    for(Ligne LB :tmpB)
                    {
                      LinkedList<Station> s = commun(LA, LB);
                     
                        if(s.size()>0)
                             
                        {
                           
                         /*Nous verifons ici si l'intersection n'est pas vide*/
                           
                                T.setLigne1(LA);
                                T.setLigne2(LB);

                                for(Station st :s)
                                   {
                                    T.setS(st);
                                   // System.out.println("Croisement "+T.getS().getNom());
                                   }
                                //System.out.println("Croisement Sortie"+T.getS().getNom());
                                 c.add(T);
                                 Tr.add(T);
                                afficherTrajet(T);
                            
                            
                         }
                        else     System.out.println("Desole Cher client(e)! Pour y aler de il faut plus d'un changement ");
                    }
              }
          }
         

    }
  return Tr;
}
   
    
/*La methode suivante prend en parametre 2 listes de lignes et retourne leur intersection   */
public LinkedList<Station> commun(Ligne A,Ligne B)
{
    
    Ligne C ;
    C=A;
    LinkedList<Station> l1 =new LinkedList<Station>();//Liste des station contenant les station de la ligne A
    LinkedList<Station> l2 =new LinkedList<Station>(); //Liste des station contenant les station de la ligne B
    LinkedList<Station> liste =new LinkedList<Station>(); //Liste des station contenant l'intersection des 2 lignes
    l1= C.getListStations();
    l2= B.getListStations();
    l1.retainAll(l2);
    liste=l1;
    //C.getListStations().retainAll(B.getListStations());
    if(liste!=null)
    {
       return liste;
    }
    return null;
  }

}


