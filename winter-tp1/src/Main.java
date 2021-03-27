import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.text.DecimalFormat;

public class Main extends JFrame {
    JFrame frame;

    JTable tabNotes;
    JTable tabStats;
    DefaultTableModel mdlNotes;
    DefaultTableModel mdlStats;

    JLabel labDA;
    JLabel labEx1;
    JLabel labEx2;
    JLabel labTp1;
    JLabel labTp2;

    JTextField txfDA;
    JTextField txfEx1;
    JTextField txfEx2;
    JTextField txfTp1;
    JTextField txfTp2;

    JButton btnAjouter;
    JButton btnModifier;
    JButton btnSupprimer;
    JButton btnQuitter;

    JPanel panEast;
    JPanel panWest;
    JPanel panSouth;

    String[] colNames = {"DA", "Examen 1", "Examen 2", "TP 1", "TP 2", "Total %"};
    String[] colNamesStats = {"", "", "", "", "", ""};

    Dimension dimMin = new Dimension(1280, 720);
    Dimension dimLab = new Dimension(150, 30);
    Dimension dimTxf = new Dimension(200, 30);
    Dimension dimBtn = new Dimension(120, 30);
    Dimension dimEast = new Dimension(450, 670);
    Dimension dimWest = new Dimension(700, 670);
    Dimension dimSouth = new Dimension(100, 50);

    // --- Constructeur --- //

    public Main() throws IOException {
        frame = new JFrame("Jean-Philippe Miguel-Gagnon - 1927230");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setMinimumSize(dimMin);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        createPanEast();
        createPanWest();
        createPanSouth();

        frame.setVisible(true);
    }

    public void createPanEast() {
        panEast = new JPanel();
        panEast.setLayout(new FlowLayout(FlowLayout.LEFT));
        panEast.setPreferredSize(dimEast);

        labDA = new JLabel("DA");
        labDA.setPreferredSize(dimLab);
        txfDA = new JTextField("");
        txfDA.setPreferredSize(dimTxf);

        labEx1 = new JLabel("Examen 1");
        labEx1.setPreferredSize(dimLab);
        txfEx1 = new JTextField("");
        txfEx1.setPreferredSize(dimTxf);

        labEx2 = new JLabel("Examen 2");
        labEx2.setPreferredSize(dimLab);
        txfEx2 = new JTextField("");
        txfEx2.setPreferredSize(dimTxf);

        labTp1 = new JLabel("TP 1");
        labTp1.setPreferredSize(dimLab);
        txfTp1 = new JTextField("");
        txfTp1.setPreferredSize(dimTxf);

        labTp2 = new JLabel("TP 2");
        labTp2.setPreferredSize(dimLab);
        txfTp2 = new JTextField("");
        txfTp2.setPreferredSize(dimTxf);


        btnAjouter = new JButton("Ajouter");
        btnAjouter.setPreferredSize(dimBtn);
        btnAjouter.addActionListener(e -> btnAjouterAction());

        btnModifier = new JButton("Modifier");
        btnModifier.setPreferredSize(dimBtn);
        btnModifier.addActionListener(e -> btnModifierAction());

        btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setPreferredSize(dimBtn);
        btnSupprimer.addActionListener(e -> btnSupprimerAction());

        panEast.add(labDA);
        panEast.add(txfDA);
        panEast.add(labEx1);
        panEast.add(txfEx1);
        panEast.add(labEx2);
        panEast.add(txfEx2);
        panEast.add(labTp1);
        panEast.add(txfTp1);
        panEast.add(labTp2);
        panEast.add(txfTp2);
        panEast.add(btnAjouter);
        panEast.add(btnModifier);
        panEast.add(btnSupprimer);

        frame.add(panEast, BorderLayout.EAST);
    }


