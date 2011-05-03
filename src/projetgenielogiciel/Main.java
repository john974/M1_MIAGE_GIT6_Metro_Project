/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetgenielogiciel;
import java.util.*;

/**
 *
 * @author HAMDACITY
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Station t1 = new Station("A", 3);
       Station t2 = new Station("B", 4);
       Station t3 = new Station("C", 5);
       Station t4 = new Station("D", 5);
       Station t5 = new Station("E", 6);

       t1.addAdjacent( new Adjacent(t2,10) );
       t2.addAdjacent(new Adjacent(t1,10));

       t1.addAdjacent(new Adjacent(t3,11));
       t3.addAdjacent(new Adjacent(t1,11));

       t2.addAdjacent( new Adjacent(t3,10));
       t3.addAdjacent( new Adjacent(t2,10));

       t3.addAdjacent( new Adjacent(t4,12));
       t4.addAdjacent(new Adjacent(t3,12));

       t1.addAdjacent( new Adjacent(t4,15));
       t4.addAdjacent(new Adjacent(t1,15));
       /**  */

       LinkedList<Station> liste = new LinkedList<Station>();
     
       liste.add(t5);
       liste.add(t1);
       liste.add(t3);
       Ligne l1 =new Ligne(1,"TER1",12);
       l1.setListStations(liste);
       
         /**Creation d'un deuxieme Ligne */
       LinkedList<Station> liste2 = new LinkedList<Station>();
       liste2.add(t1);
       liste2.add(t2);
       liste2.add(t4);

       Ligne l2 =new Ligne(2,"TER2",20);
       l2.setListStations(liste2);
       
       /**Creation d'un troisieme Ligne */
       LinkedList<Station> liste3 = new LinkedList<Station>();
       liste3.add(t4);
       liste3.add(t2);
       //liste3.add(t3);
       liste3.add(t5);
       Ligne l3 =new Ligne(1,"TER3",12);
       l3.setListStations(liste3);
       Reseau R1 =new Reseau(t1);
       R1.ajouterLigne(l1);
       R1.ajouterLigne(l2);
       R1.ajouterLigne(l3);

       LinkedList<Ligne> solution = new LinkedList<Ligne>();
       solution = R1.changerMoins(t1,t2 , solution);
      
       if(solution != null){
            //System.out.println("Vous voulez aller de "+t2.getNom()+" à "+ t3.getNom()+"\n Je vous suggere ");
       for(Ligne lg : solution){
            System.out.println("Il faut utiliser la ligne "+lg.getNom());
       }
       
       }else
           System.out.println("===/-/- \n J'ai rien trouve pour vous \n pauvre Messieur!! \n -/-/==== ");
       
      /*t1.afficherStation();
       Reseau R =new Reseau(t1);
      R.setReseau();
      R.afficherReseau();
       //R.MoinsDeChangement(t1, t4);

     LinkedList<Station> chemin = Reseau.moinDeChangement(t1, t3);
    */

    }

}
