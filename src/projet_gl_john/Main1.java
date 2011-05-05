/**
 * Classe principale pour tester le reseau qui servira de reference au projet
 * @author Comma, Diallo, Minatchy, Ngatcha
 * @version 1.0
 */
public class Main1 {
    public static void main (String[] args) {

        /* Creation du reseau */
        Reseau reseau = CreationReseauNew.initialiserReseau();

        /* Creation des lignes */
        Ligne ligne1 = CreationLignes.initialiserLigne(1);
        Ligne ligne4 = CreationLignes.initialiserLigne(4);
        Ligne ligne8 = CreationLignes.initialiserLigne(8);
        Ligne ligne12 = CreationLignes.initialiserLigne(12);
        Ligne ligne14 = CreationLignes.initialiserLigne(14);

        //ligne1.afficherListeStations();
        //ligne4.afficherListeStations();
        //ligne8.afficherListeStations();
        //ligne12.afficherListeStations();
        //ligne14.afficherListeStations();

        //reseau.afficherReseau();
        System.out.println("Station de départ : "+reseau.getStartStation().getNom());
        //System.out.println("Taille du reseau : "+reseau.getAllStations().size());
    }
}
