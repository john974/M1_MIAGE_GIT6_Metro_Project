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
        /*Ligne L1 = new Ligne(1, "L1");
        Ligne L2 = new Ligne(2, "L2");
        Ligne L3 = new Ligne(3, "L3");
        Ligne L4 = new Ligne(4, "L4");*/
       Ligne ligne1 = new Ligne(1, "Ligne 1");
       Ligne ligne4 = new Ligne(1, "Ligne 4");
       Ligne ligne8 = new Ligne(1, "Ligne 8");
       Ligne ligne12 = new Ligne(1, "Ligne 12");
       Ligne ligne14= new Ligne(1, "Ligne 14");

                 ligne1.getListStations().add(this.getStation("Chateau de Vincennes"));
                //listStations.add(chateauDeVincennes);/*;*/
                ligne1.getListStations().add(this.getStation("Berault"));
                //listStations.add(berault);
                ligne1.getListStations().add(this.getStation("Saint Mande"));
               // listStations.add(saintMande);
                 ligne1.getListStations().add(this.getStation("Porte De Vincennes"));
                //listStations.add(porteDeVincennes);
                 ligne1.getListStations().add(this.getStation("Nation"));
               // listStations.add(nation);
                ligne1.getListStations().add(this.getStation("Reuilly-Diderot"));
                // listStations.add(reuillyDiderot);
                ligne1.getListStations().add(this.getStation("Gare de Lyon"));

                //listStations.add(gareDeLyon);
                ligne1.getListStations().add(this.getStation("Bastille"));

                //listStations.add(bastille);
               ligne1.getListStations().add(this.getStation("St-Paul"));

                //listStations.add(stPaul);
                ligne1.getListStations().add(this.getStation("Hotel de Ville"));

             //  listStations.add(hotelDeVille);
               ligne1.getListStations().add(this.getStation("Chatelet"));

               // listStations.add(chatelet);
                ligne1.getListStations().add(this.getStation("Louvre Rivoli"));
                 ligne1.getListStations().add(this.getStation("Palais royal - Musee du Louvre"));


               // listStations.add(louvreRivoli);
                //listStations.add(palaisRoyalMuseeDuLouvre);
               ligne1.getListStations().add(this.getStation("Tuileries"));
                 // listStations.add(tuileries);
               ligne1.getListStations().add(this.getStation("Concorde"));

                //listStations.add(concorde);
               ligne1.getListStations().add(this.getStation("Champs Elysees - Clemenceau"));

               //listStations.add(champsElyseesClemenceau);
               ligne1.getListStations().add(this.getStation("Franklin D. Roosevelt"));

              // listStations.add(franklinDRoosevelt);
               ligne1.getListStations().add(this.getStation("georgeV"));

               //listStations.add(georgeV);
               ligne1.getListStations().add(this.getStation("Charles de Gaulle Etoile"));

               //listStations.add(charlesDeGaulleEtoile);
                ligne1.getListStations().add(this.getStation("Argentine"));
                ligne1.getListStations().add(this.getStation("Porte Maillot"));


               // listStations.add(argentine);
                //listStations.add(porteMaillot);
                ligne1.getListStations().add(this.getStation("Les Sablons"));
                // listStations.add(lesSablons);
                 ligne1.getListStations().add(this.getStation("Pont de Neuilly"));

               // listStations.add(pontDeNeuilly);
                ligne1.getListStations().add(this.getStation("Esplanade de la Defense"));

                // listStations.add(esplanadeDeLaDefense);
                 ligne1.getListStations().add(this.getStation("La Defense"));



               ligne4.getListStations().add(this.getStation("Porte de Clignancourt"));
                //listStations.add(porteDeClignancourt);
                 ligne4.getListStations().add(this.getStation("Simplon"));

                 // listStations.add(simplon);
                ligne4.getListStations().add(this.getStation("Marcadet Poissonniers"));
               // listStations.add(marcadetPoissonniers);
                 ligne4.getListStations().add(this.getStation("Chateau Rouge"));
                   // listStations.add(chateauRouge);
                ligne4.getListStations().add(this.getStation("Barbes Rochechouart"));

                // listStations.add(barbesRochechouart);
                ligne4.getListStations().add(this.getStation("Gare du Nord"));
                //listStations.add(gareDuNord);
                ligne4.getListStations().add(this.getStation("Gare de l'Est"));
                //listStations.add(gareDeLEst);
                ligne4.getListStations().add(this.getStation("Chateau d'Eau"));
               // listStations.add(chateauDEau);
                //listStations.add(strasbourgSaintDenis);
                 ligne4.getListStations().add(this.getStation("Strasbourg Saint-Denis"));
              //  listStations.add(reaumurSebastopol);
                 ligne4.getListStations().add(this.getStation("Reaumur Sebastopol"));
               // listStations.add(etienneMarcel);
                 ligne4.getListStations().add(this.getStation("Etienne Marcel"));
                //listStations.add(lesHalles);
                 ligne4.getListStations().add(this.getStation("Les Halles"));
               // listStations.add(chatelet);
				//ligne4.getListStations().add(this.getStation("Chatelet"));
               // listStations.add(cite);
                 ligne4.getListStations().add(this.getStation("Cite"));
                //listStations.add(stMichel);
                 ligne4.getListStations().add(this.getStation("St-Michel"));
               // listStations.add(odeon);
                 ligne4.getListStations().add(this.getStation("Odeon"));
                //listStations.add(saintGermainDesPres);
                 ligne4.getListStations().add(this.getStation("Saint Germain des-Pres"));
               // listStations.add(saintSulpice);
                 ligne4.getListStations().add(this.getStation("Saint Sulpice"));
                //listStations.add(stPlacide);
                 ligne4.getListStations().add(this.getStation("St-Placide"));
               // listStations.add(montparnasseBienvenue);
                 ligne4.getListStations().add(this.getStation("Montparnasse Bienvenue"));
                //listStations.add(valvin);
                 ligne4.getListStations().add(this.getStation("Valvin"));
               // listStations.add(raspail);
                 ligne4.getListStations().add(this.getStation("Raspail"));
                //listStations.add(denfertRochereau);
                 ligne4.getListStations().add(this.getStation("Denfert Rochereau"));
                //listStations.add(moutonDuvernet);
                 ligne4.getListStations().add(this.getStation("Mouton Duvernet"));
               // listStations.add(alesia);
                 ligne4.getListStations().add(this.getStation("Alesia"));
                //listStations.add(porteDOrleans);
                 ligne4.getListStations().add(this.getStation("Porte d'Orleans"));

                //ligne4.setListStations(listStations);
                 ligne4.getListStations().add(this.getStation("Chateau d'Eau"));
        
                 ligne8.getListStations().add(this.getStation("Balard"));

                //listStations.add(Balard);
               // listStations.add(lourmel);
				ligne8.getListStations().add(this.getStation("Lourmel"));
                //listStations.add(boucicaut);
				ligne8.getListStations().add(this.getStation("Boucicaut"));
                //listStations.add(felixFaure);
				ligne8.getListStations().add(this.getStation("Felix Faure"));
                //listStations.add(commerce);
				ligne8.getListStations().add(this.getStation("Commerce"));
                //listStations.add(laMottePicquetGrenelle);
				ligne8.getListStations().add(this.getStation("La Motte Picquet Grenelle"));
                //listStations.add(ecoleMilitaire);
				ligne8.getListStations().add(this.getStation("Ecole Militaire"));
                //listStations.add(laTourMaubourg);
				ligne8.getListStations().add(this.getStation("La Tour Maubourg"));
                //listStations.add(invalides);
				ligne8.getListStations().add(this.getStation("Invalides"));
                //listStations.add(concorde);
				ligne8.getListStations().add(this.getStation("Madeleine"));
                //listStations.add(madeleine);
				ligne8.getListStations().add(this.getStation("Opera"));
                //listStations.add(opera);
				ligne8.getListStations().add(this.getStation("Balard"));
                //listStations.add(richelieuDrouot);
				ligne8.getListStations().add(this.getStation("Richelieu Drouot"));
                //listStations.add(grandsBoulevards);
				ligne8.getListStations().add(this.getStation("Grands Boulevards"));
                //listStations.add(bonneNouvelle);
				ligne8.getListStations().add(this.getStation("Bonne Nouvelle"));
                //listStations.add(strasbourgSaintDenis);
				ligne8.getListStations().add(this.getStation("Republique"));
                //listStations.add(republique);
				ligne8.getListStations().add(this.getStation("Filles du Calvaire"));
                //listStations.add(fillesDuCalvaire);
				ligne8.getListStations().add(this.getStation("St-Sebastien Froissart"));
                //listStations.add(saintSebastienFroissart);
				ligne8.getListStations().add(this.getStation("Chemin Vert"));
                //listStations.add(cheminVert);
				ligne8.getListStations().add(this.getStation("Ledru-Rollin"));
                //listStations.add(bastille);
				ligne8.getListStations().add(this.getStation("Ledru-Rollin"));
                //listStations.add(ledruRollin);
				ligne8.getListStations().add(this.getStation("Faidherbe Chaligny"));
                //listStations.add(faidherbeChaligny);
				ligne8.getListStations().add(this.getStation("Balard"));
                //listStations.add(reuillyDiderot);
				ligne8.getListStations().add(this.getStation("Montgallet"));
                //listStations.add(montgallet);
				ligne8.getListStations().add(this.getStation("Daumesnil"));
                // listStations.add(daumesnil);
				ligne8.getListStations().add(this.getStation("Michel Bizot"));
                //listStations.add(michelBizot);
				ligne8.getListStations().add(this.getStation("Porte Doree"));
                //listStations.add(porteDoree);
				ligne8.getListStations().add(this.getStation("Porte de Charenton"));
                //listStations.add(porteDeCharenton);
				ligne8.getListStations().add(this.getStation("Liberte"));
                //listStations.add(liberte);
				ligne8.getListStations().add(this.getStation("Charenton-Ecoles"));
                //listStations.add(charentonEcoles);
				ligne8.getListStations().add(this.getStation("Balard"));
                 //listStations.add(porteDeLaChapelle);
				ligne12.getListStations().add(this.getStation("Porte de la Chapelle"));
                //listStations.add(marxDormoy);
				ligne12.getListStations().add(this.getStation("Marx Dormoy"));
               
				ligne12.getListStations().add(this.getStation("Marcadet Poissonniers"));
                //listStations.add(julesJoffrin);
				ligne12.getListStations().add(this.getStation("Jules Joffrin"));
                //listStations.add(lamarckCaulaincourt);
				ligne12.getListStations().add(this.getStation("Lamarck Caulaincourt"));
                //listStations.add(abbesses);
				ligne12.getListStations().add(this.getStation("Abbesses"));
                //listStations.add(pigalle);
				ligne12.getListStations().add(this.getStation("Pigalle"));
                //listStations.add(saintGeorges);
				ligne12.getListStations().add(this.getStation("Saint-Georges"));
                //listStations.add(notreDameDeLorette);
				ligne12.getListStations().add(this.getStation("Notre-Dame de-Lorette"));
                //listStations.add(triniteDEstienneDOrves);
				ligne12.getListStations().add(this.getStation("Trinite d'Estienne d'Orves"));
                //listStations.add(saintLazare);
				ligne12.getListStations().add(this.getStation("Saint-Lazare"));
                //listStations.add(madeleine);
				ligne12.getListStations().add(this.getStation("Madeleine"));
               // listStations.add(concorde);
				ligne12.getListStations().add(this.getStation("Concorde"));
                //listStations.add(assembleeNationale);
				ligne12.getListStations().add(this.getStation("Assemblee Nationale"));
                //listStations.add(solferino);
				ligne12.getListStations().add(this.getStation("Solferino"));
                //listStations.add(rueDuBac);
				ligne12.getListStations().add(this.getStation("Rue du Bac"));
                //listStations.add(sevresBabylone);
				ligne12.getListStations().add(this.getStation("Sevres Babylone"));
                //listStations.add(rennes);
				ligne12.getListStations().add(this.getStation("Rennes"));
                //listStations.add(notreDameDesChamps);
				ligne12.getListStations().add(this.getStation("Notre-Dame des-Champs"));
                //listStations.add(montparnasseBienvenue);
				ligne12.getListStations().add(this.getStation("Montparnasse Bienvenue"));
                //listStations.add(falguiere);
				ligne12.getListStations().add(this.getStation("Falguiere"));
                //listStations.add(pasteur);
				ligne12.getListStations().add(this.getStation("Pasteur"));
                //listStations.add(volontaires);
				ligne12.getListStations().add(this.getStation("Volontaires"));
                //listStations.add(vaugirard);
				ligne12.getListStations().add(this.getStation("Vaugirard"));
                //listStations.add(convention);
				ligne12.getListStations().add(this.getStation("Convention"));
                //listStations.add(porteDeVersailles);
				ligne12.getListStations().add(this.getStation("Porte de Versailles"));
                //listStations.add(corentinCelton);
				ligne12.getListStations().add(this.getStation("Corentin Celton"));
                //listStations.add(mairieDIssy);
				ligne12.getListStations().add(this.getStation("Mairie d'Issy"));
                               //listStations.add(Saint-Lazare);
			ligne14.getListStations().add(this.getStation("Saint-Lazare"));
            //listStations.add(madeleine);
			ligne14.getListStations().add(this.getStation("Madeleine"));
            //listStations.add(pyramides);
			ligne14.getListStations().add(this.getStation("Pyramides"));
            //listStations.add(chatelet);
			ligne14.getListStations().add(this.getStation("Chatelet"));
            //listStations.add(gareDeLyon);
			ligne14.getListStations().add(this.getStation("Gare de Lyon"));
            //listStations.add(bercy);
			ligne14.getListStations().add(this.getStation("Bercy"));
            //listStations.add(courStEmilion);
			ligne14.getListStations().add(this.getStation("Cour St-Emilion"));
            //listStations.add(bibliothequeFrMitterrand);
			ligne14.getListStations().add(this.getStation("Bibliotheque Fr. Mitterrand"));
            //listStations.add(olympiades);
			ligne14.getListStations().add(this.getStation("Olympiades"));
        this.allLignes.add(ligne1);
        this.allLignes.add(ligne4);
        this.allLignes.add(ligne8);
        this.allLignes.add(ligne12);
        this.allLignes.add(ligne14);
    }

   public void createStations()
    {
       Station chateauDeVincennes = new Station ("Chateau de Vincennes", 3, false);
        Station berault = new Station ("Berault", 4, false);
        Station saintMande = new Station ("Saint Mande", 2, false);
        Station porteDeVincennes = new Station ("Porte de Vincennes", 3, false);
        Station nation = new Station ("Nation", 2, false);
        Station reuillyDiderot = new Station ("Reuilly-Diderot", 4, false);
        Station gareDeLyon = new Station ("Gare de Lyon", 1, false);
        Station bastille = new Station ("Bastille", 5, false);
        Station stPaul = new Station ("St-Paul", 6, false);
        Station hotelDeVille = new Station ("Hotel de Ville", 2, false);
        Station chatelet = new Station ("Chatelet", 1, false);
        Station louvreRivoli = new Station ("Louvre Rivoli", 3, false);
        Station palaisRoyalMuseeDuLouvre = new Station ("Palais royal - Musee du Louvre", 2, false);
        Station tuileries = new Station ("Tuileries", 6, false);
        Station concorde = new Station ("Concorde", 3, false);
        Station champsElyseesClemenceau = new Station ("Champs Elysees - Clemenceau", 2, false);
        Station franklinDRoosevelt = new Station ("Franklin D. Roosevelt", 7, false);
        Station georgeV = new Station ("georgeV", 3, false);
        Station charlesDeGaulleEtoile = new Station ("Charles de Gaulle Etoile", 1, false);
        Station argentine = new Station ("Argentine", 10, false);
        Station porteMaillot = new Station ("Porte Maillot", 4, false);
        Station lesSablons = new Station ("Les Sablons", 3, false);
        Station pontDeNeuilly = new Station ("Pont de Neuilly", 2, false);
        Station esplanadeDeLaDefense = new Station ("Esplanade de la Defense", 2, false);
        Station laDefense = new Station ("La Defense", 1, false);
            // Stations surtout pour la ligne 4
        Station porteDeClignancourt = new Station ("Porte de Clignancourt", 2, false);
        Station simplon = new Station ("Simplon", 3, false);
        Station marcadetPoissonniers = new Station ("Marcadet Poissonniers", 5, false);
        Station chateauRouge = new Station ("Chateau Rouge", 6, false);
        Station barbesRochechouart = new Station ("Barbes Rochechouart", 4, false);
        Station gareDuNord = new Station ("Gare du Nord", 2, false);
        Station gareDeLEst = new Station ("Gare de l'Est", 2, false);
        Station chateauDEau = new Station ("Chateau d'Eau", 6, false);
        Station strasbourgSaintDenis = new Station ("Strasbourg Saint-Denis", 3, false);
        Station reaumurSebastopol = new Station ("Reaumur Sebastopol", 6, false);
        Station etienneMarcel = new Station ("Etienne Marcel", 5, false);
        Station lesHalles = new Station ("Les Halles", 2, false);
        //Station chatelet = new Station ("Berault", 4, false);
        Station cite = new Station ("Cite", 4, false);
        Station stMichel = new Station ("St-Michel", 6, false);
        Station odeon = new Station ("Odeon", 5, false);
        Station saintGermainDesPres = new Station ("Saint Germain des-Pres", 2, false);
        Station saintSulpice = new Station ("Saint-Sulpice", 3, false);
        Station stPlacide = new Station ("St-Placide", 8, false);
        Station montparnasseBienvenue = new Station ("Montparnasse Bienvenue", 3, false);
        Station valvin = new Station ("Valvin", 2, false);
        Station raspail = new Station ("Raspail", 1, false);
        Station denfertRochereau = new Station ("Denfert Rochereau", 1, false);
        Station moutonDuvernet = new Station ("Mouton Duvernet", 4, false);
        Station alesia = new Station ("Alesia", 4, false);
        Station porteDOrleans = new Station ("Porte d'Orleans", 9, false);
            // Stations surtout pour la ligne 8
        Station balard = new Station ("Balard", 7, false);
        Station lourmel = new Station ("Lourmel", 4, false);
        Station boucicaut = new Station ("Boucicaut", 3, false);
        Station felixFaure = new Station ("Felix Faure", 2, false);
        Station commerce = new Station ("Commerce", 7, false);
        Station laMottePicquetGrenelle = new Station ("La Motte Picquet Grenelle", 2, false);
        Station ecoleMilitaire = new Station ("Ecole Militaire", 1, false);
        Station laTourMaubourg = new Station ("La Tour Maubourg", 3, false);
        Station invalides = new Station ("Invalides", 5, false);
        //Station concorde = new Station ("Concorde", 4, false);
        Station madeleine = new Station ("Madeleine", 6, false);
        Station opera = new Station ("Opera", 2, false);
        Station richelieuDrouot = new Station ("Richelieu Drouot", 2, false);
        Station grandsBoulevards = new Station ("Grands Boulevards", 4, false);
        Station bonneNouvelle = new Station ("Bonne Nouvelle", 6, false);
        //Station strasbourgSaintDenis = new Station ("Strasbourg Saint-Denis", 4, false);
        Station republique = new Station ("Republique", 8, false);
        Station fillesDuCalvaire = new Station ("Filles du Calvaire", 2, false);
        Station saintSebastienFroissart = new Station ("St-Sebastien Froissart", 7, false);
        Station cheminVert = new Station ("Chemin Vert", 6, false);
        //Station bastille = new Station ("Bastille", 4, false);
        Station ledruRollin = new Station ("Ledru-Rollin", 3, false);
        Station faidherbeChaligny = new Station ("Faidherbe Chaligny", 2, false);
        //Station reuillyDiderot = new Station ("Reuilly-Diderot", 4, false);
        Station montgallet = new Station ("Montgallet", 1, false);
        Station daumesnil = new Station ("Daumesnil", 2, false);
        Station michelBizot = new Station ("Michel Bizot", 8, false);
        Station porteDoree = new Station ("Porte Doree", 9, false);
        Station porteDeCharenton = new Station ("Porte de Charenton", 5, false);
        Station liberte = new Station ("Liberte", 3, false);
        Station charentonEcoles = new Station ("Charenton-Ecoles", 2, false);
        Station ecoleVeterinaireDeMaisonsAlfort = new Station ("Ecole Veterinaire de Maisons-Alfort", 3, false);
        Station maisonsAlfortStade = new Station ("Maisons-Alfort-Stade", 4, false);
        Station maisonsAlfortLesJuilliotes = new Station ("Maisons-Alfort Les Juilliotes", 5, false);
        Station creteilLEchat = new Station ("Creteil L'Echat", 7, false);
        Station creteilUniversite = new Station ("Creteil-Universite", 2, false);
        Station creteilPrefecture = new Station ("Creteil-Prefecture", 5, false);
        Station creteilPointeDuLac = new Station ("Creteil Pointe du Lac", 4, false);
            // Stations surtout pour la ligne 12
        Station porteDeLaChapelle = new Station ("Porte de la Chapelle", 1, false);
        Station marxDormoy = new Station ("Marx Dormoy", 3, false);
        //Station marcadetPoissonniers = new Station ("Alesia", 4, false);
        Station julesJoffrin = new Station ("Jules Joffrin", 2, false);
        Station lamarckCaulaincourt = new Station ("Lamarck Caulaincourt", 1, false);
        Station abbesses = new Station ("Abbesses", 10, false);
        Station pigalle = new Station ("Pigalle", 8, false);
        Station saintGeorges = new Station ("Saint-Georges", 4, false);
        Station notreDameDeLorette = new Station ("Notre-Dame de-Lorette", 5, false);
        Station triniteDEstienneDOrves = new Station ("Trinite d'Estienne d'Orves", 1, false);
        //Station saintLazare = new Station ("Saint-Lazare", 4, false);
        //Station madeleine = new Station ("Madeleine", 4, false);
        //Station concorde = new Station ("Concorde", 4, false);
        Station assembleeNationale = new Station ("Assemblee Nationale", 1, false);
        Station solferino = new Station ("Solferino", 9, false);
        Station rueDuBac = new Station ("Rue du Bac", 5, false);
        Station sevresBabylone = new Station ("Sevres Babylone", 3, false);
        Station rennes = new Station ("Rennes", 2, false);
        Station notreDameDesChamps = new Station ("Notre-Dame des-Champs", 4, false);
        //Station montparnasseBienvenue = new Station ("Montparnasse Bienvenue", 4, false);
        Station falguiere = new Station ("Falguiere", 5, false);
        Station pasteur = new Station ("Pasteur", 2, false);
        Station volontaires = new Station ("Volontaires", 10, false);
        Station vaugirard = new Station ("Vaugirard", 3, false);
        Station convention = new Station ("Convention", 2, false);
        Station porteDeVersailles = new Station ("Porte de Versailles", 1, false);
        Station corentinCelton = new Station ("Corentin Celton", 5, false);
        Station mairieDIssy = new Station ("Mairie d'Issy", 6, false);
            //Stations surtout pour la ligne 14
        Station saintLazare = new Station ("Saint-Lazare", 9, false);
        //Station madeleine = new Station ("Madeleine", 4, false);
        Station pyramides = new Station ("Pyramides", 8, false);
        //Station chatelet = new Station ("Chatelet", 4, false);
        //Station gareDeLyon = new Station ("Gare de Lyon", 4, false);
        Station bercy = new Station ("Bercy", 4, false);
        Station courStEmilion = new Station ("Cour St-Emilion", 2, false);
        Station bibliothequeFrMitterrand = new Station ("Bibliotheque Fr. Mitterrand", 3, false);
        Station olympiades = new Station ("Olympiades", 5, false);

        /* La lsite des adjacents */

         chateauDeVincennes.addAdjacent(new Adjacent (berault, 1));
        berault.addAdjacent(new Adjacent (saintMande, 1));
        saintMande.addAdjacent(new Adjacent (porteDeVincennes, 2));
        porteDeVincennes.addAdjacent(new Adjacent (nation, 3));
        nation.addAdjacent(new Adjacent (reuillyDiderot, 4));
        reuillyDiderot.addAdjacent(new Adjacent (gareDeLyon, 4));
        gareDeLyon.addAdjacent(new Adjacent (bastille, 5));
        bastille.addAdjacent(new Adjacent (stPaul, 2));
        stPaul.addAdjacent(new Adjacent (hotelDeVille, 3));
        hotelDeVille.addAdjacent(new Adjacent (chatelet, 2));
        chatelet.addAdjacent(new Adjacent (louvreRivoli, 4));
        louvreRivoli.addAdjacent(new Adjacent (palaisRoyalMuseeDuLouvre, 1));
        palaisRoyalMuseeDuLouvre.addAdjacent(new Adjacent (tuileries, 2));
        tuileries.addAdjacent(new Adjacent (concorde, 2));
        concorde.addAdjacent(new Adjacent (champsElyseesClemenceau, 1));
        champsElyseesClemenceau.addAdjacent(new Adjacent (franklinDRoosevelt, 1));
        franklinDRoosevelt.addAdjacent(new Adjacent (georgeV, 2));
        georgeV.addAdjacent(new Adjacent (charlesDeGaulleEtoile, 3));
        charlesDeGaulleEtoile.addAdjacent(new Adjacent (argentine, 2));
        argentine.addAdjacent(new Adjacent (porteMaillot, 3));
        porteMaillot.addAdjacent(new Adjacent (lesSablons, 2));
        lesSablons.addAdjacent(new Adjacent (pontDeNeuilly, 2));
        pontDeNeuilly.addAdjacent(new Adjacent (esplanadeDeLaDefense, 1));
        esplanadeDeLaDefense.addAdjacent(new Adjacent (laDefense, 1));
            //Sur la ligne 4
        porteDeClignancourt.addAdjacent(new Adjacent (simplon, 1));
        simplon.addAdjacent(new Adjacent (marcadetPoissonniers, 2));
        marcadetPoissonniers.addAdjacent(new Adjacent (chateauRouge, 1));
        chateauRouge.addAdjacent(new Adjacent (barbesRochechouart, 4));
        barbesRochechouart.addAdjacent(new Adjacent (gareDuNord, 4));
        gareDuNord.addAdjacent(new Adjacent (gareDeLEst, 2));
        gareDeLEst.addAdjacent(new Adjacent (chateauDEau, 2));
        chateauDEau.addAdjacent(new Adjacent (strasbourgSaintDenis, 3));
        strasbourgSaintDenis.addAdjacent(new Adjacent (reaumurSebastopol, 5));
        reaumurSebastopol.addAdjacent(new Adjacent (etienneMarcel, 2));
        etienneMarcel.addAdjacent(new Adjacent (lesHalles, 1));
        lesHalles.addAdjacent(new Adjacent (chatelet, 1));
        chatelet.addAdjacent(new Adjacent (cite, 2));
        cite.addAdjacent(new Adjacent (stMichel, 3));
        stMichel.addAdjacent(new Adjacent (odeon, 1));
        odeon.addAdjacent(new Adjacent (saintGermainDesPres, 3));
        saintGermainDesPres.addAdjacent(new Adjacent (saintSulpice, 2));
        saintSulpice.addAdjacent(new Adjacent (stPlacide, 3));
        stPlacide.addAdjacent(new Adjacent (montparnasseBienvenue, 2));
        montparnasseBienvenue.addAdjacent(new Adjacent (valvin, 2));
        valvin.addAdjacent(new Adjacent (raspail, 2));
        raspail.addAdjacent(new Adjacent (denfertRochereau, 1));
        denfertRochereau.addAdjacent(new Adjacent (moutonDuvernet, 2));
        moutonDuvernet.addAdjacent(new Adjacent (alesia, 3));
        alesia.addAdjacent(new Adjacent (porteDOrleans, 1));
        porteDOrleans.addAdjacent(new Adjacent (alesia, 1));
            //Sur la ligne 8
        balard.addAdjacent(new Adjacent (lourmel, 3));
        lourmel.addAdjacent(new Adjacent (boucicaut, 2));
        boucicaut.addAdjacent(new Adjacent (felixFaure, 2));
        felixFaure.addAdjacent(new Adjacent (commerce, 1));
        commerce.addAdjacent(new Adjacent (laMottePicquetGrenelle, 2));
        laMottePicquetGrenelle.addAdjacent(new Adjacent (ecoleMilitaire, 4));
        ecoleMilitaire.addAdjacent(new Adjacent (laTourMaubourg, 3));
        laTourMaubourg.addAdjacent(new Adjacent (invalides, 1));
        invalides.addAdjacent(new Adjacent (concorde, 2));
        concorde.addAdjacent(new Adjacent (madeleine, 2));
        madeleine.addAdjacent(new Adjacent (opera, 6));
        opera.addAdjacent(new Adjacent (richelieuDrouot, 3));
        richelieuDrouot.addAdjacent(new Adjacent (grandsBoulevards, 2));
        grandsBoulevards.addAdjacent(new Adjacent (bonneNouvelle, 3));
        bonneNouvelle.addAdjacent(new Adjacent (strasbourgSaintDenis, 1));
        strasbourgSaintDenis.addAdjacent(new Adjacent (republique, 5));
        republique.addAdjacent(new Adjacent (fillesDuCalvaire, 3));
        fillesDuCalvaire.addAdjacent(new Adjacent (saintSebastienFroissart, 1));
        saintSebastienFroissart.addAdjacent(new Adjacent (cheminVert, 1));
        cheminVert.addAdjacent(new Adjacent (bastille, 1));
        bastille.addAdjacent(new Adjacent (ledruRollin, 2));
        ledruRollin.addAdjacent(new Adjacent (faidherbeChaligny, 3));
        faidherbeChaligny.addAdjacent(new Adjacent (reuillyDiderot, 2));
        reuillyDiderot.addAdjacent(new Adjacent (montgallet, 2));
        montgallet.addAdjacent(new Adjacent (daumesnil, 2));
        daumesnil.addAdjacent(new Adjacent (michelBizot, 1));
        michelBizot.addAdjacent(new Adjacent (porteDoree, 1));
        porteDoree.addAdjacent(new Adjacent (porteDeCharenton, 2));
        porteDeCharenton.addAdjacent(new Adjacent (liberte, 2));
        liberte.addAdjacent(new Adjacent (charentonEcoles, 2));
        charentonEcoles.addAdjacent(new Adjacent (ecoleVeterinaireDeMaisonsAlfort, 2));
        ecoleVeterinaireDeMaisonsAlfort.addAdjacent(new Adjacent (maisonsAlfortStade, 1));
        maisonsAlfortStade.addAdjacent(new Adjacent (maisonsAlfortLesJuilliotes, 1));
        maisonsAlfortLesJuilliotes.addAdjacent(new Adjacent (creteilLEchat, 1));
        creteilLEchat.addAdjacent(new Adjacent (creteilUniversite, 1));
        creteilUniversite.addAdjacent(new Adjacent (creteilPrefecture, 1));
        creteilPrefecture.addAdjacent(new Adjacent (creteilPointeDuLac, 1));
            //Sur la ligne 12
        porteDeLaChapelle.addAdjacent(new Adjacent (marxDormoy, 2));
        marxDormoy.addAdjacent(new Adjacent (marcadetPoissonniers, 8));
        marcadetPoissonniers.addAdjacent(new Adjacent (julesJoffrin, 2));
        julesJoffrin.addAdjacent(new Adjacent (lamarckCaulaincourt, 1));
        lamarckCaulaincourt.addAdjacent(new Adjacent (abbesses, 1));
        abbesses.addAdjacent(new Adjacent (pigalle, 1));
        pigalle.addAdjacent(new Adjacent (saintGeorges, 3));
        saintGeorges.addAdjacent(new Adjacent (notreDameDeLorette, 4));
        notreDameDeLorette.addAdjacent(new Adjacent (triniteDEstienneDOrves, 3));
        triniteDEstienneDOrves.addAdjacent(new Adjacent (saintLazare, 3));
        saintLazare.addAdjacent(new Adjacent (madeleine, 10));
        madeleine.addAdjacent(new Adjacent (concorde, 2));
        concorde.addAdjacent(new Adjacent (assembleeNationale, 3));
        assembleeNationale.addAdjacent(new Adjacent (solferino, 2));
        solferino.addAdjacent(new Adjacent (rueDuBac, 2));
        rueDuBac.addAdjacent(new Adjacent (sevresBabylone, 1));
        sevresBabylone.addAdjacent(new Adjacent (rennes, 2));
        rennes.addAdjacent(new Adjacent (notreDameDesChamps, 3));
        notreDameDesChamps.addAdjacent(new Adjacent (montparnasseBienvenue, 2));
        montparnasseBienvenue.addAdjacent(new Adjacent (falguiere, 3));
        falguiere.addAdjacent(new Adjacent (pasteur, 1));
        pasteur.addAdjacent(new Adjacent (volontaires, 3));
        volontaires.addAdjacent(new Adjacent (vaugirard, 2));
        vaugirard.addAdjacent(new Adjacent (convention, 2));
        convention.addAdjacent(new Adjacent (porteDeVersailles, 1));
        porteDeVersailles.addAdjacent(new Adjacent (corentinCelton, 4));
        corentinCelton.addAdjacent(new Adjacent (mairieDIssy, 2));
            //Sur la ligne 14
        saintLazare.addAdjacent(new Adjacent (madeleine, 6));
        madeleine.addAdjacent(new Adjacent (pyramides, 3));
        pyramides.addAdjacent(new Adjacent (chatelet, 7));
        chatelet.addAdjacent(new Adjacent (gareDeLyon, 6));
        gareDeLyon.addAdjacent(new Adjacent (bercy, 4));
        bercy.addAdjacent(new Adjacent (courStEmilion, 3));
        courStEmilion.addAdjacent(new Adjacent (bibliothequeFrMitterrand, 1));
        bibliothequeFrMitterrand.addAdjacent(new Adjacent (olympiades, 3));
       this.setStartStation(berault);
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
    int i=0;
    // System.out.println("Croisement dans T"+T.getS().getNom());
    if(T.getLigne1()!=null && T.getLigne2()!=null && T.getS()!=null ){
        System.out.println();
        System.out.println("TRAJET "+" Prenez la "+T.getLigne1().getNom()+" , " +
                        "descendre a la Station "+ T.getS().getNom()+
                       " puis prendre le "+T.getLigne2().getNom() );//+
    } else
         System.out.println("TRAJET DIRECT "+" Prenez la "+T.getLigne1().getNom()+"\n");

}

   /*--------------METHODE RETOURNANT LES INTERSECTIONS ------------*/

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
