import javax.swing.*;

public class Utils {
    /**
     * Génère un message d'erreur selon le contexte
     *
     * @param message le message d'erreur à montrer
     */
    public static void sendErrorMessage(java.awt.Component frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Message d'erreur", JOptionPane.ERROR_MESSAGE);
    }
}
