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
        /*Ligne L1 = new Ligne(1, "L1");
        Ligne L2 = new Ligne(2, "L2");
        Ligne L3 = new Ligne(3, "L3");
        Ligne L4 = new Ligne(4, "L4");*/
       Ligne ligne1 = new Ligne(1, "Ligne 1");
       Ligne ligne4 = new Ligne(1, "Ligne 4");
       Ligne ligne8 = new Ligne(1, "Ligne 8");
       Ligne ligne12 = new Ligne(1, "Ligne 12");
       Ligne ligne14= new Ligne(1, "Ligne 14");

               /*-------LIGNE 1------------*/

            ligne1.getListStations().add(this.getStation("Chateau"));
            ligne1.getListStations().add(this.getStation("Nation"));
            ligne1.getListStations().add(this.getStation("ReuillyDiderot"));
            ligne1.getListStations().add(this.getStation("GareDeLyon"));
            ligne1.getListStations().add(this.getStation("Bastille"));
            ligne1.getListStations().add(this.getStation("Chatelet"));
            ligne1.getListStations().add(this.getStation("Tuileries"));
            ligne1.getListStations().add(this.getStation("Concorde"));
            ligne1.getListStations().add(this.getStation("Argentine"));
            ligne1.getListStations().add(this.getStation("LaDefense"));

                    /** -------LIGNE 4 --------------**/
            ligne4.getListStations().add(this.getStation("PorteDeClignancourt"));
            ligne4.getListStations().add(this.getStation("MarcadetPoissonniers"));
            ligne4.getListStations().add(this.getStation("GareDuNord"));
            ligne4.getListStations().add(this.getStation("Strasbourg"));
            ligne4.getListStations().add(this.getStation("LesHalles"));
            ligne4.getListStations().add(this.getStation("Cite"));
            ligne4.getListStations().add(this.getStation("MontparnasseBienvenue"));
            ligne4.getListStations().add(this.getStation("Alesia"));
            ligne4.getListStations().add(this.getStation("PorteDOrleans"));

            /** ------------ LIGNE 8 -------------**/
            ligne8.getListStations().add(this.getStation("Balard"));
            ligne8.getListStations().add(this.getStation("Commerce"));
            ligne8.getListStations().add(this.getStation("Invalides"));
            ligne8.getListStations().add(this.getStation("Concorde"));
            ligne8.getListStations().add(this.getStation("Madeleine"));
            ligne8.getListStations().add(this.getStation("Opera"));
            ligne8.getListStations().add(this.getStation("Strasbourg"));
            ligne8.getListStations().add(this.getStation("Republique"));
            ligne8.getListStations().add(this.getStation("Bastille"));
            ligne8.getListStations().add(this.getStation("ReuillyDiderot"));
            ligne8.getListStations().add(this.getStation("Liberte"));
            ligne8.getListStations().add(this.getStation("PointeDuLac"));

            /** ----------------- LIGNE 12 ---------------**/
            ligne12.getListStations().add(this.getStation("PorteDelaChapelle"));
            ligne12.getListStations().add(this.getStation("MarcadetPoissonniers"));
            ligne12.getListStations().add(this.getStation("Pigalle"));
            ligne12.getListStations().add(this.getStation("SaintLazare"));
            ligne12.getListStations().add(this.getStation("Madeleine"));
            ligne12.getListStations().add(this.getStation("Concorde"));
            ligne12.getListStations().add(this.getStation("Rennes"));
            ligne12.getListStations().add(this.getStation("MontparnasseBienvenue"));
            ligne12.getListStations().add(this.getStation("Convention"));
            ligne12.getListStations().add(this.getStation("MairieDIssy"));
           // ligne12.afficherline(ligne12);
            /** ++++++++++++++++++   LIGNE 14 +++++++++++++*/

            ligne14.getListStations().add(this.getStation("SaintLazare"));
            ligne14.getListStations().add(this.getStation("Madeleine"));
            ligne14.getListStations().add(this.getStation("Pyramides"));
            ligne14.getListStations().add(this.getStation("Chatelet"));
            ligne14.getListStations().add(this.getStation("GareDeLyon"));
            ligne14.getListStations().add(this.getStation("Bercy"));
            ligne14.getListStations().add(this.getStation("Olympiades"));

            this.allLignes.add(ligne1);
            this.allLignes.add(ligne4);
            this.allLignes.add(ligne8);
            this.allLignes.add(ligne12);
            this.allLignes.add(ligne14);
    }

   public void createStations()
    {
       // Stations surtout pour la ligne 1
        Station chateauDeVincennes = new Station ("Chateau", 3, false);
        Station nation = new Station ("Nation", 2, false);
        Station reuillyDiderot = new Station ("ReuillyDiderot", 4, false);
        Station gareDeLyon = new Station ("GareDeLyon", 1, false);
        Station bastille = new Station ("Bastille", 5, false);
        Station chatelet = new Station ("Chatelet", 1, false);
        Station tuileries = new Station ("Tuileries", 6, false);
        Station concorde = new Station ("Concorde", 3, false);
        Station argentine = new Station ("Argentine", 10, false);
        Station laDefense = new Station ("LaDefense", 1, false);

            // Stations surtout pour la ligne 4

        Station porteDeClignancourt = new Station ("PorteDeClignancourt", 2, false);
        Station marcadetPoissonniers = new Station ("MarcadetPoissonniers", 5, false);
	Station gareDuNord = new Station ("GareDuNord", 2, false);
        Station strasbourgSaintDenis = new Station ("Strasbourg", 3, false);
        Station lesHalles = new Station ("LesHalles", 2, false);
	Station cite = new Station ("Cite", 4, false);
        Station montparnasseBienvenue = new Station ("MontparnasseBienvenue", 3, false);
	Station alesia = new Station ("Alesia", 4, false);
        Station porteDOrleans = new Station ("PorteDOrleans", 9, false);

        // Stations surtout pour la ligne 8

        Station balard = new Station ("Balard", 7, false);
        Station commerce = new Station ("Commerce", 7, false);
        Station invalides = new Station ("Invalides", 5, false);
        Station madeleine = new Station ("Madeleine", 6, false);
        Station opera = new Station ("Opera", 2, false);
	Station republique = new Station ("Republique", 8, false);
        Station liberte = new Station ("Liberte", 3, false);
        Station creteilPointeDuLac = new Station ("PointeDuLac", 4, false);

        // Stations surtout pour la ligne 12

        Station porteDeLaChapelle = new Station ("PorteDeLaChapelle", 1, false);
	Station pigalle = new Station ("Pigalle", 8, false);
        Station saintLazare = new Station ("SaintLazare", 4, false);
        Station rennes = new Station ("Rennes", 2, false);
        Station convention = new Station ("Convention", 2, false);
        Station mairieDIssy = new Station ("MairieDIssy", 6, false);

        //Stations surtout pour la ligne 14

        Station pyramides = new Station ("Pyramides", 8, false);
	Station bercy = new Station ("Bercy", 4, false);
        Station olympiades = new Station ("Olympiades", 5, false);

        /* La lsite des adjacents */
        // Sur la ligne 1

        chateauDeVincennes.addAdjacent(new Adjacent (nation, 1));
        nation.addAdjacent(new Adjacent (reuillyDiderot, 4));
        reuillyDiderot.addAdjacent(new Adjacent (gareDeLyon, 4));
        gareDeLyon.addAdjacent(new Adjacent (bastille, 5));
        bastille.addAdjacent(new Adjacent (chatelet, 2));
        chatelet.addAdjacent(new Adjacent (tuileries, 4));
        tuileries.addAdjacent(new Adjacent (concorde, 2));
        concorde.addAdjacent(new Adjacent (argentine, 1));
        argentine.addAdjacent(new Adjacent (laDefense, 3));

            //Sur la ligne 4
        porteDeClignancourt.addAdjacent(new Adjacent (marcadetPoissonniers, 1));
        marcadetPoissonniers.addAdjacent(new Adjacent (gareDuNord, 1));
	gareDuNord.addAdjacent(new Adjacent (strasbourgSaintDenis, 2));
        strasbourgSaintDenis.addAdjacent(new Adjacent (lesHalles, 5));
        lesHalles.addAdjacent(new Adjacent (cite, 1));
        cite.addAdjacent(new Adjacent (montparnasseBienvenue, 3));
        montparnasseBienvenue.addAdjacent(new Adjacent (alesia, 2));
        alesia.addAdjacent(new Adjacent (porteDOrleans, 1));

            //Sur la ligne 8
        balard.addAdjacent(new Adjacent (commerce, 3));
        commerce.addAdjacent(new Adjacent (invalides, 2));
        invalides.addAdjacent(new Adjacent (concorde, 2));
        concorde.addAdjacent(new Adjacent (madeleine, 2));
        madeleine.addAdjacent(new Adjacent (opera, 6));
        opera.addAdjacent(new Adjacent (strasbourgSaintDenis, 3));
        strasbourgSaintDenis.addAdjacent(new Adjacent (republique, 5));
        republique.addAdjacent(new Adjacent (bastille, 3));
        bastille.addAdjacent(new Adjacent (reuillyDiderot, 2));
        reuillyDiderot.addAdjacent(new Adjacent (liberte, 2));
        liberte.addAdjacent(new Adjacent (creteilPointeDuLac, 2));

		//Sur la ligne 12
        porteDeLaChapelle.addAdjacent(new Adjacent (marcadetPoissonniers, 2));
        marcadetPoissonniers.addAdjacent(new Adjacent (pigalle, 2));
        pigalle.addAdjacent(new Adjacent (saintLazare, 3));
        saintLazare.addAdjacent(new Adjacent (madeleine, 10));
        madeleine.addAdjacent(new Adjacent (concorde, 2));
        concorde.addAdjacent(new Adjacent (rennes, 3));
        rennes.addAdjacent(new Adjacent (montparnasseBienvenue, 3));
        montparnasseBienvenue.addAdjacent(new Adjacent (convention, 3));
	convention.addAdjacent(new Adjacent (mairieDIssy, 1));

		//Sur la ligne 14
        
        saintLazare.addAdjacent(new Adjacent(madeleine, 6));
        madeleine.addAdjacent(new Adjacent (pyramides, 3));
        pyramides.addAdjacent(new Adjacent (chatelet, 7));
        chatelet.addAdjacent(new Adjacent (gareDeLyon, 6));
        gareDeLyon.addAdjacent(new Adjacent (bercy, 4));
        bercy.addAdjacent(new Adjacent (olympiades, 3));

       this.setStartStation(madeleine);
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


  /****METHODE POUR LE MOINS DE CHANGEMENT************/

   public LinkedList<Trajet> MoinsDeChangement(Station A , Station B){
    LinkedList<Ligne> lst =new LinkedList();
    Iterator ite = this.allLignes.iterator();
    LinkedList<Trajet> Tr =new LinkedList<Trajet>();
    Trajet t= new Trajet();

    int marqueur =0; /*Entier permettant d'indiquer si on trouve une ligne contenant le point de depart et d'arriv�e*/
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
  if(marqueur ==0 ) /* Dans le cas ou n'avons pas de ligne contenant le point de depart et d'arriv�e
                        Nous recherhcons les lignes contenant  le depart(A) ou l'arriv�e (B) */
  {
         Iterator ite2 = this.allLignes.iterator();// iterateur sur la liste des lignes du reseau
         LinkedList<Ligne> tmpA =new LinkedList<Ligne>();/*liste des lignes qui passent par la station A*/
         LinkedList<Ligne> tmpB =new LinkedList<Ligne>();/*liste des lignes qui passent par la station B*/
        while(ite2.hasNext())
        {
            Ligne l =(Ligne) ite2.next();
            if((l.getListStations().contains(A)))// lignes Contenants le point de d�part
            {
               tmpA.add(l);
            }
            if((l.getListStations().contains(B)) )// lignes Contenants le point d'arriv�e
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
   /*  AFFICHAGE DU TRAJET */

   public void afficherTrajet(Trajet T){
    //int i=0;
    // System.out.println("Croisement dans T"+T.getS().getNom());
    if(T.getLigne1()!=null && T.getLigne2()!=null && T.getS()!=null ){
        System.out.println();
        System.out.println("TRAJET "+" Prenez la "+T.getLigne1().getNom()+" , " +
                        "descendre a la Station "+ T.getS().getNom()+
                       " puis prendre le "+T.getLigne2().getNom() );//+
    }   else if (T.getLigne1().getNom()!=null)
         System.out.println("TRAJET DIRECT "+" Prenez la "+T.getLigne1().getNom()+"\n");

}

   /*--------------METHODE RETOURNANT LES INTERSECTIONS des 2 lignes------------*/

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
    if(liste.size() >0)
    {
         System.out.println("Point commun");
       return liste;
    }
    return null;
  }

}
