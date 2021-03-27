import javax.swing.table.DefaultTableModel;

public class Utils {
    /**
     * Évalue la moyenne d'une colonne d'un tableau 2D
     *
     * @param tab le tableau à évaluer
     * @param col la colonne à évaluer
     * @return la moyenne
     */
    public static double moyenneEval(int[][] tab, int col) {
        double moyenne = 0;
        for (int[] ints : tab) {
            moyenne += ints[col];
        }
        moyenne = moyenne / tab.length;
        return moyenne;
    }

    /**
     * Évalue le minimum d'une colonne d'un tableau 2D
     *
     * @param tab le tableau à évaluer
     * @param col la colonne à évaluer
     * @return le minimum
     */
    public static int minEval(int[][] tab, int col) {
        int min = tab[0][col];
        for (int[] ints : tab) {
            if (ints[col] < min) {
                min = ints[col];
            }
        }
        return min;
    }

    /**
     * Évalue le maximum d'une colonne d'un tableau 2D
     *
     * @param tab le tableau à évaluer
     * @param col la colonne à évaluer
     * @return le maximum
     */
    public static int maxEval(int[][] tab, int col) {
        int max = tab[0][col];
        for (int[] ints : tab) {
            if (ints[col] > max) {
                max = ints[col];
            }
        }
        return max;
    }

    /**
     * Crée un tableau int 2d à partir d'un DefaultTableModel
     *
     * @param model le DefaultTableModel à convertir
     * @return le DefaultTableModel en tableau int 2d
     */
    public static int[][] convertT2d(DefaultTableModel model) {
        int row = model.getRowCount();
        int col = model.getColumnCount();
        int[][] tab = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                tab[i][j] = Integer.parseInt(model.getValueAt(i, j).toString());
            }
        }

        return tab;
    }

    /**
     * Permute deux valeurs dans un tableau
     *
     * @param tab tableau contenant les valeurs
     * @param i   première valeur
     * @param j   deuxième valeur
     */
    private static void permute(int[] tab, int i, int j) {
        int temp = tab[i];
        tab[i] = tab[j];
        tab[j] = temp;
    }

    /**
     * Trie une partie d'un tableau 2D
     *
     * @param tab    tableau 2d à trier
     * @param tabInd tableau index
     * @param col    colonne utilisé comme filtre
     * @param g      valeur gauche
     * @param d      valeur droite
     * @return index
     */
    private static int partition(int[][] tab, int[] tabInd, int col, int g, int d) {
        int pivot = tab[tabInd[d]][col];
        for (int i = g; i < d; i++) {
            if (tab[tabInd[i]][col] < pivot) {
                permute(tabInd, i, g);
                g++;
            }
        }
        permute(tabInd, g, d);
        return g;
    }

    /**
     * Trie un tableau 2D selon la colonne demander
     *
     * @param tab    tableau 2d à trier
     * @param tabInd tableau indice
     * @param col    colonne utilisé comme filtre
     * @param gauche valeur gauche
     * @param droite valeur droite
     */
    private static void quickSort(int[][] tab, int[] tabInd, int col, int gauche, int droite) {
        if (gauche < droite) {
            int index = partition(tab, tabInd, col, gauche, droite);
            quickSort(tab, tabInd, col, gauche, index - 1);
            quickSort(tab, tabInd, col, index + 1, droite);
        }
    }

    /**
     * Donne l'indice d'une valeur n dans un tableau 2D
     *
     * @param tab      tableau à fouiller
     * @param tabIndex tableau indice pour trier
     * @param search   valeur à rechercher
     * @return l'indice de la valeur, sinon -1
     */
    private static int fouilleDichoDa(int[][] tab, int[] tabIndex, int search) {
        int debut = 0;
        int fin = tabIndex.length - 1;
        int milieu = 0;
        boolean trouve = false;

        while (debut <= fin && !trouve) {
            milieu = (debut + fin) / 2;

            if (search == tab[tabIndex[milieu]][0])
                trouve = true;
            else if (search < tab[tabIndex[milieu]][0])
                fin = milieu - 1;
            else
                debut = milieu + 1;
        }

        return trouve ? tabIndex[milieu] : -1;
    }

    /**
     * Donne l'indice d'un numéro dans un tableau 2D
     *
     * @param tab    tableau à fouiller
     * @param search numéro que l'on cherche
     * @return l'indice de la valeur, ou -1
     */
    public static int getRowFromDA(int[][] tab, int search) {
        int[] tabIndex = new int[tab.length];
        for (int i = 0; i < tabIndex.length; i++) {
            tabIndex[i] = i;
        }

        quickSort(tab, tabIndex, 0, 0, tab.length - 1);
        return fouilleDichoDa(tab, tabIndex, search);
    }

    /**
     * Vérifie si un numéro n'est pas dans un tableau 2D
     *
     * @param tab    tableau à fouiller
     * @param search numéro que l'on cherche
     * @return true si elle n'est pas présente, sinon false
     */
    public static boolean isPresentDA(int[][] tab, int search) {
        return getRowFromDA(tab, search) == -1;
    }
}
