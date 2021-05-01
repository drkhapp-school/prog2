import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class ModifInventaire extends JDialog {
    private boolean validEntry = false;
    private String nom;
    private String nbSerie;
    private String categorie;
    private double prix;
    private String description;
    private LocalDate date;

    JDialog dialog;
    JLabel labNom;
    JTextField txfNom;
    JLabel labSerie;
    JTextField txfSerie;
    JLabel labCat;
    JComboBox<String> cmbCat;
    JLabel labPrix;
    JTextField txfPrix;
    JLabel labDate;
    JLabel labDesc;
    JTextArea txaDesc;
    JDateChooser dateChooser;
    JButton btnModifier;
    JButton btnAnnuler;

    JPanel panBas;

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

        panBas = new JPanel();
        panBas.setLayout(new FlowLayout(FlowLayout.CENTER));
        panBas.setPreferredSize(dimBas);

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
        btnAnnuler.addActionListener(e -> btnAnnulerAction());

        panBas.add(btnModifier);
        panBas.add(btnAnnuler);

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

        dialog.add(panBas);

        dialog.setVisible(true);

    }

    private void btnModifierAction() {
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

    private void btnAnnulerAction() {
        dialog.dispose();
    }

    public boolean hasValidEntry() {
        return validEntry;
    }

    public String getNom() {
        return nom;
    }

    public String getNbSerie() {
        return nbSerie;
    }

    public String getCategorie() {
        return categorie;
    }

    public double getPrix() {
        return prix;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }
}