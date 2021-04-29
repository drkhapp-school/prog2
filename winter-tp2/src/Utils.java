import javax.swing.*;

public class Utils {
    /**
     * Vérifie si l'input est pas un double
     *
     * @param input Tableau de String à évaluer
     * @return true si un String est un nombre, sinon faux
     */
    public static boolean isNotDouble(String input) {
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    /**
     * Vérifie si c'est vide
     *
     * @param input Tableau de String à évaluer
     * @return true si un String est un nombre, sinon faux
     */
    public static boolean isEmpty(String input) {
        return input.equals("");
    }

    /**
     * Génère un message d'erreur selon le contexte
     *
     * @param message le message d'erreur à montrer
     */
    public static void sendErrorMessage(java.awt.Component frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Message d'erreur", JOptionPane.ERROR_MESSAGE);
    }
}
