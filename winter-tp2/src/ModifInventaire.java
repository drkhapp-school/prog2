import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class ModifInventaire extends JDialog {
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

    public ModifInventaire(Inventaire inv) {
        dialog = new JDialog((JDialog) null, "Modification inventaire", true);
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

        CmbCat = new JComboBox<>(categories);
        CmbCat.setSelectedItem(inv.getCategorie());
        CmbCat.setPreferredSize(dimTxf);

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

        btnAjouter = new JButton("Modifier");
        btnAjouter.setPreferredSize(dimBtn);
        btnAjouter.addActionListener(e -> btnAjouterAction(inv));

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

    private void btnAjouterAction(Inventaire inv) {
        String[] arr = new String[]{
                txfNom.getText(),
                txfSerie.getText(),
                Objects.requireNonNull(CmbCat.getSelectedItem()).toString(),
                txfPrix.getText(),
                txaDesc.getText()
        };

        // Vérification des entrées
        if (arr[0].isBlank()) {
            Utils.sendErrorMessage(dialog, "Nom invalid!");
            return;
        }

        if (arr[3].isBlank()) {
            Utils.sendErrorMessage(dialog, "Prix invalid!");
            return;
        }

        if (dateChooser.getDate() == null) {
            Utils.sendErrorMessage(dialog, "Date invalid!");
            return;
        }

        inv.setNom(arr[0]);
        inv.setNbSerie(arr[1]);
        inv.setCategorie(arr[2]);
        inv.setPrix(Double.parseDouble(arr[3]));
        inv.setDescription(arr[4]);
        inv.setDate(dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dialog.dispose();
    }

    private void btnAnnulerAction() {
        dialog.dispose();
    }
}
