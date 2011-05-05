/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projet_gl;

import java.util.LinkedList;
import java.util.List;
import outils.Saisie;

/**
 *
 * @author kandji
 */
public class Chemin implements Comparable<Chemin>{
    private Station station;
    private List<Station> list = new LinkedList<Station>();
    private Ligne line;
     private int count;
    public Chemin(Station station, Ligne line, int count) {
        this.station = station;
        this.line = line;
        this.count = count;
    }

    public Ligne getLine() {
        return line;
    }

    public void setLine(Ligne line) {
        this.line = line;
    }

    public Chemin(Station station, int count) {
        this.station = station;
        this.count = count;
    }

    public int compareTo(Chemin o) {
        //throw new UnsupportedOperationException("Not supported yet.");
         return this.getCount() < o.getCount() ? 1 :
            this.getCount() == o.getCount() ? 0 : -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Chemin other = (Chemin) obj;
        if (this.station != other.station && (this.station == null || !this.station.equals(other.station))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.station != null ? this.station.hashCode() : 0);
        return hash;
    }
    

    public Chemin() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Station> getList() {
        return list;
    }

    public void setList(List<Station> list) {
        this.list = list;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
    
    public void afficher()
    {
        Saisie.p(this.station.getNom());
        Saisie.p(this.getCount());
        Saisie.p(this.getLine().getNom());
        Saisie.p("");
    }
}
