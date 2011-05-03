/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetgenielogiciel;
//import java.util.HashMap;
//import java.util.Iterator;
import java.util.*;

public class Station {
     
    private static int nodeCount=0;
    private  int numero;
    private String nom;
   
    private double tempArret;
    boolean incident;
  // HashMap<Station ,Double>  adjacents;
    LinkedList<Adjacent> adjacents = new LinkedList<Adjacent>();
    public Station(String nom, double tempArret) {
        this.nom = nom;
        this.tempArret = tempArret;
        this.incident = false;
        this.numero=nodeCount++;
       
    }
    
    

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }


 

    public LinkedList<Adjacent> getAdjacents() {
        return adjacents;
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

    public void setAdjacents(LinkedList<Adjacent> adjacents) {
        this.adjacents = adjacents;
    }

    public void setIncident(boolean incident) {
        this.incident = incident;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTempArret(double tempArret) {
        this.tempArret = tempArret;
    }


  public void addAdjacent(Adjacent A){

      this.adjacents.add(A);

    /* Lorsque A devient adjacent de B , B devient uaztomatiquement adjacent à A */
    // Adjacent A2=  new Adjacent(this,A.getCost());
    //A.getNode().addAdjacent();
  }
    public LinkedList<Station> afficherStation(){

        Iterator i = adjacents.iterator();

        while(i.hasNext()){
            Adjacent A = (Adjacent)i.next();
           
            System.out.println(this.getNom()+"-"+A.getCost()+"-"+A.getNode().getNom());
        }


        return null;
     }




}