    public void createPanWest() throws IOException {
        panWest = new JPanel();
        panWest.setLayout(new FlowLayout(FlowLayout.CENTER));
        panWest.setPreferredSize(dimWest);

        // Génération du tableau de notes
        mdlNotes = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabNotes = new JTable(mdlNotes);
        tabNotes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabNotes.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting())
                tabNotesSelectionChange();
        });
        tabNotes.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tabNotesSelectionChange();
                }
            }
        });
        loadData();

        JScrollPane scroll = new JScrollPane(tabNotes);
        scroll.setPreferredSize(new Dimension(600, 500));

        // Génération du tableau de statistiques
        mdlStats = new DefaultTableModel(colNamesStats, 4) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabStats = new JTable(mdlStats);
        tabStats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mdlStats.setValueAt("Moyenne", 0, 0);
        mdlStats.setValueAt("Note minimum", 1, 0);
        mdlStats.setValueAt("Note maximum", 2, 0);
        mdlStats.setValueAt("Nombre d'élèves", 3, 0);
        updateStats();

        JScrollPane scrollStats = new JScrollPane(tabStats);
        scrollStats.setPreferredSize(new Dimension(600, 71));

        // Sélectionner la première entrée
        tabNotes.changeSelection(0, 0, false, false);
        tabNotesSelectionChange();

        panWest.add(scroll);
        panWest.add(scrollStats);

        frame.add(panWest, BorderLayout.WEST);
    }

    public void createPanSouth() {
        panSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panSouth.setPreferredSize(dimSouth);

        btnQuitter = new JButton("Quitter");
        btnQuitter.setPreferredSize(dimBtn);
        btnQuitter.addActionListener(e -> {
                    try {
                        btnQuitterAction();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
        );

        panSouth.add(btnQuitter);

        frame.add(panSouth, BorderLayout.SOUTH);
    }

    // --- JPanel Méthodes --- //


    private void sendErrorMessage(String type) {
        if ("numberInvalid".equals(type)) {
            JOptionPane.showMessageDialog(frame, "Entrée invalide.\nAssurer qu'il y a juste des nombres en bas de 2 147 483 647.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendErrorMessage(String type, int da) {
        switch (type) {
            case ("daExists") -> JOptionPane.showMessageDialog(frame, "Le DA " + da + " existe déjà.", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
            case ("daMissing") -> JOptionPane.showMessageDialog(frame, "Le DA " + da + " n'a aucune entrée", "Message d'erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnAjouterAction() {
        int[][] tab = Utils.convertT2d(mdlNotes); // tableau 2d des notes
        String[] data = new String[]{ // tableau de l'information
                txfDA.getText(),
                txfEx1.getText(),
                txfEx2.getText(),
                txfTp1.getText(),
                txfTp2.getText()
        };
        int da; // da de la personne

        if (!isInteger(data)) {
            sendErrorMessage("numberInvalid");
            return;
        }

        da = Integer.parseInt(data[0]);
        if (!Utils.isPresentDA(tab, da)) {
            sendErrorMessage("daExists", da);
            return;
        }

        mdlNotes.addRow(data);
        updateStats();
    }

    private void btnModifierAction() {
        int[][] tab = Utils.convertT2d(mdlNotes); // tableau 2d des notews
        String[] data = new String[]{ // tableau de l'information
                txfDA.getText(),
                txfEx1.getText(),
                txfEx2.getText(),
                txfTp1.getText(),
                txfTp2.getText()
        };
        int da; // da de la personne

        if (!isInteger(data)) {
            sendErrorMessage("numberInvalid");
            return;
        }

        da = Integer.parseInt(data[0]);
        if (Utils.isPresentDA(tab, da)) {
            sendErrorMessage("daMissing", da);
            return;
        }

        int row = Utils.getRowFromDA(tab, da);
        mdlNotes.removeRow(row);
        mdlNotes.insertRow(row, data);
        updateStats();
    }

    private void btnSupprimerAction() {
        int[][] tab = Utils.convertT2d(mdlNotes);
        String textDA = txfDA.getText();


        if (!isInteger(textDA)) {
            sendErrorMessage("numberInvalid");
            return;
        }

        int da = Integer.parseInt(textDA);
        if (Utils.isPresentDA(tab, da)) {
            sendErrorMessage("daMissing", da);
            return;
        }

        int row = Utils.getRowFromDA(tab, da);
        mdlNotes.removeRow(row);
        updateStats();
    }

    private void btnQuitterAction() throws IOException {
        int rep = JOptionPane.showConfirmDialog(frame, "Voulez-vous sauvegarder?", "Confirmation de fermeture", JOptionPane.YES_NO_CANCEL_OPTION);
        switch (rep) {
            case JOptionPane.YES_OPTION:
                saveData();
            case JOptionPane.NO_OPTION:
                System.exit(0);
                break;
        }
    }

    // --- Méthodes --- //

    public static void main(String[] args) throws IOException {
        new Main();
    }

    /**
     * Modifie les JTextFields avec les valeur du tableau
     */
    private void tabNotesSelectionChange() {
        int row = tabNotes.getSelectedRow();
        txfDA.setText((String) tabNotes.getValueAt(row, 0));
        txfEx1.setText((String) tabNotes.getValueAt(row, 1));
        txfEx2.setText((String) tabNotes.getValueAt(row, 2));
        txfTp1.setText((String) tabNotes.getValueAt(row, 3));
        txfTp2.setText((String) tabNotes.getValueAt(row, 4));
    }

    /**
     * Modifie le tableau tabStats avec les nouvelles informations
     */
    private void updateStats() {
        DecimalFormat df = new DecimalFormat("0.00");
        int[][] tab;
        int moyenne;

        for (int i = 0; i < mdlNotes.getRowCount(); i++) {
            moyenne = 0;
            for (int j = 1; j <= 4; j++) {
                moyenne += Integer.parseInt(mdlNotes.getValueAt(i, j).toString());
            }
            moyenne = moyenne / 4;
            mdlNotes.setValueAt(moyenne, i, 5);
        }

        tab = Utils.convertT2d(mdlNotes); //Tableau 2d de tabNotes

        for (int i = 1; i <= 5; i++) {
            mdlStats.setValueAt(df.format(Utils.moyenneEval(tab, i)), 0, i);
            mdlStats.setValueAt(Utils.minEval(tab, i), 1, i);
            mdlStats.setValueAt(Utils.maxEval(tab, i), 2, i);
        }
        mdlStats.setValueAt(mdlNotes.getRowCount(), 3, 1);
    }

    /**
     * Insère l'information dans le fichier notes.txt dans le tableau tabNotes
     */
    private void loadData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("notes.txt"));
        String[] arr;
        String line;

        while ((line = reader.readLine()) != null) {
            arr = line.split(" ");
            mdlNotes.addRow(arr);
        }

        reader.close();
    }

    /**
     * Sauvegarde le tableau tabNotes dans un fichier notes.txt
     */
    private void saveData() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("notes.txt", false));
        String data;

        for (int i = 0; i < tabNotes.getRowCount(); i++) {
            for (int j = 0; j < 5; j++) {
                data = (String) tabNotes.getValueAt(i, j);
                writer.write(data);
                if (j < 4) {
                    writer.write(" ");
                } else if (i < tabNotes.getRowCount() - 1) {
                    writer.newLine();
                }
            }
        }

        writer.close();
    }

    /**
     * Vérifie si un string est un integer valid
     *
     * @param str String à évaluer
     * @return true si le string est un nombre, sinon false
     */
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Vérifie si un array de string est un array de integer valid
     *
     * @param arr Tableau de string à évaluer
     * @return true si les strings sont des integers, sinon false
     */
    private boolean isInteger(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            try {
                Integer.parseInt(arr[i]);
                i++;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

}
