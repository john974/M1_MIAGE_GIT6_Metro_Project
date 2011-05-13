/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

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
    /** cettte procedure itialise un sommet par defaut au reseau de stations.
     * @author groupe de projet
     * @param un objet de type station
     * @return -.
     */
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

    /** cettte procedure ajoute la liste des noeuds au reseau de station.
     *  on ajoute la station de debut, on le marque. En suite on ajoute tous ses voisins et le processus et
     * recursif. A partir du sommet de depart on ajoute tous les sommets aux reseaux. Il faut initialiser le
     * sommet du reseau avant d'appeler cette procedure.
     * @author groupe de projet
     * @param -
     * @return -.
     */
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

    /** Cette métode ajoute une station au réseau ainsi que ses voisins.
     * @autor group de travail
     * @param un objet de type station
     * @return
     */
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

    /** cette procédure permet de marquer une station comme visité.
     * Il est utilisé par les fonctions de recherche de chemin entre autre
     * @author groupe de travail
     * @param un objet de type station
     * @return -.
     */
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

    @Override
    public String toString() {
        StringBuffer res = new StringBuffer("**** L'ENSEMBLE DES STATIONS DU RESEAU REGROUPEES PAR LIGNE *****\n");
        for(Ligne l: this.getAllLignes())
            res.append(l.toString());
        res.append("\n********************************************************************\n");
        return res.toString();
    }

    /** Cette fonction retourne la reference correspondance a la station passé en paramètre.
     * La comparaiason porte sur le nom de la station
     * @param un objet de type station
     * @return un objet de type station
     */
    public Station getStation(Station s)
    {
        return this.getAllStations().get(this.allStations.indexOf(s));
    }

     /** Cette fonction retourne la station dont le nom correspond au paramètre de la fonction.
      * C'est une fonction de recherche, la recherche porte sur le nom de la station
     * La comparaiason porte sur le nom de la station
     * @param un objet de type String
     * @return un objet de type station
     */
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

    /** Cette fonction booleenne valide une recherche de chemin. Elle verifie que les stations de debut et de fin
     * se trouve toutes dans le reseau.
     * @param Station debut
     * @param Station fin
     * @return true || false
     */
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

    /** Cette fonction booleenne valide une recherche de chemin. Elle verifie que les stations
     * se trouvent toutes dans le reseau. Elle intervient dans le cas où on veut passé par plusieurs stations.
     * @param LinkedList<String>: ensemble des sommets par lesquelles on veut passer. On donne juste les noms des stations.
     * @return true si toutes les stations sont dans le reseau
     * @return false si au moins une station n'existe pas dans le reseau
     */
    public boolean valideChemin(LinkedList<String> tmp)
    {
        for(String s : tmp)
        {
            Station tp = this.getStation(s);
            if(!this.getAllStations().contains(tp))
                return false;
        }
        return true;
    }
    /** Cette méthode trouve le plus court chemin entre deux stations données.
     * Elle retourne une des stations liés avec des predecesseurs formant le chemin à
     * suivre. la synchronisation permet d'eviter de conflit pour la recherche parallele.
     * @param Station debut
     * @param Station fin
     * @return une station avec des predecesseurs
     */
    public  synchronized Station getChemin(Station debut, Station fin)
    {
       // LinkedList<Station> listChemin = new LinkedList<Station>();
        boolean trouve = false;
        Station end = null;
       // LinkedList<Adjacent> chemin = new LinkedList<Adjacent>();
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
             // a.getNode().ad
              try{
              /* Si le neud courant correspond au noeud d'arrivée on affiche le noeud et on arrete le boucle **/
              if(a.getNode().getNom().compareTo(fin.getNom()) == 0)
              {
                 // synchronized (this)
                 // {
                     //chemin.add(a);
                     end = a.getNode();
                     this.coutDeplacement = a.getCost();
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

   /** Cette procédure permet de créer l'ensemble des lignes du reseau. Elle ajoute aux
    * differentes lignes leurs stations
    * @param -
    * @return - 
    */
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

   /**
    * Cette procédures crée les stations du reseau et déclare les stations adjacents.
    * Elle initialise la tête du reseau de station à la fin et et met le reseau en place.
    * Pour ces deux dernieres actions, la méthode appelle les fonctions <setStartStation(Station a)> et <setReseau>
    * @param
    * @return
    */
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
   /**Cette méthode initialise le reseau des stations. Elle appelle les méthodes
    * createStations et createLines.
    * @param
    * @return
    */
   public void init()
   {
       createStations();
       createLines();
   }
   /**
    * Cette fonction trouve le plus court chemin reliant deux Stations du reseau.
    * Elle prend en paramètres deux String correspondant au nom des deux stations.
    * @param d
    * @param a
    * @return String Chemin
    */
   public String plusCourtChemin(String d,String a)
   {
      StringBuffer res = new StringBuffer();

       Station debut = this.getStation(d);//this.getStation(s1);
       Station fin = this.getStation(a);//this.getStation(s2);
       
         Station tmp = this.getChemin(debut,fin);
         if(tmp != null)
         {
            res.append("\nChemin: "+tmp.chemin());
            res.append("\nLignes à emprunter: ");
            res.append(getLignes(tmp));
            res.append("\n\tCout: "+this.coutDeplacement);
         }
     return res.toString();
   }

    /**
    * Cette fonction trouve le plus court chemin reliant deux Stations du reseau
    * en passant par d'autres stations. Elle prend en paramètres une liste de nom
    * correspondant au stations de debut et de fin, eventuellement les stations intermédiaires.
    * @param LinkedList ensemble correspondant au nom des stations
    * @return Chemin ou un message d'erreur
    */
   public String plusCourtChemin(LinkedList<String> ensemble)
   {
       StringBuffer res = new StringBuffer("");
       if(valideChemin(ensemble))
       {
            res.append("\nxxxxxxxxxxxxxxxx REPONSE xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            res.append("\nDepart: ").append(ensemble.getFirst());
            res.append("\nArrivée: ").append(ensemble.getLast());
           String d = ensemble.removeFirst();
           while(ensemble.size() > 0)
           {
               String f = ensemble.getFirst();
               res.append(this.plusCourtChemin(d, f));
               d = ensemble.removeFirst();
           }
           res.append("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
       }
       else
       {
           res.append("\nxxxxxxxxxxxxxxxxxx REPONSE xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
           res.append("\nLes stations n'existent pas ou sont momentanemment inaccessibles !x");
           res.append("\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
       }
       return res.toString();
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
   /**
    * Cette procedure retourne l'ensemble des stations qui constituent un chemin.
    * @param Station end. Le paramètre correspond à la station d'arrivée, à travers les
    * predecesseurs on trouve le chemin qui mène au debut
    * @return LinkedList de station. 
    */
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
   /**
    * Cette fonction nous informe sur les lignes à emprunter pour suivre un chemin.
    * @param Station end. Le paramètre correspond à la station d'arrivée, à travers les
    * predecesseurs on trouve le chemin et les lignes qui mène au debut.
    * @return String correspond aux lignes à prendre et aux stations qu'elles contiennent.
    */
   public String getLignes(Station end)
   {
       boolean trouve;
       String lignes = "\n";
       LinkedList<Station> chemin = new LinkedList<Station>();
       LinkedList<Chemin> route = new LinkedList<Chemin>();

       chemin = getChemin(end);
       //Iterator debut = chemin.iterator();
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
            lignes += "\t"+ch.getLine().afficherTrajet();
            st = ch.getLine().getListStations().getLast();
       }
      if(!st.equals(end))
      {
      while(iter.hasNext() && !trouve)
      {
          ch = (Chemin)iter.next();
          if(ch.getStation().equals(st))
          {
                lignes += "\n\t"+ch.getLine().afficherTrajet();
                st = ch.getLine().getListStations().getLast();
           }
          if(ch.getLine().getListStations().getLast().equals(end))
             trouve = true;

       }
       }
       return lignes;
   }

   /*
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
   }*/
   /**
    * Cette fonction met à jour le cout d'une station lié à une station
    * @param route
    * @param ch
    */
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
