/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projet_gl;

import java.util.HashMap;

public class Ligne {
    private int numero;
    private String nom;
    private double tempDeParcour;
    private boolean incident;
    private HashMap<String,Station> listStations;

     public Ligne(int numero, String nom, double tempDeParcour, boolean incident) {
        this.numero = numero;
        this.nom = nom;
        this.tempDeParcour = tempDeParcour;
        this.incident = incident;
    }

    public void setIncident(boolean incident) {
        this.incident = incident;
    }

    public void setListStations(HashMap<String, Station> listStations) {
        if(this.listStations == null )
            this.listStations = new HashMap<String, Station>();
        this.listStations = listStations;
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

    public HashMap<String, Station> getListStations() {
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


}
