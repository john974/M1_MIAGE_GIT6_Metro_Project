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
    private LinkedList<Ligne> allLignes = new LinkedList<Ligne>();/* l'ensemble des lignes qui se trouve sur le reseau **/

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

     public LinkedList<Ligne> getAllLignes() {
        return allLignes;
    }

    public void setAllLignes(LinkedList<Ligne> allLignes) {
        this.allLignes = allLignes;
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
    public  synchronized Station getChemin(Station debut, Station fin)
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
            return null;
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
      
      this.getVisitedStations().clear();
      this.closed.clear();
      return end;
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

    public void createLines()
    {
        Ligne L1 = new Ligne(1, "L1");
        Ligne L2 = new Ligne(2, "L2");
        Ligne L3 = new Ligne(1, "L3");
        Ligne L4 = new Ligne(1, "L4");

        L1.getListStations().add(getStation("A"));
        L1.getListStations().add(getStation("I"));
        L1.getListStations().add(getStation("D"));
        L1.getListStations().add(getStation("F"));
        L1.getListStations().add(getStation("G"));

        L2.getListStations().add(getStation("A"));
        L2.getListStations().add(getStation("H"));
        L2.getListStations().add(getStation("C"));
        L2.getListStations().add(getStation("D"));
        L2.getListStations().add(getStation("E"));
        L2.getListStations().add(getStation("G"));
        L2.getListStations().add(getStation("R"));

        L3.getListStations().add(getStation("I"));
        L3.getListStations().add(getStation("B"));
        L3.getListStations().add(getStation("C"));
        L3.getListStations().add(getStation("O"));
        L3.getListStations().add(getStation("G"));
        L3.getListStations().add(getStation("K"));

        L4.getListStations().add(getStation("M"));
        L4.getListStations().add(getStation("I"));
        L4.getListStations().add(getStation("N"));
        L4.getListStations().add(getStation("E"));
        L4.getListStations().add(getStation("K"));
        L4.getListStations().add(getStation("X"));

        this.allLignes.add(L1);
        this.allLignes.add(L2);
        this.allLignes.add(L3);
        this.allLignes.add(L4);
    }

    public void createStations()
    {
        /* creation de quelques stations
        le nom et le temp d'arret **/
       Station a = new Station("A", 5);
       Station b = new Station("B", 3);
       Station c = new Station("C", 4);
       Station d = new Station("D", 4);
       Station e = new Station("E", 5);
       Station i = new Station("I", 6);
       Station h = new Station("H", 2);
       Station o = new Station("O", 3);
       Station g = new Station("G", 6);
       Station f = new Station("F", 2);
       Station k = new Station("K", 3);
       Station x = new Station("X", 7);
       Station n = new Station("N", 3);
       Station r = new Station("R", 8);
       Station m = new Station("M", 5);

       /* ajout de quelque adjacents **/
       a.addAdjacent(new Adjacent(i,10));
       a.addAdjacent(new Adjacent(h,9));
       b.addAdjacent(new Adjacent(i,15));
       b.addAdjacent(new Adjacent(c,12));
       c.addAdjacent(new Adjacent(h,8));
       c.addAdjacent(new Adjacent(d,12));
       c.addAdjacent(new Adjacent(o,30));
       d.addAdjacent(new Adjacent(i,15));
       d.addAdjacent(new Adjacent(f,12));
       d.addAdjacent(new Adjacent(e,13));
       e.addAdjacent(new Adjacent(n,20));
       e.addAdjacent(new Adjacent(g,25));
       e.addAdjacent(new Adjacent(k,10));
       f.addAdjacent(new Adjacent(g,8));
       g.addAdjacent(new Adjacent(r,15));
       g.addAdjacent(new Adjacent(k,10));
       g.addAdjacent(new Adjacent(o,25));
       i.addAdjacent(new Adjacent(m,8));
       i.addAdjacent(new Adjacent(n,20));
       k.addAdjacent(new Adjacent(x,9));

       this.setStartStation(a);
       this.setReseau();
       
    }
   public void essai()
   {
      
       createStations();
       createLines();

       Station debut = this.getStation("M");//this.getStation(s1);
       Station fin = this.getStation("O");//this.getStation(s2);
       
       if(debut != null && fin != null)
       {
             Station tmp = this.getChemin(debut,fin);
             if(tmp != null)
                Saisie.p(tmp.chemin());
       }
       else
           Saisie.p("Le nom des stations de debut et de fin doivent exister  ");
      // this.afficher();
   }

   public LinkedList<Ligne> getLigne(LinkedList<Station> ls)
   {
       boolean trouve = false;
       LinkedList<Ligne> tmp = new LinkedList<Ligne>();
       LinkedList<Ligne> tmpDebut = new LinkedList<Ligne>();
       LinkedList<Ligne> tmpFin = new LinkedList<Ligne>();
       tmpDebut = (LinkedList<Ligne>) ls.clone();
       tmpFin = (LinkedList<Ligne>) ls.clone();
       for(Ligne l : this.allLignes)
       {
           if(l.getListStations().containsAll(ls))
           {
               Saisie.p(l.getNom());
               trouve = true;
               tmp.add(l);
               //return l;
           }
       }

       if(!trouve)
       {
           int taille = ls.size();
           tmp.addAll(this.getLigne(ls));
           for(int k = 0; k < taille /2; k++)
           {
               for(Ligne l : this.allLignes)
               {
                   LinkedList<Station> order = (LinkedList<Station>)ls.subList(k,taille - k+1);
                   LinkedList<Station> reverse = (LinkedList<Station>)ls.subList(k+1,taille - 1 + k);
                   
               }
           }
           
       }
       return tmp;
   }
}
