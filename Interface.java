

/**
 * Décrivez votre classe Interface ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 * 
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Interface extends JFrame {

    private int HAUTEUR = 6;
    private int LARGEUR = 7;

    private Configuration jeu;
    private Joueur j1;
    private Joueur j2;
    private int pionsRestant;

    JPanel topPanel; // le panel en haut
    JPanel botPanel; // le panel en bas

    // variable utile pour changer l'interface
    private JLabel[] grille; // la grille de l'interface
    private JButton[] bouttonColonne; // le bouton de chaque colonne
    ImageIcon icone; // l'icone principale du jeu
    ImageIcon iconeJ1;
    ImageIcon iconeJ2;

    public Interface() {
        super("PUISSANCE 4");

        JSplitPane splitPane; // permet de diviser le panel principal en deux

        // creation du nombre de bouttons et de la grille
        bouttonColonne = new JButton[LARGEUR];
        grille = new JLabel[HAUTEUR * LARGEUR];

        // creation des icones
        icone = createImageIcon("/img/icone.png", "icone principale");
        iconeJ1 = createImageIcon("/img/iconeJetonRouge.png", "icone du joueur 1");
        iconeJ2 = createImageIcon("/img/iconeJetonJaune.png", "icone du joueur 2");

        // creation des Panels haut et bas
        topPanel = new JPanel(new GridLayout(HAUTEUR, LARGEUR, 5, 5)); // panel qui arrange la grille en
                                                                       // HAUTEUR_colonnes de LARGEUR_lignes
        botPanel = new JPanel(new GridLayout(1, 0, 10, 10)); // panel qui arrange les bouttons de la gauche vers la
                                                             // droite

        // division verticale du panel principale en 2
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, botPanel);
        splitPane.setResizeWeight(1.0); // permet de ne pas changer la taille du botPanel

        topPanel.setMinimumSize(new Dimension(1280, 540));
        botPanel.setMinimumSize(new Dimension(1280, 100)); // environ 1/4 de l'ecran

        // creation et mis en place de la grille
        for (int i = 0; i < grille.length; i++) {
            grille[i] = new JLabel(icone); // on ne peut pas modifier la barre de division quand c'est une icone
            topPanel.add(grille[i]);
        }

        

        // misEnPlaceBoutton(botPanel);
        appuiBoutton();
        menuInterface();
        setContentPane(splitPane); // assigner splitPane comme panel principal
        setSize(1100, 720);
        setVisible(true);
        setResizable(false);

        icone = new ImageIcon(icone.getImage().getScaledInstance(grille[0].getWidth(), grille[0].getHeight(), Image.SCALE_SMOOTH));
        iconeJ1 = new ImageIcon(iconeJ1.getImage().getScaledInstance(grille[0].getWidth(), grille[0].getHeight(), Image.SCALE_SMOOTH));
        iconeJ2 = new ImageIcon(iconeJ2.getImage().getScaledInstance(grille[0].getWidth(), grille[0].getHeight(), Image.SCALE_SMOOTH));

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        addWindowListener(l);

        newGame();

    }

    public void newGame() {
        jeu = new Configuration();
        pionsRestant = HAUTEUR * LARGEUR;
        int version = version();
        if (version == 0) {
            this.j1 = new Joueur("1");
            this.j2 = new Joueur("2");
        } else {
            int niveau = choixNiveau();
            this.j1 = new Joueur("1");
            if (niveau == 0)
                j2 = new Joueur();
            else
                j2 = new Joueur(niveau);
        }

        // creation et mis en place de la grille
        for (int i = 0; i < grille.length; i++) {
            grille[i].setIcon(icone); // on ne peut pas modifier la barre de division quand c'est une icone
        }

        j1.setImage(iconeJ1);
        j2.setImage(iconeJ2);
        Joueur.choixTour(this.j1, this.j2);
        if (!jeu.estGagnant() && j2.getTour() && j2.getIa()) {
            tourIA();
        }
        // on active les bouttons
        for (int i = 0; i < LARGEUR; i++) {
            bouttonColonne[i].setEnabled(true);
        }

    }

    private int choixNiveau() {
        // Custom button text
        Object[] options = { "Basic", "Moyen", "Difficile", "Challenge" };
        String s = (String) JOptionPane.showInputDialog(this, "Choisissez le niveau de difficult\u00e9",
                "Choix de la partie", JOptionPane.PLAIN_MESSAGE, null, options, "Basic");
        if(s!=null){
            switch (s) {
                case "Basic":
                    return 0;
                case "Moyen":
                    return 2;
                case "Difficile":
                    return 3;
                case "Challenge":
                    return 4;
                default:
                    break;
            }
        }
        return 0;
    }

    private int version() {
        // Custom button text
        Object[] options = { "Humain contre Humain", "Humain contre Ordinateur" };
        int n = JOptionPane.showOptionDialog(this, "A quelle version voulez-vous jouer?", "Choix de la partie",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return n;
    }

    public void rejouer(){
        jeu = new Configuration();
        pionsRestant = HAUTEUR * LARGEUR;
        // creation et mis en place de la grille
        for (int i = 0; i < grille.length; i++) {
            grille[i].setIcon(icone); // on ne peut pas modifier la barre de division quand c'est une icone
        }
        Joueur.choixTour(this.j1, this.j2);
        //si l'ia commence en premier
        if (!jeu.estGagnant() && j2.getTour() && j2.getIa()) {
            tourIA();
        }
        // on active les bouttons
        for (int i = 0; i < LARGEUR; i++) {
            bouttonColonne[i].setEnabled(true);
        }
    }

    public void menuInterface() {
        JMenuBar barreMenu;

        JMenu menu;
        JMenu aPropos;

        JMenuItem save;
        JMenuItem load;
        JMenuItem newGame;
        JMenuItem regle;
        JMenuItem exit;
        JMenuItem credit;
        JMenuItem rejouer;

        ActionListener m = appuiMenu();

        save = new JMenuItem("Sauvegarder");
        load = new JMenuItem("Charger");
        newGame = new JMenuItem("Nouvelle Partie");
        regle = new JMenuItem("Comment jouer?");
        credit = new JMenuItem("Credits");
        exit = new JMenuItem("Quitter");
        rejouer = new JMenuItem("Rejouer Partie");

        newGame.addActionListener(m);
        save.addActionListener(m);
        load.addActionListener(m);
        regle.addActionListener(m);
        exit.addActionListener(m);
        credit.addActionListener(m);
        rejouer.addActionListener(m);

        menu = new JMenu("Menu");
        menu.add(rejouer);
        menu.add(newGame);
        menu.add(save);
        menu.add(load);
        menu.add(exit);
        

        aPropos = new JMenu("A propos");
        aPropos.add(regle);
        aPropos.add(credit);

        barreMenu = new JMenuBar();
        barreMenu.add(menu);
        barreMenu.add(aPropos);

        setJMenuBar(barreMenu);

    }

    private ActionListener appuiMenu() {
        ActionListener m = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = e.getActionCommand();
                if (message.equals("Sauvegarder")) {
                    try {
                        Fichier.sauvegarder(jeu.getGrille(), (j1.getTour() ? 1 : 2));
                    } catch (IOException exception) {
                        System.out.println("Impossible de sauvegarder");
                        exception.printStackTrace();
                        System.exit(1);
                    }
                } else if (message.equals("Charger")) {
                    try {
                        if (Fichier.load(jeu) == 1) {
                            j1.setTour(true);
                            j2.setTour(false);
                        } else {
                            j1.setTour(false);
                            j2.setTour(true);
                        }
                        Symbol[][] s = jeu.getGrille();
                        initialiserGrille(s);

                        if (!jeu.estGagnant() && j2.getTour() && j2.getIa()) {
                            EventQueue.invokeLater(() -> {
                                tourIA();
                            });
                        }
                    } catch (FileNotFoundException exception) {
                        System.out.println("Impossible de charger la sauvegarde, Fichier introuvable");
                        exception.printStackTrace();
                    }
                } else if (message.equals("Nouvelle Partie")) { // nouvelle partie
                    newGame();
                } else if (message.equals("Comment jouer?")) {
                    regle();
                } else if (message.equals("Credits")) {
                    credit();
                }
                else if (message.equals("Rejouer Partie")){
                    rejouer();

                } else {
                    System.exit(0);
                }
            }
        };
        return m;
    }

    private ActionListener appuiBoutton() {
        ActionListener p = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int boutton = Integer.parseInt(e.getActionCommand()); // colonne
                misAJourGrille(boutton);
                if (!jeu.estGagnant() && j2.getTour() && j2.getIa()) {
                    EventQueue.invokeLater(() -> {
                        // grille[0].setIcon(new ImageIcon("iconeJetonJaune.png"));
                        tourIA();

                    });
                }
            }

        };

        // creation et mis en place des bouttons
        for (int i = 0; i < LARGEUR; i++) { // <LARGEUR
            bouttonColonne[i] = new JButton("" + (i + 1) + "");
            botPanel.add(bouttonColonne[i]);
        }

        // mis en place des bouttons dans l'actionListener
        for (int i = 0; i < LARGEUR; i++) {
            bouttonColonne[i].addActionListener(p);
        }

        return p;
    }

    public void initialiserGrille(Symbol[][] s) {
        int compteur = 0;
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[0].length; j++) {
                if (s[i][j].equals(Symbol.VIDE)) {
                    grille[compteur].setIcon(icone);
                } else if (s[i][j].equals(Symbol.O)) {
                    grille[compteur].setIcon(j2.getIcon());
                } else {
                    grille[compteur].setIcon(j1.getIcon());
                }
                compteur++;
            }
        }
    }

    public int misAJourGrille(int colonne) {
        Joueur joueur = j1.getTour() ? j1 : j2; // pour savoir quel joueur a appuyé sur le boutton
        if (colonne > 0 && colonne <= LARGEUR) {
            int ligne = jeu.verifierLigne(colonne - 1);

            if (ligne > -1) {
                grille[((ligne * LARGEUR) + colonne) - 1].setIcon(joueur.getIcon()); // change l'icone de la case
                                                                                     // correspondant
                jeu.getGrille()[ligne][colonne - 1] = joueur.getSymbol(); // change la valeur de la case correspondant
                pionsRestant--;
                if (jeu.estGagnant() || !(pionsRestant > 0)) {
                    // on desactive les bouttons
                    for (int i = 0; i < LARGEUR; i++) {
                        bouttonColonne[i].setEnabled(false);
                    }
                    if (!(pionsRestant > 0)) {
                        JOptionPane.showMessageDialog(this, "La partie est une \u00e9galit\u00e9 !!", "Fin de la partie",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Le joueur " + (joueur.getNom()) + " gagne la partie!",
                                "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                jeu.placerCoup(j1, j2, ligne, colonne - 1);
            }
            return ligne;
        }
        return -1;
    }

    private void tourIA() {
        long debut = System.nanoTime();
        while (System.nanoTime() < debut + 500 * 1000 * 1000) {
        } // wait
        int ligneChoisie;
        do {
            ligneChoisie = misAJourGrille(IAjouer(j2));
        } while (ligneChoisie < 0);
    }

    public int IAjouer(Joueur joueur) {
        int colonne = -1;
        if (joueur.getIANaif()) {
            colonne = IA.choixColonne(jeu);
        } else {
            Coup c = joueur.minimax(jeu);
            colonne = c.getColonne();
        }
        return colonne + 1;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */ // pour securiser la lecture des icones
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void regle() {
        String s = "Le but du jeu est d'aligner une suite de 4 pions de m\u00eame couleur sur une grille"
                + " comptant 6 rang\u00e9es et 7 colonnes.\n Chaque joueur dispose de 21 pions d'une couleur.\n"
                + "Tour \u00e0 tour les deux joueurs placent un pion dans la colonne de leur choix, "
                + "le pion coulisse alors jusqu'\u00e0 la position la plus basse possible dans la "
                + "colonne \u00e0 la suite de quoi c'est \u00e0 l'adversaire de jouer.\n "
                + "Le vainqueur est le joueur qui r\u00e9alise le premier un alignement (horizontal, vertical ou diagonal)"
                + " cons\u00e9cutif d'au moins quatre pions de sa couleur.\n "
                + "Si, alors que toutes les cases de la grille de jeu sont remplies, "
                + "aucun des deux joueurs n'a r\u00e9alis\u00e9 un tel alignement, la partie est d\u00e9clar\u00e9e nulle.";

        JOptionPane.showMessageDialog(this, s, "Regle du jeu", JOptionPane.INFORMATION_MESSAGE);
    }

    public void credit() {
        String s = "Auteur : RANAIVO Harimbola Ranto\n" + "Puissance 4 \u00a92021";
        JOptionPane.showMessageDialog(this, s, "Credits", JOptionPane.INFORMATION_MESSAGE);
    }

}
