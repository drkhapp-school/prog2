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
import java.util.Date;

public class AddEntretien extends JDialog {
    private boolean validEntry = false; // Si l'entrée est valid
    private String description; // Description du nouvel inventaire
    private LocalDate date; // Date du nouvel inventaire

    JDialog dialog;
    JLabel labDate;
    JLabel labDesc;
    JTextArea txaDesc;
    JDateChooser dateChooser;
    JButton btnAjouter;
    JButton btnAnnuler;

    JPanel panBas;

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

        panBas = new JPanel();
        panBas.setLayout(new FlowLayout(FlowLayout.CENTER));
        panBas.setPreferredSize(dimBas);

        labDate = new JLabel("*Date:");
        labDate.setPreferredSize(dimLab);

        dateChooser = new JDateChooser(new Date());
        dateChooser.setPreferredSize(new Dimension(200, 30));

        labDesc = new JLabel("Description:");
        labDesc.setPreferredSize(dimLab);

        txaDesc = new JTextArea();
        txaDesc.setPreferredSize(dimTxa);
        txaDesc.setBorder(Bordure);

        btnAjouter = new JButton("Ajouter");
        btnAjouter.setPreferredSize(dimBtn);
        btnAjouter.addActionListener(e -> btnAjouterAction());

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setPreferredSize(dimBtn);
        btnAnnuler.addActionListener(e -> btnAnnulerAction());

        panBas.add(btnAjouter);
        panBas.add(btnAnnuler);

        dialog.add(labDate);
        dialog.add(dateChooser);
        dialog.add(labDesc);
        dialog.add(txaDesc);

        dialog.add(panBas);

        dialog.setVisible(true);

    }
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

    public boolean hasValidEntry() {
        return validEntry;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    private void btnAnnulerAction() {
        dialog.dispose();
    }

}
