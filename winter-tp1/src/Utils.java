/**
 * Objectif: Méthodes utilitaires pour faciliter les méthodes dans la classe Main
 *
 * @author: Jean-Philippe Miguel-Gagnon
 * Session H2021
 */


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
        double moyenne = 0; // moyenne à retourner
        for (int[] ints : tab)
            moyenne += ints[col];

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
        int min = tab[0][col]; // minimum à retourner
        for (int[] ints : tab)
            if (ints[col] < min)
                min = ints[col];

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
        int max = tab[0][col]; // maximum à retourner
        for (int[] ints : tab)
            if (ints[col] > max)
                max = ints[col];

        return max;
    }

    /**
     * Crée un tableau int 2d à partir d'un DefaultTableModel
     *
     * @param model le DefaultTableModel à convertir
     * @return le DefaultTableModel en tableau int 2d
     */
    public static int[][] convertT2d(DefaultTableModel model) {
        int row = model.getRowCount(); // nombre de lignes
        int col = model.getColumnCount(); // nombre de colonne
        int[][] tab = new int[row][col]; // tableau à retourner

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                tab[i][j] = valueToInt(model, i, j);

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
        int temp = tab[i]; // variable temporaire pour la permutation
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
     * @return l'index pour le quicksort
     */
    private static int partition(int[][] tab, int[] tabInd, int col, int g, int d) {
        int pivot = tab[tabInd[d]][col];
        for (int i = g; i < d; i++)
            if (tab[tabInd[i]][col] < pivot) {
                permute(tabInd, i, g);
                g++;
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
        int debut = 0; // début de la zone de fouille
        int fin = tabIndex.length - 1; // fin de la zone de fouille
        int milieu = 0; // milieu de la zone de fouille
        boolean trouver = false; // si on a trouver search

        while (debut <= fin && !trouver) {
            milieu = (debut + fin) / 2;

            if (search == tab[tabIndex[milieu]][0])
                trouver = true;
            else if (search < tab[tabIndex[milieu]][0])
                fin = milieu - 1;
            else
                debut = milieu + 1;
        }

        return trouver ? tabIndex[milieu] : -1;
    }

    /**
     * Vérifie si un numéro n'est pas dans un tableau 2D
     *
     * @param tab    tableau à fouiller
     * @param search numéro que l'on cherche
     * @return true si elle n'est pas présente, sinon false
     */
    public static boolean isPresentDA(int[][] tab, int search) {
        int[] tabIndex = new int[tab.length]; // tableau d'index
        for (int i = 0; i < tabIndex.length; i++)
            tabIndex[i] = i;

        quickSort(tab, tabIndex, 0, 0, tab.length - 1);
        return fouilleDichoDa(tab, tabIndex, search) == -1;
    }

    /**
     * Retourne la valeur d'une table en int
     * @param model le DefaultTableModel à évaluer
     * @param row la ligne
     * @param col la colonne
     * @return Integer de l'objet dans le tableau
     */
    public static int valueToInt(DefaultTableModel model, int row, int col) {
        return Integer.parseInt(model.getValueAt(row, col).toString());
    }

    /**
     * Retourne la valeur d'une table en String
     * @param model le DefaultTableModel à évaluer
     * @param row la ligne
     * @param col la colonne
     * @return String de l'objet dans le tableau
     */
    public static String valueToString(DefaultTableModel model, int row, int col) {
        return model.getValueAt(row, col).toString();
    }
}
