/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projet_gl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.lang.Object;
import java.util.Iterator;
import outils.Saisie;

public class Reseau {

    private Station startStation; /* la station de debut du reseau */
    private LinkedList<Station> allStations = new LinkedList<Station>(); /* la liste de toute les stations */
    private LinkedList<Integer> visitedStations = new LinkedList<Integer>(); /* la liste des stations visités pour la recherche du chemin**/
    private PriorityQueue<Adjacent> closed = new PriorityQueue<Adjacent>();

    public Reseau(Station startStation) {
        this.startStation = startStation;
    }

    public Reseau() {
    }

    public LinkedList<Station> getAllStations() {
        return allStations;
    }

    public Station getStartStation() {
        return startStation;
    }

    public LinkedList<Integer> getVisitedStations() {
        return visitedStations;
    }

    public void setAllStations(LinkedList<Station> allStations) {
        this.allStations = allStations;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public void setVisitedStations(LinkedList<Integer> visitedStations) {
        this.visitedStations = visitedStations;
    }

    public PriorityQueue<Adjacent> getClosed() {
        return closed;
    }

    /* ajoute la liste des noeuds aux reseaux **/
    public void setReseau()
    {
        this.allStations.add(startStation);
        visit(startStation);
        for(Adjacent a : this.startStation.getAdjacents())
        {
            if(!this.visitedStations.contains(a.getNode().getNumero()))
               ajouter(a.getNode());
        }
    }

    public void ajouter(Station s)
    {
        visit(s);
        this.allStations.add(s);
        for(Adjacent a : s.getAdjacents())
        {
            if(!(this.visitedStations.contains(a.getNode().getNumero())))
                ajouter(a.getNode());
        }
    }

    /* cette met un sommet à visiter **/
    private void visit(Station s)
    {
	this.visitedStations.add(s.getNumero());
    }

    public boolean isVisited(Station s)
    {
        return this.getVisitedStations().contains(s.getNumero());
    }

   /* cette méthode informe si un chemin est explorable ***/
   /* public boolean isPossiblePath(Station fin, Station s)
    {
        if(this.closed == null)
            return true;
        if(!this.closed.contains(fin))
            return true;
        //else if(this.closed.)
        return false;
    }*/

    public void afficher()
    {
        this.getVisitedStations().clear();
        Saisie.p("------------------");
        for(Station s : this.allStations)
        {
            for(Adjacent a : s.getAdjacents())
            {
                /* on affiche pas le meme arcs deux fois car (A,B) = (B,A) **/
                if(!this.visitedStations.contains(a.getNode().getNumero()))
                {
                    Saisie.p("d("+s.getNom()+","+a.getNode().getNom()+") = "+a.getCost());
                    this.visitedStations.add(s.getNumero());
                }
            }
        }
        Saisie.p("------------------");
    }

    public Station getStation(Station s)
    {
        return this.getAllStations().get(this.allStations.indexOf(s));
    }

    /** cette méthode retourne la  correspondant au nom donné par l'utilisateur**/
    public Station getStation(String nom)
    {
        int pos = -1;
        Station tmp = new Station(nom);
        if(this.allStations.contains(tmp))
           pos = this.getAllStations().indexOf(tmp);
        if(pos == -1)
            return null;

        return this.getAllStations().get(pos);
    }

    /* cette méthode valide les stations de depart et d'arrivée **/
    public boolean valideChemin(Station debut, Station fin)
    {
        if(!this.getAllStations().contains(fin))
        {
            Saisie.p("Le reseau ne contient pas la station d'arrivée");
            return false;
        }
        if(!this.getAllStations().contains(debut))
        {
            Saisie.p("Le reseau ne contient pas la station de depart ");
            return false;
        }
        if(fin.isIncident())
        {
            Saisie.p("Il y a un incident a la station d'arrivée\nImpossible d'y aller !");
            return false;
        }
        if(debut.isIncident())
        {
            Saisie.p("Il y a un incident a la station de depart\nImpossible de passer par cette station !");
            return false;
        }

        if(debut.equals(fin))
        {
            Saisie.p("La station de debut et de fin sont identiques\nDonner deux station differentes svp !");
            return false;
        }
        return true;
    }

    /** cette méthode trouve le plus court chemin entre deux stations données.
     cette synchronisation permet d'eviter de conflit pour la recherche parallele **/
    public  synchronized void getChemin(Station debut, Station fin)
    {
        boolean trouve = false;
        Station end = null;
        LinkedList<Adjacent> chemin = new LinkedList<Adjacent>();
        /** On initialise la tete de reseau à "debut" **/
        this.setStartStation(debut);
        this.getVisitedStations().clear();
        this.getClosed().clear();
        visit(debut); /* on met le sommet debut à visiter */
        debut.setPredecesseur(null);

        /* on teste s'il n'y a pas d'incident a la station d'arrivée ***/
        if(!valideChemin(debut, fin))
        {
            return;
        }
       /* on mets tous les adjacents de "debut" dans la liste closed ces adjacenst auront comme predecesseur le sommet "debut"**/
      for(Adjacent a : debut.getAdjacents())
      {
          //this.closed.add(a);
          if(!a.getNode().isIncident())
          {
              Adjacent tmp = new Adjacent(a.getNode(),a.getCost());
             this.closed.add(tmp);
             a.getNode().setPredecesseur(debut);
          }
      }
      /* tant que la liste closed n'est pas vide et le chemin le plus court n'est pas trouvé **/
        while(this.closed.size() != 0 && !trouve)
        {
              Adjacent a = closed.poll();/* on supprime la tete de liste et on le traite **/
              visit(a.getNode());
              try{
              /* Si le neud courant correspond au noeud d'arrivée on affiche le noeud et on arrete le boucle **/
              if(a.getNode().equals(fin))
              {
                  synchronized (this)
                  {
                     chemin.add(a);
                     end = a.getNode();
                     a.afficher();
                  }
                  trouve = true;
              }  
              else /* on traite les adjacents du noeuds en cours de traitement **/
              {
                  for(Adjacent aa : a.getNode().getAdjacents())
                  {
                      /* si le sommet n'est pas visité et il n'ya pas d'incident **/
                      if(!isVisited(aa.getNode()) && !aa.getNode().isIncident())
                      {
                           Adjacent tmp = new Adjacent(aa.getNode(),aa.getCost() + a.getCost() + a.getNode().getTempArret());
                           if(this.getClosed().contains(aa))
                           {
                                if(getCost(aa.getNode()) > tmp.getCost())
                                    tmp.getNode().setPredecesseur(a.getNode());
                           }
                           else
                               tmp.getNode().setPredecesseur(a.getNode());
                           this.closed.add(tmp);
                      }
                  }
              }
            }
            catch(Exception ex)
            {
                ex.fillInStackTrace();
            }
        }
      if(trouve)
      {
          end.afficher();
          while(end.getPredecesseur() != null)
          {
              end = end.getPredecesseur();
              end.afficher();
          }
      }
      if(!trouve)
          //p("L'accès à cette station est momentannement indisponible !\nAucune itinéraire ne permet de s'y rendre !");
      this.getVisitedStations().clear();
      this.closed.clear();
    }
    
    public double getCost(Station s )
    {
        boolean trouve = false;
        double cout = 0;
        Iterator iter = this.closed.iterator();
        while(iter.hasNext() && !trouve)
        {
            Adjacent tmp = (Adjacent)iter.next();
            if(tmp.getNode().equals(s))
            {
                trouve = true;
                cout = tmp.getCost();
            }
        }
        return cout;
    }
    
   public void essai()
   {
       /* creation de quelques stations
        le nom et le temp d'arret **/
       Station t1 = new Station("A", 3);
       Station t2 = new Station("B", 4);
       Station t3 = new Station("C", 5);
       Station t4 = new Station("D", 6);
       Station t5 = new Station("E", 5);

       /* ajout de quelque adjacents **/
       t1.addAdjacent(new Adjacent(t2,5));
       t1.addAdjacent(new Adjacent(t3,100));
       t2.addAdjacent(new Adjacent(t3,50));
       t2.addAdjacent(new Adjacent(t5,2));
       t3.addAdjacent(new Adjacent(t4,2));
       t4.addAdjacent(new Adjacent(t1,30));
       t4.addAdjacent(new Adjacent(t5,13));

     // t1.setIncident(true);
      //t3.setIncident(true);
       //System.out.println(t2.ppv().getNode().getNom()+" "+t2.ppv().getCost());
       /* mise en place du reseau avec la station t1 comme debut **/
       String s1 = Saisie.lireString("Donner la station de debut:");
       String s2 = Saisie.lireString("Donner la station de fin:");
      this.setStartStation(t1);
       /* essai du changement de tete **/
      // r.setStartStation(t1);
       this.setReseau();
       Station debut = this.getStation(s1);
       Station fin = this.getStation(s2);
      //t1.getAdjacents().getLast().afficher();
       if(debut != null && fin != null)
             this.getChemin(debut,fin);
       else
           Saisie.p("le nom des stations de debut et de fin doit etre diferent de null ");
       //r.getClosed().poll().afficher();

       //r.getClosed().peek().afficher();
       this.afficher();
      // r.getChemin(t3, t5);
   }
}
