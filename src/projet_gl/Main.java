/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projet_gl;

import java.util.LinkedList;
import outils.Saisie;

public class Main {

    public static void main(String[] args) {

        Reseau r = new Reseau();
        r.init();
        LinkedList<String> ensemble = new LinkedList<String>();
        Saisie.p("************************************************************");
        Saisie.p("Bienvenue sur le reseau de transport de Paris              *");
        Saisie.p("Acceder au menu general et suivez les instructions svp     *");
        Saisie.p("************************************************************\n");
        
        StringBuffer menu = new StringBuffer();

        //Scanner scanner = new Scanner (System.in);
        int selection = 0;

        menu.append("\n**** Faites une selection: *********************************\n");
        menu.append("[1]:RECHERCHE DE L'ITINERAIRE LE PLUS RAPIDE               *\n");
        menu.append("[2]:ITINERAIRE AVEC LE MOINS DE CHANGEMENTS DE LIGNES      *\n");
        menu.append("[3]:ITINERAIRE EN PASSANT PAR CERTAINES STATIONS           *\n");
        menu.append("[4]:EXIT                                                   *\n");
        menu.append("************************************************************\n");
        menu.append("\n==>Selection: ");
        String pattern = "[1-4]";
        do
        {
            selection = Integer.parseInt(Saisie.menu(menu, pattern));
            switch(selection ){
                case 1:
                    System.out.println("***** RECHERCHE DE L'ITINERAIRE LE PLUS RAPIDE *****");
                    String d = Saisie.lireString("DONNEZ LA STATION DE DEPART !");
                    String a = Saisie.lireString("DONNEZ LA STATION D'ARRIVEE !");
                    r.plusCourtChemin(d,a);
                break;

                case 2:
                 System.out.println("***** ITINERAIRE AVEC LE MOINS DE CHANGEMENTS DE LIGNES *****");
                 /*str = scanner.nextLine();
                 System.out.println("Veuillez choisir votre station de départ" );
                 str = scanner.nextLine();
                 System.out.println("Veuillez choisir votre statioin d'arrivée");
                 str = scanner.nextLine();
                 System.out.println("Votre itinéraire");*/
                 break;



                 case 3:
                 System.out.println("***** ITINERAIRE EN PASSANT PAR CERTAINES STATIONS *****");
                 d = Saisie.lireString("DONNEZ LA STATION DE DEPART !");
                 ensemble.add(d);
                 String i = Saisie.lireString("DONNEZ LES STATIONS  INTERMEDIAIRES !");
                 String[] k = i.split("\\s");
                 for(String st : k)
                     ensemble.add(st);
                 a = Saisie.lireString("DONNEZ LA STATION D'ARRIVEE !");
                 ensemble.add(a);
                 r.plusCourtChemin(ensemble);
                /* str = scanner.nextLine();
                 System.out.println("Veuillez choisir votre station de depart" );
                 str = scanner.nextLine();
                 System.out.println("Veuillez choisir votre statioin d'arrivée");
                 str = scanner.nextLine();
                 System.out.println("Veuillez saisir les stations intermédiaires");
                 str = scanner.nextLine();
                 System.out.println("Votre trajet est:");*/
                break;
                case 4:
                    Saisie.p("==> Merci et à Bientot <==");
                break;
             }
        }while(selection != 4);
      
      // r.essai();
    }

}
