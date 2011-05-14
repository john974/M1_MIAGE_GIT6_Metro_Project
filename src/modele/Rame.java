

package modele;

public class Rame {
    private int numero;
    private String nom;
    private int numeroDeLigne;

    public Rame(int numero, String nom) {
        this.numero = numero;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int getNumero() {
        return numero;
    }

    public int getNumeroDeLigne() {
        return numeroDeLigne;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNumeroDeLigne(int numeroDeLigne) {
        this.numeroDeLigne = numeroDeLigne;
    }


}
