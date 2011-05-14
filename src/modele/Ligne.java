/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

import java.util.HashMap;
import java.util.LinkedList;
import outils.Saisie;

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

    public void afficher()
    {
        StringBuffer sb = new StringBuffer(this.nom);
        Saisie.p(sb);
    }
    public void afficherExtreme()
    {
        if(this.listStations.size() > 0)
            this.getListStations().getFirst().afficher();
        if(this.listStations.size() > 0)
            this.getListStations().getLast().afficher();
    }


    public void afficherline(Ligne l){
        System.out.println("Taille de la ligne "+ l.listStations.size());
        if (l.listStations.size() > 0){
        for(Station s :l.listStations){

            System.out.println("-\n"+s.getNumero());
        }
        }
    }


    public String afficherTrajet()
    {
        String tmp = this.getNom()+": ";
        if(this.getListStations().size() > 0)
        {
            for(Station s : this.getListStations())
            {
            tmp += s.getNom()+"-->";
            }
           
            //Saisie.p(tmp);
        }
        return tmp;
    }

    @Override
    public String toString() {
        StringBuffer res = new StringBuffer("\nLigne : ");
        res.append(this.nom);
        res.append("\n\tStations : ");
        for(Station s : this.listStations)
        {
             res.append("[");
             res.append(s.getNom());
             res.append("]");
        }
        return res.toString();
    }
    
}
