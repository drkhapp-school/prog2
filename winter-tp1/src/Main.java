/**
 * Objectif: Application permettant de gérer des notes d'un groupe d'élèves et de créer des statistiques
 *
 * @author: Jean-Philippe Miguel-Gagnon - 1927230
 * Session H2021
 */

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

    Dimension dimMin = new Dimension(1280, 720);
    Dimension dimLab = new Dimension(150, 30);
    Dimension dimTxf = new Dimension(200, 30);
    Dimension dimBtn = new Dimension(120, 30);
    Dimension dimEast = new Dimension(450, 670);
    Dimension dimWest = new Dimension(700, 670);
    Dimension dimSouth = new Dimension(100, 50);

    // --- Constructeur --- //
    public Main() throws IOException {
        frame = new JFrame("Jean-Philippe Miguel-Gagnon + 1927230");
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

    /**
     * Constructeur du panneau de droite
     */
    public void createPanEast() {
        panEast = new JPanel();
        panEast.setLayout(new FlowLayout(FlowLayout.LEFT));
        panEast.setPreferredSize(dimEast);

        // Génération des JTextFields et JLabels
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

        // Génération des JButtons
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

    /**
     * Constructeur du panneau de gauche
     */
    public void createPanWest() throws IOException {
        String[] colNotes = {"DA", "Examen 1", "Examen 2", "TP 1", "TP 2", "Total %"}; // Noms des colonnes du tabNotes
        String[] colStats = {"", "", "", "", "", ""}; // Noms des colonnes du tabStats

        panWest = new JPanel();
        panWest.setLayout(new FlowLayout(FlowLayout.CENTER));
        panWest.setPreferredSize(dimWest);

        // Génération du tableau de notes
        mdlNotes = new DefaultTableModel(colNotes, 0) {
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
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
                    tabNotesSelectionChange();
            }
        });
        loadData();

        JScrollPane scroll = new JScrollPane(tabNotes);
        scroll.setPreferredSize(new Dimension(600, 500));

        // Génération du tableau de statistiques
        mdlStats = new DefaultTableModel(colStats, 4) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabStats = new JTable(mdlStats);
        tabStats.setFocusable(false);
        tabStats.setRowSelectionAllowed(false);
        mdlStats.setValueAt("Moyenne", 0, 0);
        mdlStats.setValueAt("Note minimum", 1, 0);
        mdlStats.setValueAt("Note maximum", 2, 0);
        mdlStats.setValueAt("Nombre d'élèves", 3, 0);
        updateStats();

        JScrollPane scrollStats = new JScrollPane(tabStats);
        scrollStats.setPreferredSize(new Dimension(600, 71));

        // Sélectionner la première entrée
        try {
            tabNotes.setRowSelectionInterval(0, 0);
            tabNotesSelectionChange();
        } catch (IllegalArgumentException ignored) {
        }

        panWest.add(scroll);
        panWest.add(scrollStats);
        frame.add(panWest, BorderLayout.WEST);
    }

    /**
     * Constructeur du panneau du bas
     */
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
        });

        panSouth.add(btnQuitter);
        frame.add(panSouth, BorderLayout.SOUTH);
    }

    // --- Action Listeners -- //

    /**
     * Mis à jour des JTextFields dans le panneau de droite
     */
    private void tabNotesSelectionChange() {
        int row = tabNotes.getSelectedRow();
        txfDA.setText(Utils.valueToString(mdlNotes, row, 0));
        txfEx1.setText(Utils.valueToString(mdlNotes, row, 1));
        txfEx2.setText(Utils.valueToString(mdlNotes, row, 2));
        txfTp1.setText(Utils.valueToString(mdlNotes, row, 3));
        txfTp2.setText(Utils.valueToString(mdlNotes, row, 4));
    }

    /**
     * Ajout d'une nouvelle entrée dans la base de données
     */
    private void btnAjouterAction() {
        int[][] tab = Utils.convertT2d(mdlNotes); // tableau 2d des notes
        String[] arr = new String[]{ // tableau de l'information
                txfDA.getText(),
                txfEx1.getText(),
                txfEx2.getText(),
                txfTp1.getText(),
                txfTp2.getText()
        };

        // Entrée invalide
        if (invalidEntry(arr))
            sendErrorMessage("Entrée invalide.\nAssurer que les notes sont entre 0 et 100, et que le DA est un nombre positif en bas de 2 147 483 648.");
        else {
            int da = Integer.parseInt(arr[0]);

            // DA existant dans une autre entrée
            if (!Utils.isPresentDA(tab, da))
                sendErrorMessage("Le DA " + da + " existe déjà.");
            else {
                mdlNotes.addRow(arr);
                tabNotes.setRowSelectionInterval(mdlNotes.getRowCount() - 1, mdlNotes.getRowCount() - 1);
                updateStats();
            }
        }
    }

    /**
     * Modification d'une entrée dans la base de données
     */
    private void btnModifierAction() {
        int[][] tab = Utils.convertT2d(mdlNotes);
        int row = tabNotes.getSelectedRow(); // ligne sélectionnée
        String[] arr = new String[]{ // tableau de l'information
                txfDA.getText(),
                txfEx1.getText(),
                txfEx2.getText(),
                txfTp1.getText(),
                txfTp2.getText()
        };
        int da;

        // Aucune sélection
        if (row == -1)
            sendErrorMessage("Veuillez sélectionner une ligne.");
        // Entrée invalide
        else if (invalidEntry(arr))
            sendErrorMessage("Entrée invalide.\nAssurer que les notes sont entre 0 et 100, et que le DA est un nombre positif en bas de 2 147 483 648.");
        else {
            da = Integer.parseInt(arr[0]);

            // DA existant dans une autre entrée
            if (!Utils.isPresentDA(tab, da) && tab[row][0] != da)
                sendErrorMessage("Le DA " + da + " existe déjà.");
            else {
                for (int i = 0; i < arr.length; i++)
                    mdlNotes.setValueAt(arr[i], row, i);

                updateStats();
            }
        }
    }

    /**
     * Supprime une entrée dans la base de données
     */
    private void btnSupprimerAction() {
        int row = tabNotes.getSelectedRow(); // ligne sélectionnée

        // Aucune sélection
        if (row == -1)
            sendErrorMessage("Veuillez sélectionner une ligne.");
        else {
            mdlNotes.removeRow(row);
            txfDA.setText(null);
            txfEx1.setText(null);
            txfEx2.setText(null);
            txfTp1.setText(null);
            txfTp2.setText(null);
            updateStats();
        }
    }

    /**
     * Quitter l'application en offrant l'option de sauvegarder
     */
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
     * Génère un message d'erreur selon le contexte
     *
     * @param message le message d'erreur à montrer
     */
    private void sendErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Message d'erreur", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Modifie la colonne de totaux et le tableau tabStats avec les nouvelles informations
     */
    private void updateStats() {
        DecimalFormat df = new DecimalFormat("0.00"); // Format des pourcentages
        int[][] tab; // Tableau des notes
        int moyenne; // Moyenne pour total

        // Calcul du total
        for (int i = 0; i < mdlNotes.getRowCount(); i++) {
            moyenne = 0;
            for (int j = 1; j <= 4; j++)
                moyenne += Integer.parseInt(mdlNotes.getValueAt(i, j).toString());

            moyenne = moyenne / 4;
            mdlNotes.setValueAt(moyenne, i, 5);
        }

        // Calcul des statistiques
        tab = Utils.convertT2d(mdlNotes);

        // Si le tableau est vide, on envoie un nouveau tableau avec que des 0 pour les statistiques
        if (mdlNotes.getRowCount() == 0)
            tab = new int[][]{{0, 0, 0, 0, 0, 0}};

        for (int i = 1; i < tab[0].length; i++) {
            mdlStats.setValueAt(df.format(Utils.moyenneEval(tab, i)), 0, i);
            mdlStats.setValueAt(Utils.minEval(tab, i), 1, i);
            mdlStats.setValueAt(Utils.maxEval(tab, i), 2, i);
        }
        mdlStats.setValueAt(mdlNotes.getRowCount(), 3, 1);
    }

    /**
     * Lecture de notes.txt pour insérer dans le tableau de notes
     */
    private void loadData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("notes.txt"));
        String[] arr; // tableau contenant les données
        String line; // ligne présentement en lecture

        while ((line = reader.readLine()) != null) {
            arr = line.split(" ");
            mdlNotes.addRow(arr);
        }

        reader.close();
    }

    /**
     * Sauvegarde du tableau de notes dans notes.txt
     */
    private void saveData() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("notes.txt", false));
        String data; // String à enregistrer dans le fichier

        for (int i = 0; i < mdlNotes.getRowCount(); i++) {
            for (int j = 0; j < 5; j++) {
                data = Utils.valueToString(mdlNotes, i, j);
                writer.write(data);
                if (j < 4)
                    writer.write(" ");
                else if (i < mdlNotes.getRowCount() - 1)
                    writer.newLine();
            }
        }

        writer.close();
    }

    /**
     * Vérifie si il y a une entrée erronée.
     * Les nombres doivent être positives, et, à partir de la 2eme entrée, doivent être entre 0 et 100
     *
     * @param arr Tableau de String à évaluer
     * @return true si un String est erronée, sinon false
     */
    private boolean invalidEntry(String[] arr) {
        boolean invalid = false; // si l'entrée est invalide
        int i = 0; // int pour la boucle

        while (i < arr.length && !invalid) {
            try {
                // Vérifier si c'est un nombre
                int test = Integer.parseInt(arr[i]);

                // Vérifier si le nombre n'est pas positif
                if (test < 0)
                    invalid = true;

                // Vérifier si le nombre n'est pas entre 0 et 100 (seulement si c'est pas la 1e entrée)
                if (i != 0 && test > 100)
                    invalid = true;

                i++;
            } catch (NumberFormatException e) {
                invalid = true;
            }
        }

        return invalid;
    }
}
