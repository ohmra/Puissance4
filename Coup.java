public class Coup {
    private int ligne;
    private int colonne;
    private int puissance;

    public Coup(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.puissance = -10;
    }

    public Coup(int ligne, int colonne, int puissance) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.puissance = puissance;
    }

    public Coup(int puissance) {
        this.ligne = -10;
        this.colonne = -10;
        this.puissance = puissance;
    }

    public int getColonne() { return this.colonne; }

    public int getLigne() { return this.ligne; }
    public int getPuissance() { return this.puissance; }

    public void setLigne(int l) { this.ligne = l; }
    public void setColonne(int c) { this.colonne = c;  }
    public void setPuissance(int p) { this.puissance = p; }

    public Coup copie() {
        return new Coup(this.ligne, this.colonne, this.puissance);
    }
}