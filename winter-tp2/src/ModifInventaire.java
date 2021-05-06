/**
 * Objectif: Créer un dialogue pour la modification d'un inventaire
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
import java.util.Objects;

public class ModifInventaire extends JDialog {
    private boolean validEntry = false; // Si l'entrée est valid
    private String nom; // Nom du nouvel inventaire
    private String nbSerie; // Numéro de série du nouvel inventaire
    private String categorie; // Catégorie du nouvel inventaire
    private String description; // Description du nouvel inventaire
    private LocalDate date; // Date du nouvel inventaire
    private double prix; // Prix du nouvel inventaire

    JDialog dialog;
    JPanel panButton;

    JLabel labNom, labSerie, labCat, labPrix, labDate, labDesc;
    JTextField txfNom, txfSerie, txfPrix;
    JComboBox<String> cmbCat;
    JTextArea txaDesc;
    JDateChooser dateChooser;
    JButton btnModifier, btnAnnuler;

    String[] categories = Constant.CATEGORY_NAMES;

    Dimension dimTxf = Constant.DIMENSION_TEXT_FIELD;
    Dimension dimBtn = Constant.DIMENSION_BUTTON;
    Dimension dimLab = Constant.DIMENSION_TEXT_LABEL;
    Dimension dimTxa = Constant.DIMENSION_TEXT_AREA;
    Dimension dimBas = new Dimension(400, 50);

    public ModifInventaire(Inventaire inv) {
        dialog = new JDialog((JDialog) null, "Modif Inventaire", true);
        dialog.setSize(400, 425);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        Border Bordure = BorderFactory.createLineBorder(Color.BLACK, 1);

        panButton = new JPanel();
        panButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panButton.setPreferredSize(dimBas);

        labNom = new JLabel("*Nom:");
        labNom.setPreferredSize(dimLab);

        txfNom = new JTextField(inv.getNom());
        txfNom.setPreferredSize(dimTxf);

        labSerie = new JLabel("No série:");
        labSerie.setPreferredSize(dimLab);

        txfSerie = new JTextField(inv.getNbSerie());
        txfSerie.setPreferredSize(dimTxf);

        labCat = new JLabel("Catégorie:");
        labCat.setPreferredSize(dimLab);

        cmbCat = new JComboBox<>(categories);
        cmbCat.setSelectedItem(inv.getCategorie());
        cmbCat.setPreferredSize(dimTxf);

        labPrix = new JLabel("*Prix:");
        labPrix.setPreferredSize(dimLab);

        txfPrix = new JTextField(String.valueOf(inv.getPrix()));
        txfPrix.setPreferredSize(dimTxf);

        labDate = new JLabel("*Date achat:");
        labDate.setPreferredSize(dimLab);

        dateChooser = new JDateChooser(Date.from(inv.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        dateChooser.setPreferredSize(new Dimension(200, 30));

        labDesc = new JLabel("Description:");
        labDesc.setPreferredSize(dimLab);

        txaDesc = new JTextArea(inv.getDescription());
        txaDesc.setPreferredSize(dimTxa);
        txaDesc.setBorder(Bordure);

        btnModifier = new JButton("Ajouter");
        btnModifier.setPreferredSize(dimBtn);
        btnModifier.addActionListener(e -> btnModifierAction());

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setPreferredSize(dimBtn);
        btnAnnuler.addActionListener(e -> dialog.dispose());

        panButton.add(btnModifier);
        panButton.add(btnAnnuler);

        dialog.add(labNom);
        dialog.add(txfNom);
        dialog.add(labSerie);
        dialog.add(txfSerie);
        dialog.add(labCat);
        dialog.add(cmbCat);
        dialog.add(labPrix);
        dialog.add(txfPrix);
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
    private void btnModifierAction() {
        try {
            nom = txfNom.getText();
            nbSerie = txfSerie.getText();
            categorie = Objects.requireNonNull(cmbCat.getSelectedItem()).toString();
            prix = Double.parseDouble(txfPrix.getText());
            description = txaDesc.getText();
            date = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Vérification de donnée
            if (Utils.invalidData(nom, prix)) throw new IllegalArgumentException();

            validEntry = true;
            dialog.dispose();
        } catch (IllegalArgumentException | NullPointerException e) {
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
     * @return le nom de l'inventaire modifié
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return le numéro de série de l'inventaire modifié
     */
    public String getNbSerie() {
        return nbSerie;
    }

    /**
     * @return la catégorie de l'inventaire modifié
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return le prix de l'inventaire modifié
     */
    public double getPrix() {
        return prix;
    }

    /**
     * @return la description de l'inventaire modifié
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return la date de l'inventaire modifié
     */
    public LocalDate getDate() {
        return date;
    }
}