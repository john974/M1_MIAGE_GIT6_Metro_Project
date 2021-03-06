/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;
import outils.Saisie;

public final class Station implements Comparable<Station>{

    public void setPredecesseur(Station predecesseur) {
        this.predecesseur = predecesseur;
    }
    private static int nodeCount = 1;

    private int numero;
    private String nom;
    private double tempArret;
    private boolean incident ;
    private Station predecesseur = null;
    private LinkedList<Adjacent> adjacents = new LinkedList<Adjacent>();

    public Station(String nom) {
        this.nom = nom;
    }

    public Station( String nom,int numero, boolean incident) {
        this.numero = nodeCount++;
        this.tempArret = numero;
        this.nom = nom;
        this.incident = incident;
    }

    
    public Station(String nom, double tempArret) {
       
        this.nom = nom;
        this.tempArret = tempArret;
    }

    public void setIncident(boolean incident) {
        this.incident = incident;
    }

    public LinkedList<Adjacent> getAdjacents() {
        return adjacents;
    }

    public int getNumero() {
        return numero;
    }

    public void setAdjacents(LinkedList<Adjacent> adjacents) {
        this.adjacents = adjacents;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTempArret(double tempArret) {
        this.tempArret = tempArret;
    }

    public boolean isIncident() {
        return incident;
    }

    public String getNom() {
        return nom;
    }

    public double getTempArret() {
        return tempArret;
    }

    public void afficher()
    {
        Saisie.p("Nom: "+this.getNom()+"\nNumero: "+this.getNumero()+"\n");
    }

    @Override
    public String toString() {
        String res = "Station{ " + "numero = " + numero + "\nnom = " + nom + "\ntempArret = " + tempArret + "\nincident = " + incident+"\nadjacents = ";
        for(Adjacent a : this.getAdjacents())
            res += "["+a.getNode().getNom()+"]";
        return res;
    }


    public String  chemin()
    {
        StringBuffer s = new StringBuffer("");
        if(this.predecesseur == null)
            return s.append(new StringBuffer(this.nom+"")).toString();
           // return new StringBuffer("-");
        else
            s.append(this.predecesseur.chemin()).append("-").append(this.nom);

        return s.toString();
    }

    /** cette methode ajoute un adjacent a la liste des adjacents.
     * chaque station a des voisins dont il connait l'existance.
     * Il faut noter qu'a chaque fois une station A devient adjacent avec une station B,
     * la station B devient automatiquement adjacent avec la station A, i.e la station B ajoute aussi A
     * dans sa liste des stations.
     * @author groupe de projet
     * @param un objet de type station
     * @return void.
     */
    public void addAdjacent(Adjacent A)
    {
        /* si la station courante contient deja A comme Adjacent, on ne l'ajoute pas
         sinon on l'ajoute comme nouveau sommet adjacent */
        if(this.adjacents.contains(A))
            return;
        else
        {
            this.adjacents.add(A);
            this.sortAdjacents();
        }

        /* meme processus le contenu dans la classe adjacent A**/
        if(A.getNode().adjacents.contains(this))
            return;
        else
        {
            (A.getNode().getAdjacents()).add(new Adjacent(this,A.getCost()));
            A.getNode().sortAdjacents();
        }
    }
    /** cettte fonction calcul la distance qui separe deux sommets adjacents.
     * @author groupe de projet
     * @param un objet de type double
     * @return la distance qui sépapre les deux station ou -1 si les deux stations ne sont pas des voisins.
     */
    public double distance(Station s)
    {
        double dist = -1;
        for(Adjacent a : this.getAdjacents())
        {
            if(a.getNode().getNom().compareTo(s.getNom()) == 0)
                dist = a.getCost();
        }

        return dist;
    }

    public Station getPredecesseur() {
        return predecesseur;
    }
     /** cettte fonction est utilisé dans le calcul du plus court chemin.
      * elle met à jour un sommet qui exsite déja dans l'arbre des chemins explorables.
      * Cette opération est effectué si le sommet en qustion peut être atteinte avec un cout minimal
      * en passant par un autre sommet; ce dernier sommet devient son predecesseur
     * @author groupe de projet
     * @param un objet de type station
     * @return la distance qui sépapre les deux station ou -1 si les deux stations ne sont pas des voisins.
     */
    public void updatePredecesseur(Station s)
    {
        Station tmp = this;
        while(tmp.getPredecesseur() != null)
            tmp = tmp.getPredecesseur();
        tmp.setPredecesseur(s);
    }
   /*cette methode teste si deux sommets sont adjacents **/
   public boolean isAdjacent(Station s)
   {
       for(Adjacent a : this.getAdjacents())
       {
           if(a.getNode().equals(s))
               return true;
       }

       return false;
   }

    /* definition de la méthode  compareTo de l'interface comparable **/
    public int compareTo(Station o) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.getNom().compareTo(o.getNom()) < 0 ? -1 :
            this.getNom().compareTo(o.getNom()) == 0 ? 0 : 1;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Station other = (Station) obj;
        if ((this.nom == null) ? (other.nom != null) : !this.nom.toUpperCase().equals(other.nom.toUpperCase())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.nom != null ? this.nom.hashCode() : 0);
        return hash;
    }
    
    /** cette procedure trie la liste des sommets adjacents au sommets courants.
     * Le trie est effectue en fonction de la distance qui les sépare
     * @author groupe de projet
     * @param -
     * @return -.
     */
    public void sortAdjacents()
    {
        Collections.sort(adjacents);
    }

}
