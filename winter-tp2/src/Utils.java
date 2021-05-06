/**
 * Objectif: Méthodes utilitaires pour la classe Main et les classes de dialogues
 *
 * @author: Jean-Philippe Miguel-Gagnon - 1927230
 * @since: Session H2021
 */

import javax.swing.*;
import java.math.BigDecimal;

public class Utils {
    /**
     * Génère un message d'erreur selon le contexte
     * @param frame le frame à montrer le message d'erreur
     * @param message le message d'erreur à montrer
     */
    public static void sendErrorMessage(java.awt.Component frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Message d'erreur", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Vérifier si les valeurs sont invalide
     * @param text texte à vérifier (ne peut pas être vide)
     * @param prix double à vérifier (ne peut pas avoir plus de 2 décimaux)
     * @return vrai si il y a une erreur
     */
    public static boolean invalidData(String text, double prix) {
        return text.isBlank() || new BigDecimal(String.valueOf(prix)).scale() > 2;
    }

    /**
     * Vérifier si les valeurs sont invalide
     * @param text texte à vérifier (ne peut pas être vide)
     * @return vrai si il y a une erreur
     */
    public static boolean invalidData(String text) {
        return text.isBlank();
    }
}
