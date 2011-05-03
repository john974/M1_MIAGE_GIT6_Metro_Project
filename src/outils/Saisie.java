/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package outils;

import java.util.Scanner;

/**
 *
 * @author kandji
 */
public class Saisie{

    /* cette méthode permet de lire des enttrée.Il prend en parametre un string qu'on peut afficher
     dans le cas ou c'est un menu contextuel; i.e il faut faire choix parmi plusieurs **/
    public static String lireString(String s)
    {
        String txt = "";
        System.out.println(s);
        Scanner sc ;
        try
        {
            sc = new Scanner(System.in);
            txt = sc.nextLine();
        }
        catch(Exception ex)
        {
            ex.fillInStackTrace();
        }

        return txt.trim();/* on supprime les espaces vides**/
    }

    /* petite procedure qui permet de d'afficher(print) n'importe quel type d'objet **/
    public static void p(Object o)
    {
        System.out.println(o);
    }
}
