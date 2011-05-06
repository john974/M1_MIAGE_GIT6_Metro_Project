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
import java.util.List;
import javax.net.ssl.SSLEngineResult.Status;
import outils.Saisie;

public class Reseau {

    private double coutDeplacement = 0;
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
        this.getVisitedStations().clear();
        this.allStations.add(startStation);
        visit(startStation);
        for(Adjacent a : this.startStation.getAdjacents())
        {
            if(!this.visitedStations.contains(a.getNode().getNumero()))
               ajouter(a.getNode());
        }
        this.getVisitedStations().clear();
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
            //Saisie.p("Le reseau ne contient pas la station d'arrivée");
            return false;
        }
        if(!this.getAllStations().contains(debut))
        {
            //Saisie.p("Le reseau ne contient pas la station de depart ");
            return false;
        }
        if(fin.isIncident())
        {
            //Saisie.p("Il y a un incident a la station d'arrivée\nImpossible d'y aller !");
            return false;
        }
        if(debut.isIncident())
        {
            //Saisie.p("Il y a un incident a la station de depart\nImpossible de passer par cette station !");
            return false;
        }

        if(debut.equals(fin))
        {
            //Saisie.p("La station de debut et de fin sont identiques\nDonner deux station differentes svp !");
            return false;
        }
        return true;
    }

    public boolean valide(LinkedList<String> tmp)
    {
        for(String s : tmp)
        {
            Station tp = this.getStation(s);
            if(!this.getAllStations().contains(tp))
                return false;
        }
        return true;
    }
    /** cette méthode trouve le plus court chemin entre deux stations données.
     cette synchronisation permet d'eviter de conflit pour la recherche parallele **/
    public  synchronized Station getChemin(Station debut, Station fin)
    {
       // LinkedList<Station> listChemin = new LinkedList<Station>();
        boolean trouve = false;
        Station end = null;
        LinkedList<Adjacent> chemin = new LinkedList<Adjacent>();
        /** On initialise la tete de reseau à "debut" **/
        this.setStartStation(debut);
        this.getVisitedStations().clear();
        this.getClosed().clear();
        visit(debut); /* on met le sommet debut à visiter */
        debut.setPredecesseur(null);

        
        
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
              //Saisie.p(this.closed.size());
              Adjacent a = closed.poll();/* on supprime la tete de liste et on le traite **/
              visit(a.getNode());
              try{
              /* Si le neud courant correspond au noeud d'arrivée on affiche le noeud et on arrete le boucle **/
              if(a.getNode().getNom().compareTo(fin.getNom()) == 0)
              {
                 // synchronized (this)
                 // {
                     chemin.add(a);          
                     this.coutDeplacement = a.getCost();
                     end = a.getNode();
                    // a.afficher();
                     //listChemin.add(a.getNode());
                  //}
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
                               //aa.afficher();
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
      //Saisie.p(listChemin.size());
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
        Ligne L3 = new Ligne(3, "L3");
        Ligne L4 = new Ligne(4, "L4");

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
   public void init()
   {
       createStations();
       createLines();
   }
   public void plusCourtChemin(String d,String a)
   {
      // String d = Saisie.lireString("DONNEZ LA STATION DE DEPART !");
       //String a = Saisie.lireString("DONNEZ LA STATION D'ARRIVEE !");

       Station debut = this.getStation(d);//this.getStation(s1);
       Station fin = this.getStation(a);//this.getStation(s2);

       /* on teste s'il n'y a pas d'incident a la station d'arrivée ***/
       if(valideChemin(debut, fin))
       {
             Station tmp = this.getChemin(debut,fin);
             if(tmp != null)
             {
                Saisie.p("xxxxxxxxxxxxxxxx REPONSE xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                Saisie.p("Depart: "+debut.getNom());
                Saisie.p("Arrivée: "+fin.getNom());
                Saisie.p("Cout: "+this.coutDeplacement);
                Saisie.p("\nChemin: "+tmp.chemin());
                Saisie.p("\nLignes à emprunter: ");
                getLignes(tmp);
                Saisie.p("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
             }
       }
       else
       {
           Saisie.p("\nxxxxxxxxxxxxxxxxxx REPONSE xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
           Saisie.p(" Les stations de debut et de fin doivent exister !         x");
           Saisie.p("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
       }
     // this.afficher();
   }

   public void plusCourtChemin(LinkedList<String> ensemble)
   {
       Station tmp = null;
       if(valide(ensemble))
       {
       String d = ensemble.removeFirst();
           while(ensemble.size() > 0)
           {
               String f = ensemble.getFirst();
               this.plusCourtChemin(d, f);
               d = ensemble.removeFirst();
           }
       }
       else
       {
           Saisie.p("\nxxxxxxxxxxxxxxxxxx REPONSE xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
           Saisie.p(" L'ensemble des stations de l'itinéraire doivent exister ! x");
           Saisie.p("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
       }
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

   public LinkedList<Station> getChemin(Station end)
   {
       LinkedList<Station> chemin = new LinkedList<Station>();
       if(end == null)
           return null;
       if(end.getPredecesseur() == null)
       {
           chemin.add(end);
           return chemin;
       }
       else
       {
           chemin.addAll(getChemin(end.getPredecesseur()));
           chemin.add(end);
       }
       return chemin;
   }
   public void getLignes(Station end)
   {
       boolean trouve;
       LinkedList<Station> chemin = new LinkedList<Station>();
       LinkedList<Chemin> route = new LinkedList<Chemin>();

       chemin = getChemin(end);
       Iterator debut = chemin.iterator();
       int index = 1;
       int cout = 1;

       for(int i = 0 ; i < chemin.size() ;i++)
       {
           for(int k = chemin.size() ; k > i; k--)
           {
                for(Ligne l : this.allLignes)
               {
                   Ligne ll = new Ligne(0, l.getNom());
                   List<Station> tmp = new LinkedList<Station>();
                   tmp = chemin.subList(i,k);
                   //ll.setListStations(tmp);
                   if(l.getListStations().containsAll(tmp))
                   {
                       Chemin ch = new Chemin(chemin.get(i),ll,tmp.size());
                       ch.getLine().getListStations().addAll(tmp);
                       update(route,ch);
                   }
               }
           }
       }
       trouve = false;
       Station st = new Station("");
       Iterator iter = route.iterator();
       Chemin ch;
       if(iter.hasNext())
       {
            ch = (Chemin)iter.next();
            ch.getLine().afficherTrajet();
            st = ch.getLine().getListStations().getLast();
       }
      if(!st.equals(end))
      {
      while(iter.hasNext() && !trouve)
      {
          ch = (Chemin)iter.next();
          if(ch.getStation().equals(st))
          {
                ch.getLine().afficherTrajet();
                st = ch.getLine().getListStations().getLast();
           }
          if(ch.getLine().getListStations().getLast().equals(end))
             trouve = true;

       }
       }
   }
   public void getRoute(LinkedList<Chemin> route, Station end)
   {
       boolean trouve = false;
       LinkedList<Station> chemin = new LinkedList<Station>();
       chemin = getChemin(end);
       Iterator iter = chemin.iterator();
       while(iter.hasNext() && !trouve)
       {
           Station tmp = (Station)iter.next();

       }
   }
   public Ligne getStationLine(LinkedList<Chemin> route,Station s)
   {
       Iterator iter = route.iterator();
       Chemin tmp = null;
       boolean trouve = false;
       while(iter.hasNext() && !trouve)
       {
            tmp = (Chemin)iter.next();
           if(tmp.getStation().equals(s))
               trouve = true;
       }
       return tmp.getLine();
   }
   public void update(LinkedList<Chemin> route, Chemin ch)
   {
       boolean trouve = false;
       Iterator iter = route.iterator();
       while(iter.hasNext() && !trouve)
       {
           Chemin tmp = (Chemin)iter.next();
           if(tmp.getStation().getNom().compareTo(ch.getStation().getNom()) == 0)
           {
               if(tmp.getList().size() < ch.getList().size())
                    tmp.setCount(ch.getList().size());
               trouve = true;
           }
       }
       if(!trouve)
           route.add(ch);
   }
}
