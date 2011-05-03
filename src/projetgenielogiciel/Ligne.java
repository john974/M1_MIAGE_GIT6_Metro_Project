/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetgenielogiciel;
import java.util.*;

/**
 *
 * @author HAMDACITY
 */
public class Ligne {
    private int numero;
    private String nom;
    private double tempDeParcourt;
    private boolean incident;
    private LinkedList<Station> listStations = new LinkedList<Station>();

    public Ligne(int numero, String nom, double tempDeParcourt) {
        this.numero = numero;
        this.nom = nom;
        this.tempDeParcourt = tempDeParcourt;
        this.incident = false;
        //  this.listStations = listStations;
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

    public double getTempDeParcourt() {
        return tempDeParcourt;
    }

    public void setIncident(boolean incident) {
        this.incident = incident;
    }

    public void setListStations(LinkedList<Station> listStations) {
        this.listStations = listStations;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setTempDeParcourt(double tempDeParcourt) {
        this.tempDeParcourt = tempDeParcourt;
    }
    
   

}
