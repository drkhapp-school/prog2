/**
 * Objectif: Teste unitaires pour tester les méthodes utilitaires
 *
 * @author: Jean-Philippe Miguel-Gagnon - 1927230
 * Session H2021
 */

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Util Tests")
class UtilsTest {

    int[][] tab2d = { // Tableau 2D pour évaluer
            {1234501, 51, 61, 71, 81},
            {1234502, 52, 62, 72, 82},
            {1234503, 53, 63, 73, 83},
            {1234504, 11, 22, 55, 44},
            {1234505, 99, 84, 96, 98},
            {1234506, 51, 61, 71, 81},
            {1234507, 52, 62, 72, 82},
            {1234508, 53, 63, 73, 83},
            {1234509, 11, 22, 55, 44},
            {1234510, 99, 84, 96, 98},
            {1234511, 51, 61, 71, 81},
            {1234512, 52, 62, 72, 82},
            {1234513, 53, 63, 73, 83},
            {1234514, 11, 22, 55, 44},
            {1234515, 99, 84, 96, 98},
            {1234516, 51, 61, 71, 81},
            {1234517, 52, 62, 72, 82},
            {1234518, 53, 63, 73, 83},
            {1234519, 11, 22, 55, 44},
            {1234520, 99, 84, 96, 98},
            {1234521, 51, 61, 71, 81},
            {1234522, 52, 62, 72, 82}
    };

    /**
     * Test de la méthode moyenneEval()
     */
    @Test
    public void moyenneEval() {
        int col = 1; // colonne à évaluer
        double expectedResult = 53.0454545454545455; // moyenne attendu
        double result = Utils.moyenneEval(tab2d, col); // résultat

        assertEquals(expectedResult, result, "Average of Column");
    }

    /**
     * Test de la méthode minEval()
     */
    @Test
    public void minEval() {
        int col = 2; // colonne à évaluer
        int expectedResult = 22; // minimum attendu
        int result = Utils.minEval(tab2d, col); // résultat

        assertEquals(expectedResult, result, "Minimum of column");

        col = 3;
        expectedResult = 55;
        result = Utils.minEval(tab2d, col);
        assertEquals(expectedResult, result, "Multiple minimums");
    }

    /**
     * Test de la méthode maxEval()
     */
    @Test
    public void maxEval() {
        int col = 1; // colonne à évaluer
        int expectedResult = 99; // maximum attendu
        int result = Utils.maxEval(tab2d, col); // résultat
        assertEquals(expectedResult, result, "Maximum of column");

        col = 3;
        expectedResult = 96;
        result = Utils.maxEval(tab2d, col);
        assertEquals(expectedResult, result, "Multiple maximums");

    }

    /**
     * Test de la méthode isPresentDa()
     */
    @Test
    void isPresentDA() {
        int search = 1234522; // da à trouver
        boolean result = Utils.isPresentDA(tab2d, search); // résultat
        assertFalse(result, "Number found");

        search = 1234523;
        result = Utils.isPresentDA(tab2d, search);
        assertTrue(result, "Number not found");

    }
}