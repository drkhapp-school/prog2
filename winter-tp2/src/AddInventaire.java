import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class AddInventaire extends JDialog {
    JDialog dialog;
    JLabel labNom;
    JTextField txfNom;
    JLabel labSerie;
    JTextField txfSerie;
    JLabel labCat;
    JComboBox<String> CmbCat;
    JLabel labPrix;
    JTextField txfPrix;
    JLabel labDate;
    JLabel labDesc;
    JTextArea txaDesc;
    JDateChooser dateChooser;
    JButton btnAjouter;
    JButton btnAnnuler;

    JPanel panBas;

    String[] categories = {"Jeux", "Caméra", "Accessoires"};

    Dimension dimTxf = new Dimension(200, 30);
    Dimension dimBtn = new Dimension(100, 30);
    Dimension dimLab = new Dimension(125, 30);
    Dimension dimTxa = new Dimension(200, 150);
    Dimension dimBas = new Dimension(400, 50);

    public AddInventaire() {
        dialog = new JDialog((JDialog) null, "Ajout Inventaire", true);
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

        txfNom = new JTextField();
        txfNom.setPreferredSize(dimTxf);

        labSerie = new JLabel("No série:");
        labSerie.setPreferredSize(dimLab);

        txfSerie = new JTextField();
        txfSerie.setPreferredSize(dimTxf);

        labCat = new JLabel("Catégorie:");
        labCat.setPreferredSize(dimLab);

        CmbCat = new JComboBox<>(categories);
        CmbCat.setSelectedIndex(0);
        CmbCat.setPreferredSize(dimTxf);

        labPrix = new JLabel("*Prix:");
        labPrix.setPreferredSize(dimLab);

        txfPrix = new JTextField();
        txfPrix.setPreferredSize(dimTxf);

        labDate = new JLabel("*Date achat:");
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

        dialog.add(labNom);
        dialog.add(txfNom);
        dialog.add(labSerie);
        dialog.add(txfSerie);
        dialog.add(labCat);
        dialog.add(CmbCat);
        dialog.add(labPrix);
        dialog.add(txfPrix);
        dialog.add(labDate);
        dialog.add(dateChooser);
        dialog.add(labDesc);
        dialog.add(txaDesc);

        dialog.add(panBas);

        dialog.setVisible(true);

    }

    private void btnAjouterAction() {
        String[] arr = new String[]{
                txfNom.getText(),
                txfSerie.getText(),
                Objects.requireNonNull(CmbCat.getSelectedItem()).toString(),
                txfPrix.getText(),
                txaDesc.getText()
        };

        String nom;
        double prix;
        LocalDate date;
        String categorie;
        String description;
        String nbSerie;

        // Vérification des entrées
        if (Utils.isEmpty(arr[0])) {
            sendErrorMessage("Nom invalid!");
            return;
        }

        if (Utils.isNotDouble(arr[3])) {
            sendErrorMessage("Prix invalid!");
            return;
        }

        nom = arr[0];
        nbSerie = arr[1];
        categorie = arr[2];
        prix = Double.parseDouble(arr[3]);
        description = arr[4];
        date = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Main.ListeInventaire.add(new Inventaire(nom, description, categorie, date, nbSerie, prix));
        Main.success = true;
        dialog.dispose();
    }

    /**
     * Génère un message d'erreur selon le contexte
     *
     * @param message le message d'erreur à montrer
     */
    private void sendErrorMessage(String message) {
        JOptionPane.showMessageDialog(dialog, message, "Message d'erreur", JOptionPane.ERROR_MESSAGE);
    }


    private void btnAnnulerAction() {
        dialog.dispose();
    }
}