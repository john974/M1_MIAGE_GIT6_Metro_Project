/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projet_gl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;

public final class Station implements Comparable<Station>{

    public void setPredecesseur(Station predecesseur) {
        this.predecesseur = predecesseur;
    }
    private static int nodeCount = 1;

    private int numero;
    private String nom;
    private double tempArret;
    private boolean incident = false;
    private Station predecesseur = null;
    private LinkedList<Adjacent> adjacents = new LinkedList<Adjacent>();

    public Station(String nom) {
        this.nom = nom;
    }

    
    public Station(String nom, double tempArret) {
        this.numero = nodeCount++;
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
        p("Nom: "+this.getNom()+"\nNumero: "+this.getNumero()+"\n");
    }

    public void  chemin()
    {
        Iterator iter  = this.adjacents.iterator();
        while(iter.hasNext())
        {
           Adjacent a = (Adjacent)iter.next();
           p(this.getNom()+"-"+a.getCost()+"-"+a.getNode().getNom());
        }
        
    }

    /* ctte methode ajoute un adjacents a la liste des adjacents.**/
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
    /* cettte fonction calcul la distance qui separe deux sommets adjacents .**/
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
     /* cette méthode retourne le plus proche voisins d'un sommet
      cette méthode n'est plus importante car les voisins sont desormais triée ***/
   public Adjacent ppv()
   {
       Adjacent tmp = this.getAdjacents().getFirst();
        for(Adjacent a : this.getAdjacents())
        {
            if(a.getCost() < tmp.getCost())
            {
                tmp = a;
            }
        }

        return tmp;
   }

    public Station getPredecesseur() {
        return predecesseur;
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
    public void p(String s)
    {
        System.out.println(s);
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
        if ((this.nom == null) ? (other.nom != null) : !this.nom.equals(other.nom)) {
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
    
    /*definition d'une méthode de trie des sommets adjacents **/
    public void sortAdjacents()
    {
        Collections.sort(adjacents);
    }

}
