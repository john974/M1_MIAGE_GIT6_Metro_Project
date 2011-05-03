/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projet_gl;

/**
 *
 * @author kandji
 */
public class Chemin {
    private Station courant;
    private Station predecesseur;

    public Chemin() {
    }

    public Chemin(Station courant) {
        this.courant = courant;
    }

    public Station getPredecesseur() {
        return predecesseur;
    }

    public void setPredecesseur(Station predecesseur) {
        this.predecesseur = predecesseur;
    }
    
}
