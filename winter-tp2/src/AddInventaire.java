/**
 * Objectif: Créer un dialogue pour l'ajout d'un inventaire
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
import java.util.Objects;

public class AddInventaire extends JDialog {
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
    JButton btnAjouter, btnAnnuler;

    String[] categories = Constant.CATEGORY_NAMES;

    Dimension dimTxf = Constant.DIMENSION_TEXT_FIELD;
    Dimension dimBtn = Constant.DIMENSION_BUTTON;
    Dimension dimLab = Constant.DIMENSION_TEXT_LABEL;
    Dimension dimTxa = Constant.DIMENSION_TEXT_AREA;
    Dimension dimBas = new Dimension(400, 50);

    public AddInventaire() {
        dialog = new JDialog((JDialog) null, "Ajout Inventaire", true);
        dialog.setSize(400, 425);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new FlowLayout());
        Border bordure = BorderFactory.createLineBorder(Color.BLACK, 1);

        panButton = new JPanel();
        panButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panButton.setPreferredSize(dimBas);

        labNom = new JLabel("*Nom:");
        labNom.setPreferredSize(dimLab);

        txfNom = new JTextField();
        txfNom.setPreferredSize(dimTxf);

        labSerie = new JLabel("No série:");
        labSerie.setPreferredSize(dimLab);

        txfSerie = new JTextField();
        txfSerie.setPreferredSize(dimTxf);

        labCat = new JLabel("Catégorie:");
        labCat.setPreferredSize(dimLab);

        cmbCat = new JComboBox<>(categories);
        cmbCat.setSelectedIndex(0);
        cmbCat.setPreferredSize(dimTxf);

        labPrix = new JLabel("*Prix:");
        labPrix.setPreferredSize(dimLab);

        txfPrix = new JTextField();
        txfPrix.setPreferredSize(dimTxf);

        labDate = new JLabel("*Date achat:");
        labDate.setPreferredSize(dimLab);

        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(200, 30));

        labDesc = new JLabel("Description:");
        labDesc.setPreferredSize(dimLab);

        txaDesc = new JTextArea();
        txaDesc.setPreferredSize(dimTxa);
        txaDesc.setBorder(bordure);
        txaDesc.setLineWrap(true);

        btnAjouter = new JButton("Ajouter");
        btnAjouter.setPreferredSize(dimBtn);
        btnAjouter.addActionListener(e -> btnAjouterAction());

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setPreferredSize(dimBtn);
        btnAnnuler.addActionListener(e -> dialog.dispose());

        panButton.add(btnAjouter);
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
    private void btnAjouterAction() {
        try{
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
     * @return le nom du nouvel inventaire
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return le numéro de série du nouvel inventaire
     */
    public String getNbSerie() {
        return nbSerie;
    }

    /**
     * @return la catégorie du nouvel inventaire
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return le prix du nouvel inventaire
     */
    public double getPrix() {
        return prix;
    }

    /**
     * @return la description du nouvel inventaire
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return la date du nouvel inventaire
     */
    public LocalDate getDate() {
        return date;
    }
}