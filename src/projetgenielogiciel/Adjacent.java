/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetgenielogiciel;

/**
 *
 * @author HAMDACITY
 */
public class Adjacent {
    private Station node;
    private int cost;

    public Adjacent(Station nodeTo, int cost) {
        this.node = nodeTo;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public Station getNode() {
        return node;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setNode(Station node) {
        this.node = node;
    }



}
