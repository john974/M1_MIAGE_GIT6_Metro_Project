/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

import java.util.LinkedList;
import outils.Saisie;

public class Main {

    public static void main(String[] args) {

        /**
         * Création et initialisation du reseau 
         */
        Reseau r = new Reseau(); 
        r.init();

        LinkedList<String> ensemble = new LinkedList<String>();
        Saisie.p("************************************************************");
        Saisie.p("Bienvenue sur le reseau de transport de Paris              *");
        Saisie.p("Acceder au menu general et suivez les instructions svp     *");
        Saisie.p("************************************************************\n");
        
        StringBuffer menu = new StringBuffer();
        int selection = 0;

        menu.append("\n**** Faites une selection: *********************************\n");
        menu.append("[1]:RECHERCHE DE L'ITINERAIRE LE PLUS RAPIDE               *\n");
        menu.append("[2]:ITINERAIRE AVEC LE MOINS DE CHANGEMENTS DE LIGNES      *\n");
        menu.append("[3]:ITINERAIRE EN PASSANT PAR CERTAINES STATIONS           *\n");
        menu.append("[4]:AFFICHER LE RESEAU                                     *\n");
        menu.append("[5]:ADMINISTRATION                                         *\n");
        menu.append("[6]:EXIT                                                   *\n");
        menu.append("************************************************************\n");
        menu.append("\n==>Selection: ");
        String pattern = "[1-6]";
        do
        {
           selection = Integer.parseInt(Saisie.menu(menu, pattern));
            switch(selection ){
                case 1:
                    System.out.println("***** RECHERCHE DE L'ITINERAIRE LE PLUS RAPIDE *****");
                    String d = Saisie.lireString("DONNEZ LA STATION DE DEPART !");
                    String a = Saisie.lireString("DONNEZ LA STATION D'ARRIVEE !");
                    ensemble.add(d);
                    ensemble.add(a);
                    Saisie.p(r.plusCourtChemin(ensemble));
                break;

                case 2:
                 System.out.println("***** ITINERAIRE AVEC LE MOINS DE CHANGEMENTS DE LIGNES *****");                
                 String depart = Saisie.lireString("DONNEZ LA STATION DE DEPART !");
                 String arrivee = Saisie.lireString("DONNEZ LA STATION D'ARRIVEE !");
                 Station dep = r.getStation(depart);
                  Station arr = r.getStation(arrivee);
                 r.MoinsDeChangement(dep,arr);
                 break;

                 case 3:
                 System.out.println("***** ITINERAIRE EN PASSANT PAR CERTAINES STATIONS *****");
                 d = Saisie.lireString("DONNEZ LA STATION DE DEPART !");
                 ensemble.add(d);
                 String i = Saisie.lireString("DONNEZ LES STATIONS  INTERMEDIAIRES SEPARES PAR DES ESPACES!");
                 String[] k = i.split("\\s");
                 for(String st : k)
                     ensemble.add(st);
                 a = Saisie.lireString("DONNEZ LA STATION D'ARRIVEE !");
                 ensemble.add(a);
                 Saisie.p(r.plusCourtChemin(ensemble));
                break;

                case 4:
                    Saisie.p(r.toString());
                break;

                case 5:
                    Saisie.p("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    Saisie.p("Ce module était prévu pour administrer le reseau, c'est a \n"
                            + "dire ajouter et modifier les stations et les lignes.\n"
                            + "Cependant elle n'a pu être impléménté par manque de temps !");
                    Saisie.p("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                break;
                
                case 6:
                    Saisie.p("==> Merci et à Bientot <==");
                break;
             }
        }while(selection != 6);
    }
}
