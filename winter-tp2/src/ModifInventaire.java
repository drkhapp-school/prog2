import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Date;

public class ModifInventaire extends JDialog {
    JDialog dialog;
    JLabel labNom;
    JTextField txfNom;
    JLabel labSerie;
    JTextField txfSerie;
    JLabel labCat;
    JComboBox CmbCat;
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

    public ModifInventaire() {
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

        txfNom = new JTextField();
        txfNom.setPreferredSize(dimTxf);

        labSerie = new JLabel("No série:");
        labSerie.setPreferredSize(dimLab);

        txfSerie = new JTextField();
        txfSerie.setPreferredSize(dimTxf);

        labCat = new JLabel("Catégorie:");
        labCat.setPreferredSize(dimLab);

        CmbCat = new JComboBox(categories);
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

        btnAjouter = new JButton("Modifier");
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

    private void btnAnnulerAction() {
    }

    private void btnAjouterAction() {
    }
}
