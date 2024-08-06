public class IA {

    public static int choixColonne(Configuration jeu) {
        Symbol[][] tab = jeu.getGrille();
        int[][] poids = new int[tab.length][tab[0].length];
        for (int i = 0; i < tab.length; i++)
            for (int j = 0; j < tab[0].length; j++) {
                if (tab[i][j].equals(Symbol.X))
                    poids[i][j] = 1;
                else if (tab[i][j].equals(Symbol.O))
                    poids[i][j] = -1;
                else
                    poids[i][j] = 0;
            }
        // ATTAQUE POIDS 3
        // verticale offensif avec un poids de 3
        for (int i = tab.length - 1; i >= 3; i--)
            for (int j = 0; j < tab[0].length; j++)
                if (poids[i][j] + poids[i - 1][j] + poids[i - 2][j] + poids[i - 3][j] == -3)
                    return j;

        // horizontale offensif avec un poids de 3
        for (int i = tab.length - 1; i >= 0; i--) {
            for (int j = 0; j <= tab[0].length - 4; j++) {
                if (poids[i][j] + poids[i][j + 1] + poids[i][j + 2] + poids[i][j + 3] == -3) {
                    for (int k = j; k < j + 4; k++) {
                        if (i < tab.length - 1) {
                            if (poids[i][k] == 0 && poids[i + 1][k] != 0)
                                return k;
                        } else if (poids[i][k] == 0)
                            return k;
                    }
                }
            }
        }

        // diagonale offensif du bas vers haut à droite poids 3
        for (int i = tab.length - 1; i >= 3; i--) {
            for (int j = 0; j <= tab[0].length - 4; j++) {
                if (poids[i][j] + poids[i - 1][j + 1] + poids[i - 2][j + 2] + poids[i - 3][j + 3] == -3) {
                    if (i == tab.length - 1) { // cas particulier
                        if (poids[i][j] == 0)
                            return j;
                        if (poids[i - 1][j + 1] == 0 && poids[i - 1 + 1][j + 1] != 0)
                            return j + 1;
                        if (poids[i - 2][j + 2] == 0 && poids[i - 2 + 1][j + 2] != 0)
                            return j + 2;
                        if (poids[i - 3][j + 3] == 0 && poids[i - 3 + 1][j + 3] != 0)
                            return j + 3;
                    } else {
                        for (int k = 0; k < 4; k++) {
                            if (poids[i - k][j + k] == 0 && poids[i - k + 1][j + k] != 0)
                                return j + k;
                        }
                    }
                }
            }
        }

        // diagonale offensif du bas vers haut à gauche poids 3
        for (int i = tab.length - 1; i >= 3; i--) {
            for (int j = tab[0].length - 1; j >= 3; j--) {
                if (poids[i][j] + poids[i - 1][j - 1] + poids[i - 2][j - 2] + poids[i - 3][j - 3] == -3) {
                    if (i == tab.length - 1) { // cas particulier
                        if (poids[i][j] == 0)
                            return j;
                        if (poids[i - 1][j - 1] == 0 && poids[i - 1 + 1][j - 1] != 0)
                            return j - 1;
                        if (poids[i - 2][j - 2] == 0 && poids[i - 2 + 1][j - 2] != 0)
                            return j - 2;
                        if (poids[i - 3][j - 3] == 0 && poids[i - 3 + 1][j - 3] != 0)
                            return j - 3;
                    } else {
                        for (int k = 0; k < 4; k++) {
                            if (poids[i - k][j - k] == 0 && poids[i - k + 1][j - k] != 0)
                                return j - k;
                        }
                    }
                }
            }
        }

        // DEFENSE POIDS 3
        // verticale defensif avec un poids de 3
        for (int i = tab.length - 1; i >= 3; i--) {
            for (int j = 0; j < tab[0].length; j++) {
                if (poids[i][j] + poids[i - 1][j] + poids[i - 2][j] + poids[i - 3][j] == 3)
                    return j;
            }
        }
        // horizontale defensif avec un poids de 3
        for (int i = tab.length - 1; i >= 0; i--) {
            for (int j = 0; j <= tab[0].length - 4; j++) {
                if (poids[i][j] + poids[i][j + 1] + poids[i][j + 2] + poids[i][j + 3] == 3) {
                    for (int k = j; k < j + 4; k++) {
                        if (i < tab.length - 1) {
                            if (poids[i][k] == 0 && poids[i + 1][k] != 0)
                                return k;
                        } else if (poids[i][k] == 0)
                            return k;
                    }
                }
            }
        }

        // diagonale defensif du bas vers haut à droite poids 3
        for (int i = tab.length - 1; i >= 3; i--) {
            for (int j = 0; j <= tab[0].length - 4; j++) {
                if (poids[i][j] + poids[i - 1][j + 1] + poids[i - 2][j + 2] + poids[i - 3][j + 3] == 3) {
                    if (i == tab.length - 1) { // cas particulier
                        if (poids[i][j] == 0)
                            return j;
                        if (poids[i - 1][j + 1] == 0 && poids[i - 1 + 1][j + 1] != 0)
                            return j + 1;
                        if (poids[i - 2][j + 2] == 0 && poids[i - 2 + 1][j + 2] != 0)
                            return j + 2;
                        if (poids[i - 3][j + 3] == 0 && poids[i - 3 + 1][j + 3] != 0)
                            return j + 3;
                    } else {
                        for (int k = 0; k < 4; k++) {
                            if (poids[i - k][j + k] == 0 && poids[i - k + 1][j + k] != 0)
                                return j + k;
                        }
                    }
                }
            }
        }

        // diagonale defensif du bas vers haut à gauche poids 3
        for (int i = tab.length - 1; i >= 3; i--) {
            for (int j = tab[0].length - 1; j >= 3; j--) {
                if (poids[i][j] + poids[i - 1][j - 1] + poids[i - 2][j - 2] + poids[i - 3][j - 3] == 3) {
                    if (i == tab.length - 1) { // cas particulier
                        if (poids[i][j] == 0)
                            return j;
                        if (poids[i - 1][j - 1] == 0 && poids[i - 1 + 1][j - 1] != 0)
                            return j - 1;
                        if (poids[i - 2][j - 2] == 0 && poids[i - 2 + 1][j - 2] != 0)
                            return j - 2;
                        if (poids[i - 3][j - 3] == 0 && poids[i - 3 + 1][j - 3] != 0)
                            return j - 3;
                    } else {
                        for (int k = 0; k < 4; k++) {
                            if (poids[i - k][j - k] == 0 && poids[i - k + 1][j - k] != 0)
                                return j - k;
                        }
                    }
                }
            }
        }
        return (int) (Math.random() * tab[0].length);
    }
}
