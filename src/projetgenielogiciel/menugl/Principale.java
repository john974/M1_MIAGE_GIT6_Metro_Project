package menugl;
/**
 *
 * @author guy
 */
import java.util.Scanner;

public class Principale{
    public static void main(String [] args){

        System.out.println("Bienvenue sur le reseau de transport de BoudonVille");
        System.out.println("Acceder au menu general et faites le choix de votre itinéraire");
        String str;

         Scanner scanner = new Scanner (System.in);
        int selection = 0;
        int choixMenu = 0;

        
        System.out.println("Faites une selection:");
        System.out.println("[1]:RECHERCHE DE L'ITINERAIRE LE PLUS RAPIDE");
        System.out.println("[2]:ITINERAIRE AVEC LE MOINS DE CHANGEMENTS DE LIGNES");
        System.out.println("[3]:ITINERAIRE EN PASSANT PAR CERTAINES STATIONS");
        System.out.println("[4]:EXIT");

            System.out.println("Selection: ");
            selection=scanner.nextInt();

            
                switch(selection ){


                    case 1:
                    System.out.println("***** RECHERCHE DE L'ITINERAIRE LE PLUS RAPIDE *****");
                    str = scanner.nextLine();
                    System.out.println("Veuillez saisir votre station de départ");
                    str = scanner.nextLine();
                    System.out.println("Veuillez saisir votre Station d'arrivée");
                    str = scanner.nextLine();
                    System.out.println("Votre itinéraire: " );
                    break;
            
                    case 2:
                     System.out.println("***** ITINERAIRE AVEC LE MOINS DE CHANGEMENTS DE LIGNES *****");
                     str = scanner.nextLine();
                     System.out.println("Veuillez choisir votre station de départ" );
                     str = scanner.nextLine();
                     System.out.println("Veuillez choisir votre statioin d'arrivée");
                     str = scanner.nextLine();
                     System.out.println("Votre itinéraire");
                     break;
                     
            

                     case 3:
                     System.out.println("***** ITINERAIRE EN PASSANT PAR CERTAINES STATIONS *****");
                     str = scanner.nextLine();
                     System.out.println("Veuillez choisir votre station de depart" );
                     str = scanner.nextLine();
                     System.out.println("Veuillez choisir votre statioin d'arrivée");
                     str = scanner.nextLine();
                     System.out.println("Veuillez saisir les stations intermédiaires");
                     str = scanner.nextLine();
                     System.out.println("Votre trajet est:");
                     break;

                    default: System.out.println("Veuillez recommencer s'il vous plait");
                    break;
                     
               }

      }
       
}
 

