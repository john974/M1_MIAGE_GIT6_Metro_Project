/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projet_gl;

public final class Adjacent implements Comparable<Adjacent>{
    private Station node;
    private double cost;

    public Adjacent() {
    }


    public Adjacent(Station nodeTo, double cost) {
        this.node = nodeTo;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public Station getNode() {
        return node;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setNode(Station node) {
        this.node = node;
    }

     public void afficher()
    {
       // this.getNode().afficher();
        System.out.println("Nom: "+this.getNode().getNom()+"\nCout: "+this.getCost());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Adjacent other = (Adjacent) obj;
        if (this.node != other.node && (this.node == null || !this.node.equals(other.node))) {
            return false;
        }
        /*if (Double.doubleToLongBits(this.cost) != Double.doubleToLongBits(other.cost)) {
            return false;
        }*/
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.node != null ? this.node.hashCode() : 0);
        //hash = 53 * hash + (int) (Double.doubleToLongBits(this.cost) ^ (Double.doubleToLongBits(this.cost) >>> 32));
        return hash;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int compareTo(Adjacent o) {
        //throw new UnsupportedOperationException("Not supported yet.");
        /* on compare en fonction du trajet qui lie deux noeuds
         et le temps d'arrets au niveau du noeud.***/
        return (this.getCost() + this.getNode().getTempArret()) < (o.getCost()+o.getNode().getTempArret()) ? -1 :
            (this.getCost()+this.getNode().getTempArret()) == (o.getCost()+o.getNode().getTempArret()) ? 0 : 1;
    }
    
}
