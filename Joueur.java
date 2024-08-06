
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.*;

public class Joueur {
    private String nom;
    private boolean leTour;
    private boolean ia;
    private boolean iaNaif;
    private Symbol symbol;
    private int profondeurMaximum;
    private ImageIcon icone;

    // Human Constructor
    public Joueur(String nom) {
        this.nom = nom;
        this.ia = false;
        this.leTour = false;
        this.symbol = Symbol.VIDE;
    }

    // IA Constructor
    public Joueur() {
        this.nom = "Computer";
        this.ia = true;
        this.iaNaif = true;
        this.symbol = Symbol.VIDE;
        this.leTour = false;
    }

    // IA minmax Constructor
    public Joueur(int profondeurMaximum) {
        this.nom = "Computer";
        this.ia = true;
        this.iaNaif = false;
        this.symbol = Symbol.VIDE;
        this.leTour = false;
        this.profondeurMaximum = profondeurMaximum;
    }

    public static void choixTour(Joueur j1, Joueur j2) {
        int rand = (int) (Math.random() * 10);
        if (rand < 5) {
            j1.leTour = false;
            j2.leTour = true;
        } else {
            j1.leTour = true;
            j2.leTour = false;
        }

        j1.symbol = Symbol.X;
        j2.symbol = Symbol.O;
    }

    public void setSymbol(Symbol s) { symbol = s; }
    public Symbol getSymbol() { return this.symbol; }

    public void ActiveIANaif() {
        this.iaNaif = true;
        this.ia = false;
    }

    public void setTour(boolean leTour) { this.leTour = leTour; }
    public void setImage(ImageIcon icon) { this.icone = icon; }

    public boolean getIa() { return this.ia; }
    public boolean getIANaif() { return this.iaNaif; }
    public boolean getTour() { return this.leTour; }
    public String getNom() { return this.nom; }
    public ImageIcon getIcon() { return this.icone; };

    public Coup minimax(Configuration s) {
        return minimax_aux(s, 0, true);
    }

    private Coup minimax_aux(Configuration s, int profondeur, boolean joueurMax) {
        if (s.estGagnant() || profondeur == this.profondeurMaximum || s.getNbDePions() == 0) { // on arrête la recursion
                                                                                               // quand quelqu'un gagne
            return new Coup(s.evaluation()); // ou quand la profondeur est atteint
        } // ou quand le plateau est plein
        if (joueurMax) {
            Coup coup = new Coup(-1000000); // on initialise à une valeur le plus petit possible
            Coup score;
            Configuration ss = s.copie();
            Symbol[][] grille = s.getGrille();
            int ligne = -1;
            for (int i = 0; i < grille[0].length; i++) {
                if (grille[0][i].equals(Symbol.VIDE)) {
                    ligne = ss.verifierLigne(i);
                    Configuration fils = ss.copie();
                    ligne = fils.verifierLigne(i);
                    fils.placerJeton(Symbol.O, ligne, i);
                    ss.getFils().add(fils);
                    score = minimax_aux(fils, profondeur + 1, false);
                    if (coup.getPuissance() < score.getPuissance()) { // on prend le max entre la valeur et le score
                        coup = score.copie();
                        coup.setLigne(ligne);
                        coup.setColonne(i);
                    }
                }
            }

            return coup;
        } else {
            Coup coup = new Coup(1000000); // on initialise à une valeur le plus petit possible
            Coup score;
            Configuration ss = s.copie();
            Symbol[][] grille = s.getGrille();
            int ligne = -1;
            for (int i = 0; i < grille[0].length; i++) {
                if (grille[0][i].equals(Symbol.VIDE)) {
                    ligne = ss.verifierLigne(i);
                    Configuration fils = ss.copie();
                    ligne = fils.verifierLigne(i);
                    fils.placerJeton(Symbol.X, ligne, i);
                    ss.getFils().add(fils);
                    score = minimax_aux(fils, profondeur + 1, true);
                    if (coup.getPuissance() > score.getPuissance()) { // on prend le max entre la valeur et le score
                        coup = score.copie();
                    }
                }
            }

            return coup;
        }
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }



    private int min(int a, int b) {
        return a < b ? a : b;
    }

    public Coup minimax_AlphaBeta(Configuration s) {
        return minimax_aux_AlphaBeta(s, 0, -10000000, 10000000, true);
    }

    private Coup minimax_aux_AlphaBeta(Configuration s, int profondeur, int alpha, int beta, boolean joueurMax) {
        if (s.estGagnant() || profondeur == this.profondeurMaximum || s.getNbDePions() == 0) { // on arrête la recursion
                                                                                               // quand quelqu'un gagne
            return new Coup(s.evaluation()); // ou quand la profondeur est atteint
        } // ou quand le plateau est plein
        if (joueurMax) {
            Coup coup = new Coup(-1000000); // on initialise à une valeur le plus petit possible
            Coup score;
            Configuration ss = s.copie();
            Symbol[][] grille = s.getGrille();
            int ligne = -1;
            for (int i = 0; i < grille[0].length; i++) {
                if (grille[0][i].equals(Symbol.VIDE)) {
                    Configuration fils = ss.copie();
                    ligne = fils.verifierLigne(i);
                    fils.placerJeton(Symbol.O, ligne, i);
                    ss.getFils().add(fils);
                    score = minimax_aux_AlphaBeta(fils, profondeur + 1, alpha, beta, false);
                    if (coup.getPuissance() < score.getPuissance()) { // on prend le max entre la valeur et le score
                        coup = score.copie();
                        coup.setLigne(ligne);
                        coup.setColonne(i);
                    }
                    alpha = max(alpha, score.getPuissance());
                    if (beta <= alpha)
                        break;
                }
            }

            return coup;
        } else {
            Coup coup = new Coup(1000000); // on initialise à une valeur le plus petit possible
            Coup score;
            Configuration ss = s.copie();
            Symbol[][] grille = s.getGrille();
            int ligne = -1;
            for (int i = 0; i < grille[0].length; i++) {
                if (grille[0][i].equals(Symbol.VIDE)) {
                    Configuration fils = ss.copie();
                    ligne = fils.verifierLigne(i);
                    fils.placerJeton(Symbol.X, ligne, i);
                    ss.getFils().add(fils);
                    score = minimax_aux_AlphaBeta(fils, profondeur + 1, alpha, beta, true);
                    if (coup.getPuissance() > score.getPuissance()) { // on prend le max entre la valeur et le score
                        coup = score.copie();
                    }
                    beta = min(beta, score.getPuissance());
                    if (beta <= alpha)
                        break;
                }
            }

            return coup;
        }
    }
}