/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projet_gl;

import java.util.HashMap;
import java.util.LinkedList;

public class Ligne {
    private int numero;
    private String nom;
    private double tempDeParcour;
    private boolean incident = false;
    private LinkedList<Station> listStations = new LinkedList<Station>();

     public Ligne(int numero, String nom, double tempDeParcour, boolean incident) {
        this.numero = numero;
        this.nom = nom;
        this.tempDeParcour = tempDeParcour;
        this.incident = incident;
    }

    public void setIncident(boolean incident) {
        this.incident = incident;
    }

    public void setListStations(LinkedList<Station> listStations) {
        if(this.listStations == null )
            this.listStations = new LinkedList<Station>();
        this.listStations = listStations;
    }

    public Ligne(int numero, String nom) {
        this.numero = numero;
        this.nom = nom;
    }
    

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setTempDeParcour(double tempDeParcour) {
        this.tempDeParcour = tempDeParcour;
    }

    public boolean isIncident() {
        return incident;
    }

    public LinkedList<Station> getListStations() {
        return listStations;
    }

    public String getNom() {
        return nom;
    }

    public int getNumero() {
        return numero;
    }

    public double getTempDeParcour() {
        return tempDeParcour;
    }


    public   boolean isLine(LinkedList<Station> ls)
    {
        if(this.listStations.containsAll(ls))
            return true;
        return false;
    }

    public void getLine(LinkedList<Station> ls)
    {
        
    }
}
