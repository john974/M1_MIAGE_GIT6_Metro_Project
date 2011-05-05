/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetgenielogiciel;

/**
 *
 * @author HAMADACITY
 */
public class Trajet {
    private Ligne ligne1;
    private Ligne ligne2;
    private Station s;

    public Trajet(Ligne ligne1, Ligne ligne2, Station s) {
        this.ligne1 = ligne1;
        this.ligne2 = ligne2;
        this.s = s;
    }

    public Trajet() {
    }

    public Ligne getLigne1() {
        return ligne1;
    }

    public Ligne getLigne2() {
        return ligne2;
    }

    public Station getS() {
        return s;
    }

    public void setLigne1(Ligne ligne1) {
        this.ligne1 = ligne1;
    }

    public void setLigne2(Ligne ligne2) {
        this.ligne2 = ligne2;
    }

    public void setS(Station s) {
        this.s = s;
    }
    

}
