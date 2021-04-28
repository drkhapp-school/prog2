import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Date;

public class AddEntretien extends JDialog {
    JDialog dialog;
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

    private void btnAnnulerAction() {
    }

    private void btnAjouterAction() {
    }
}
