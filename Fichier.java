
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Fichier {

    /**
     * Save the state of the board in a file
     * 
     * @param s the current state of the board
     * @param joueur the player who would play 
     * @exception IOException
     */
    // pour sauvegarder
    public static void sauvegarder(Symbol[][] s, int joueur) throws IOException {
        File f = new File("Sauvegarde.txt");
        FileWriter fw = new FileWriter(f);
        PrintWriter pw = new PrintWriter(fw);

        pw.print(s.length + " " + s[0].length + " " + joueur + " ");
        for (int j = s.length - 1; j >= 0; j--) {
            for (int i = 0; i < s[0].length; i++) {
                if (s[j][i].equals(Symbol.X))
                    pw.print("x");
                else if (s[j][i].equals(Symbol.O))
                    pw.print("o");
                else
                    pw.print("-");
            }
        }
        pw.close();
    }

    /**
     * Load a game
     * @param jeu the board
     * @return the player who should play next
     */
    public static int load(Configuration jeu) throws FileNotFoundException {
        File sauvegarde = new File("Sauvegarde.txt");
        Scanner scan = new Scanner(sauvegarde);
        String save = scan.nextLine();

        int hauteur = Integer.parseInt(String.valueOf(save.charAt(0)));
        int largeur = Integer.parseInt(String.valueOf(save.charAt(2)));
        int tour = Integer.parseInt(String.valueOf(save.charAt(4)));
        jeu.initialiser(hauteur, largeur);
        Symbol[][] s = jeu.getGrille();

        int indexColonne = 0;
        int indexLigne = s.length - 1;
        for (int i = 6; i < save.length(); i++) {
            switch (String.valueOf(save.charAt(i))) {
                case "x":
                    s[indexLigne][indexColonne] = Symbol.X;
                    break;
                case "o":
                    s[indexLigne][indexColonne] = Symbol.O;
                    break;
                default:
                    s[indexLigne][indexColonne] = Symbol.VIDE;
                    break;
            }
            indexColonne = (indexColonne + 1) % s[0].length;
            if (indexColonne == 0)
                indexLigne--;
        }

        scan.close();
        return tour;
    }
}
