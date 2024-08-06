
import java.util.ArrayList;

public class Configuration {
    private int LARGEUR = 7;
    private int HAUTEUR = 6;
    private int nbDePions;
    private Symbol[][] grille;
    private ArrayList<Configuration> fils;

    public Configuration() {
        nbDePions = HAUTEUR * LARGEUR;
        this.grille = new Symbol[HAUTEUR][LARGEUR];
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                this.grille[i][j] = Symbol.VIDE;
            }
        }
        this.fils = new ArrayList<>();
    }

    public Configuration(int hauteur, int largeur) {
        LARGEUR = largeur;
        HAUTEUR = hauteur;
        nbDePions = HAUTEUR * LARGEUR;
        this.grille = new Symbol[HAUTEUR][LARGEUR];
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                this.grille[i][j] = Symbol.VIDE;
            }
        }
        this.fils = new ArrayList<>();
    }

    public void initialiser(int hauteur, int largeur) {
        HAUTEUR = hauteur;
        LARGEUR = largeur;
        grille = new Symbol[HAUTEUR][LARGEUR];
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                this.grille[i][j] = Symbol.VIDE;
            }
        }
    }

    public Configuration copie() {
        Configuration Configuration = new Configuration();
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                Configuration.grille[i][j] = this.grille[i][j];
            }
        }
        Configuration.nbDePions = this.nbDePions;
        Configuration.fils = new ArrayList<>();
        return Configuration;
    }

    public int getNbDePions() { return this.nbDePions;   }
    public ArrayList<Configuration> getFils() { return fils; };
    public Symbol[][] getGrille() { return this.grille; }
    public int getHAUTEUR() { return this.HAUTEUR; }
    public int getLARGEUR() { return this.LARGEUR; }
    
    public void placerJeton(Symbol s, int i, int j) {
        this.grille[i][j] = s;
    }

    public void placerCoup(Joueur joueur1, Joueur joueur2, int i, int j) {
        if (joueur1.getTour()) {
            this.grille[i][j] = joueur1.getSymbol();
            joueur1.setTour(false);
            joueur2.setTour(true);
        } else {
            this.grille[i][j] = joueur2.getSymbol();
            joueur2.setTour(false);
            joueur1.setTour(true);
        }
        nbDePions--;
    }

    // pour les besoins du test
    public void afficherGrille() {
        for (int i = 0; i < HAUTEUR; i++) {
            for (int j = 0; j < LARGEUR; j++) {
                if (this.grille[i][j] == Symbol.VIDE || this.grille[i][j] == null)
                    System.out.print(" |");
                else
                    System.out.print(this.grille[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < LARGEUR; i++)
            System.out.print((i + 1) + "|");
    }

    public void afficherJeu(Joueur j1, Joueur j2) {
        afficherGrille();
        System.out.println();
        System.out.print(j1.getNom() + ": " + j1.getSymbol() + " VS " + j2.getNom() + ": " + j2.getSymbol());
        System.out.println();
        System.out.println("Tapez 'save' pour sauvegarder");
        System.out.println("Tapez 'load' pour charger la derniere partie sauvegardée");
        System.out.println("Tapez 'exit' pour quitter");
    }

    public int verifierLigne(int colonne) {
        if (colonne < 0)
            return -1;
        int ligne = HAUTEUR - 1;
        boolean emplacementVIDE = false;
        while (!emplacementVIDE && ligne >= 0) {
            if (this.grille[ligne][colonne] == Symbol.VIDE) {
                emplacementVIDE = true;
            } else {
                ligne--;
            }
        }
        return ligne;
    }

    public boolean estGagnant() {
        for (int i = HAUTEUR - 1; i >= 0; i--) {
            for (int j = 0; j < LARGEUR - 3; j++) {
                if (this.grille[i][j] != Symbol.VIDE
                        && (this.grille[i][j] == this.grille[i][j + 1] && this.grille[i][j + 1] == this.grille[i][j + 2]
                                && this.grille[i][j + 2] == this.grille[i][j + 3])) {
                    return true;
                }
            }
        }
        for (int j = 0; j < LARGEUR; j++) {
            for (int i = HAUTEUR - 1; i >= 3; i--) {
                if (this.grille[i][j] != Symbol.VIDE
                        && (this.grille[i][j] == this.grille[i - 1][j] && this.grille[i - 1][j] == this.grille[i - 2][j]
                                && this.grille[i - 2][j] == this.grille[i - 3][j])) {
                    return true;
                }
            }
        }
        for (int i = HAUTEUR - 1; i >= 3; i--) {
            for (int j = 0; j < LARGEUR - 3; j++) {
                if (this.grille[i][j] != Symbol.VIDE && (this.grille[i][j] == this.grille[i - 1][j + 1]
                        && this.grille[i - 1][j + 1] == this.grille[i - 2][j + 2]
                        && this.grille[i - 2][j + 2] == this.grille[i - 3][j + 3])) {
                    return true;
                }
            }
        }
        for (int i = HAUTEUR - 1; i >= 3; i--) {
            for (int j = LARGEUR - 1; j >= 3; j--) {
                if (this.grille[i][j] != Symbol.VIDE && (this.grille[i][j] == this.grille[i - 1][j - 1]
                        && this.grille[i - 1][j - 1] == this.grille[i - 2][j - 2]
                        && this.grille[i - 2][j - 2] == this.grille[i - 3][j - 3])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean estEgalite() {
        return this.nbDePions == 0 ? true : false;
    }

    public int evaluation() {
        return (evaluation_j2() - evaluation_j1());
    }

    public int evaluation_j1() {
        int[][] poids = new int[HAUTEUR][LARGEUR];
        for (int i = 0; i < HAUTEUR; i++)
            for (int j = 0; j < LARGEUR; j++) {
                if (this.grille[i][j].equals(Symbol.X))
                    poids[i][j] = -10;
                else if (this.grille[i][j].equals(Symbol.O))
                    poids[i][j] = 1;
                else
                    poids[i][j] = 0;
            }

        int score = 0;
        // verticale offensif
        for (int i = 0; i < LARGEUR; i++) {
            for (int j = HAUTEUR - 1; j >= 3; j--) {
                int alignement = poids[j][i] + poids[j - 1][i] + poids[j - 2][i] + poids[j - 3][i];
                if (alignement == -40)
                    score = score + 1000;
                if (alignement == -30)
                    score = score + 100;
                if (alignement == -20)
                    score = score + 10;
                /*if (alignement == -10)
                    score = score + 1;*/
            }
        }

        // horizontale offensif avec un poids de 4
        for (int j = HAUTEUR - 1; j >= 3; j--) {
            for (int i = 0; i <= LARGEUR - 4; i++) {
                int alignement = poids[j][i] + poids[j][i + 1] + poids[j][i + 2] + poids[j][i + 3];
                if (alignement == -40)
                    score = score + 1000;
                if (alignement == -30)
                    score = score + 100;
                if (alignement == -20)
                    score = score + 10;
                /*if (alignement == -10)
                    score = score + 1;*/
            }
        }

        // diagonale offensif du bas vers haut à droite poids 4
        for (int i = HAUTEUR - 1; i >= 3; i--) {
            for (int j = 0; j <= LARGEUR - 4; j++) {
                int alignement = poids[i][j] + poids[i - 1][j + 1] + poids[i - 2][j + 2] + poids[i - 3][j + 3];
                if (alignement == -40)
                    score = score + 1000;
                if (alignement == -30)
                    score = score + 100;
                if (alignement == -20)
                    score = score + 10;
                /*if (alignement == -10)
                    score = score + 1;*/
            }
        }

        // diagonale offensif du bas vers haut à gauche poids 4
        for (int i = HAUTEUR - 1; i >= 3; i--) {
            for (int j = LARGEUR - 1; j >= 3; j--) {
                int alignement = poids[i][j] + poids[i - 1][j - 1] + poids[i - 2][j - 2] + poids[i - 3][j - 3];
                if (alignement == -40)
                    score = score + 1000;
                if (alignement == -30)
                    score = score + 100;
                if (alignement == -20)
                    score = score + 10;
                /*if (alignement == -10)
                    score = score + 1;*/
            }
        }

        return score;
    }

    public int evaluation_j2() {
        int[][] poids = new int[HAUTEUR][LARGEUR];
        for (int i = 0; i < HAUTEUR; i++)
            for (int j = 0; j < LARGEUR; j++) {
                if (this.grille[i][j].equals(Symbol.X))
                    poids[i][j] = -10;
                else if (this.grille[i][j].equals(Symbol.O))
                    poids[i][j] = 1;
                else
                    poids[i][j] = 0;
            }

        int score = 0;
        // verticale offensif
        for (int i = 0; i < LARGEUR; i++) {
            for (int j = HAUTEUR - 1; j >= 3; j--) {
                int alignement = poids[j][i] + poids[j - 1][i] + poids[j - 2][i] + poids[j - 3][i];
                if (alignement == 4)
                    score = score + 1000;
                if (alignement == 3)
                    score = score + 100;
                if (alignement == 2)
                    score = score + 10;
                /*if (alignement == 1)
                    score = score + 1;*/
            }
        }

        // horizontale offensif avec un poids de 4
        for (int j = HAUTEUR - 1; j >= 3; j--) {
            for (int i = 0; i <= LARGEUR - 4; i++) {
                int alignement = poids[j][i] + poids[j][i + 1] + poids[j][i + 2] + poids[j][i + 3];
                if (alignement == 4)
                    score = score + 1000;
                if (alignement == 3)
                    score = score + 100;
                if (alignement == 2)
                    score = score + 10;
                /*if (alignement == 1)
                    score = score + 1;*/
            }
        }

        // diagonale offensif du bas vers haut à droite poids 4
        for (int i = HAUTEUR - 1; i >= 3; i--) {
            for (int j = 0; j <= LARGEUR - 4; j++) {
                int alignement = poids[i][j] + poids[i - 1][j + 1] + poids[i - 2][j + 2] + poids[i - 3][j + 3];
                if (alignement == 4)
                    score = score + 1000;
                if (alignement == 3)
                    score = score + 100;
                if (alignement == 2)
                    score = score + 10;
                /*if (alignement == 1)
                    score = score + 1;*/
            }
        }

        // diagonale offensif du bas vers haut à gauche poids 4
        for (int i = HAUTEUR - 1; i >= 3; i--) {
            for (int j = LARGEUR - 1; j >= 3; j--) {
                int alignement = poids[i][j] + poids[i - 1][j - 1] + poids[i - 2][j - 2] + poids[i - 3][j - 3];
                if (alignement == 4)
                    score = score + 1000;
                if (alignement == 3)
                    score = score + 100;
                if (alignement == 2)
                    score = score + 10;
                /*if (alignement == 1)
                    score = score + 1;*/
            }
        }

        return score;
    }
}