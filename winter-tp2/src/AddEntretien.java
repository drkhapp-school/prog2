/**
 * Objectif: Créer un dialogue pour l'ajout d'un entretien
 *
 * @author: Jean-Philippe Miguel-Gagnon - 1927230
 * @since: Session H2021
 */

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class AddEntretien extends JDialog {
    private boolean validEntry = false; // Si l'entrée est valid
    private String description; // Description du nouvel inventaire
    private LocalDate date; // Date du nouvel inventaire

    JDialog dialog;
    JPanel panButton;

    JLabel labDate, labDesc;
    JTextArea txaDesc;
    JDateChooser dateChooser;
    JButton btnAjouter, btnAnnuler;

    Dimension dimBtn = Constant.DIMENSION_BUTTON;
    Dimension dimLab = Constant.DIMENSION_TEXT_LABEL;
    Dimension dimTxa = Constant.DIMENSION_TEXT_AREA;
    Dimension dimBas = new Dimension(400, 50);

    public AddEntretien() {
        dialog = new JDialog((JDialog) null, "Ajout Entretien", true);
        dialog.setSize(400, 275);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        Border Bordure = BorderFactory.createLineBorder(Color.BLACK, 1);

        panButton = new JPanel();
        panButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panButton.setPreferredSize(dimBas);

        labDate = new JLabel("*Date:");
        labDate.setPreferredSize(dimLab);

        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(200, 30));

        labDesc = new JLabel("Description:");
        labDesc.setPreferredSize(dimLab);

        txaDesc = new JTextArea();
        txaDesc.setPreferredSize(dimTxa);
        txaDesc.setBorder(Bordure);
        txaDesc.setLineWrap(true);

        btnAjouter = new JButton("Ajouter");
        btnAjouter.setPreferredSize(dimBtn);
        btnAjouter.addActionListener(e -> btnAjouterAction());

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setPreferredSize(dimBtn);
        btnAnnuler.addActionListener(e -> dialog.dispose());

        panButton.add(btnAjouter);
        panButton.add(btnAnnuler);

        dialog.add(labDate);
        dialog.add(dateChooser);
        dialog.add(labDesc);
        dialog.add(txaDesc);

        dialog.add(panButton);

        dialog.setVisible(true);

    }

    /**
     * Vérifie si l'entrée est valide, faire un message d'erreur si il y a une entrée invalide
     */
    private void btnAjouterAction() {
        try{
            description = txaDesc.getText();
            date = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Vérification de donnée
            if (Utils.invalidData(description)) throw new IllegalArgumentException();

            validEntry = true;
            dialog.dispose();
        } catch(IllegalArgumentException | NullPointerException e){
            Utils.sendErrorMessage(dialog, "Erreur de donnée!");
        }
    }

    /**
     * @return vrai si l'entrée est valide, sinon faux
     */
    public boolean hasValidEntry() {
        return validEntry;
    }

    /**
     * @return la date du nouvel inventaire
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return la description du nouvel inventaire
     */
    public String getDescription() {
        return description;
    }
}
