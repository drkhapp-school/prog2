import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

public class Main extends JFrame implements Constant {
    private ArrayList<Inventaire> ListeInventaire = new ArrayList<>(); // Liste des inventaires
    private File fichier; // Le fichier en cours
    private boolean isLoaded = false; // Si un fichier est en cours
    private final String title = "Jean-Philippe Miguel-Gagnon"; // Titre de l'application
    private final String[] colInventaire = {"Nom", "Catégorie", "Prix", "Date achat", "Description"}; // Nom des colonnes pour tabInventaire
    private final String[] colEntretien = {"Date", "Description"}; // Nom des colonnes pour tabEntretien

    JFrame frame;
    JPanel panNorth, panWest, panEast, panSouth;

    JMenuBar menuBar;
    JMenu menuTP2, menuFichier;
    JMenuItem miAbout, miQuit, miNew, miOpen, miClose, miSave, miSaveAs, miExport;

    JTable tabInventaire, tabEntretien;
    DefaultTableModel mdlInventaire, mdlEntretien;
    TableRowSorter<DefaultTableModel> sorter;

    JTextField txfFilter;
    JButton btnFilter;

    JButton btnAddInv, btnDelInv, btnAddEnt, btnDelEnt, btnQuit;

    Dimension dimTxf = DIMENSION_TEXT_FIELD;
    Dimension dimBtn = DIMENSION_BUTTON;
    Dimension dimNorth = new Dimension(1300, 40);
    Dimension dimEast = new Dimension(500, 700);
    Dimension dimWest = new Dimension(800, 700);
    Dimension dimSouth = new Dimension(1300, 50);

    // --- Constructeur --- //

    public Main() {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(1325, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApp();
            }
        });

        createMenuBar();
        createPanNorth();
        createPanWest();
        createPanEast();
        createPanSouth();
        frame.setVisible(true);
    }

    /**
     * Constructeur du menu de bar
     */
    private void createMenuBar() {
        menuBar = new JMenuBar();

        // Menu "TP2"
        menuTP2 = new JMenu("TP2");
        miAbout = new JMenuItem("À propos");
        miAbout.addActionListener(e -> miAboutAction());
        miAbout.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK));

        miQuit = new JMenuItem("Quitter");
        miQuit.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));
        miQuit.addActionListener(e -> miQuitAction());

        menuBar.add(menuTP2);
        menuTP2.add(miAbout);
        menuTP2.addSeparator();
        menuTP2.add(miQuit);

        // Menu "Fichier"
        menuFichier = new JMenu("Fichier");

        miNew = new JMenuItem("Nouveau...");
        miNew.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
        miNew.addActionListener(e -> miNewAction());

        miOpen = new JMenuItem("Ouvrir...");
        miOpen.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
        miOpen.addActionListener(e -> miOpenAction());

        miClose = new JMenuItem("Fermer");
        miClose.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_DOWN_MASK));
        miClose.addActionListener(e -> miCloseAction());

        miSave = new JMenuItem("Enregistrer");
        miSave.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
        miSave.addActionListener(e -> miSaveAction());

        miSaveAs = new JMenuItem("Enregistrer sous...");
        miSaveAs.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        miSaveAs.addActionListener(e -> miSaveAsAction());

        miExport = new JMenuItem("Exporter...");
        miExport.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));
        miExport.addActionListener(e -> miExportAction());

        menuBar.add(menuFichier);
        menuFichier.add(miNew);
        menuFichier.add(miOpen);
        menuFichier.add(miClose);
        menuFichier.addSeparator();
        menuFichier.add(miSave);
        menuFichier.add(miSaveAs);
        menuFichier.addSeparator();
        menuFichier.add(miExport);

        frame.setJMenuBar(menuBar);
    }

    /**
     * Constructeur du panneau du haut (bouton filtre)
     */
    private void createPanNorth() {
        panNorth = new JPanel();
        panNorth.setLayout(new FlowLayout(FlowLayout.LEFT));
        panNorth.setPreferredSize(dimNorth);

        txfFilter = new JTextField();
        txfFilter.setPreferredSize(dimTxf);

        btnFilter = new JButton("Filtrer");
        btnFilter.setPreferredSize(dimBtn);
        btnFilter.addActionListener(e -> btnFilterAction());

        panNorth.add(txfFilter);
        panNorth.add(btnFilter);

        frame.add(panNorth, BorderLayout.NORTH);
    }

    /**
     * Constructeur du panneau de gauche (tableau d'inventaire)
     */
    private void createPanWest() {
        panWest = new JPanel();
        panWest.setLayout(new FlowLayout(FlowLayout.LEFT));
        panWest.setPreferredSize(dimWest);

        // Tableau d'inventaire
        mdlInventaire = new DefaultTableModel(colInventaire, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                try {
                    return getValueAt(0, column).getClass();
                } catch (ArrayIndexOutOfBoundsException e) {
                    return super.getColumnClass(column);
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabInventaire = new JTable(mdlInventaire);
        tabInventaire.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabInventaire.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                tabInventaireSelectionChange();
            }
        });

        tabInventaire.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tabInventaireSelectionChange();
                }
            }
        });

        tabInventaire.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    modifyInventaire();
                }
            }
        });

        tabInventaire.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(mdlInventaire);
        tabInventaire.setRowSorter(sorter);

        JScrollPane scrollInv = new JScrollPane(tabInventaire);
        scrollInv.setPreferredSize(new Dimension(790, 600));

        // Boutons d'inventaire
        btnAddInv = new JButton("+");
        btnAddInv.setPreferredSize(dimBtn);
        btnAddInv.addActionListener(e -> btnAddInvAction());

        btnDelInv = new JButton("-");
        btnDelInv.setPreferredSize(dimBtn);
        btnDelInv.addActionListener(e -> btnDelInvAction());

        panWest.add(scrollInv);
        panWest.add(btnAddInv);
        panWest.add(btnDelInv);

        frame.add(panWest, BorderLayout.WEST);
    }

    /**
     * Constructeur du panneau de droite (tableau d'entretien)
     */
    private void createPanEast() {
        panEast = new JPanel();
        panEast.setLayout(new FlowLayout(FlowLayout.LEFT));
        panEast.setPreferredSize(dimEast);

        // Tableau d'entretien
        mdlEntretien = new DefaultTableModel(colEntretien, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabEntretien = new JTable(mdlEntretien);
        tabEntretien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollEnt = new JScrollPane(tabEntretien);
        scrollEnt.setPreferredSize(new Dimension(490, 600));

        // Boutons d'entretien
        btnAddEnt = new JButton("+");
        btnAddEnt.setPreferredSize(dimBtn);
        btnAddEnt.addActionListener(e -> btnAddEntAction());

        btnDelEnt = new JButton("-");
        btnDelEnt.setPreferredSize(dimBtn);
        btnDelEnt.addActionListener(e -> btnDelEntAction());

        panEast.add(scrollEnt);
        panEast.add(btnAddEnt);
        panEast.add(btnDelEnt);

        frame.add(panEast, BorderLayout.EAST);
    }


    /**
     * Constructeur du panneau du bas (bouton quitter)
     */
    private void createPanSouth() {
        panSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panSouth.setPreferredSize(dimSouth);

        btnQuit = new JButton("Quitter");
        btnQuit.setPreferredSize(dimBtn);
        btnQuit.addActionListener(e -> btnQuitAction());

        panSouth.add(btnQuit);

        frame.add(panSouth, BorderLayout.SOUTH);
    }

    // --- Action Listeners --- //

    /**
     * Mis à jour du tableau d'entretien selon la ligne sélectionnée
     */
    private void tabInventaireSelectionChange() {
        int row = tabInventaire.getSelectedRow();
        if (row == -1) {
            mdlEntretien.setRowCount(0);
            return;
        }

        updateTabEnt(ListeInventaire.get(tabInventaire.convertRowIndexToModel(row)));
    }

    // --- Bar de Menu: TP2 --- //

    /**
     * Afficher l'information sur l'application
     */
    private void miAboutAction() {
        JOptionPane.showMessageDialog(frame, "Travail Pratique 2\nJean-Philippe Miguel-Gagnon - 1927230\nSession H2021\nDans le cadre du cours 420-C27", "À propos", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Quitter l'application
     */
    private void miQuitAction() {
        exitApp();
    }

    // --- Bar de Menu: Fichier --- //

    /**
     * Ouvrir un inventaire existant
     */
    private void miOpenAction() {
        if (fileAlreadyLoaded()) return;

        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter fcFilter = new FileNameExtensionFilter("*.dat", "dat");

        fc.setDialogTitle("Open inventaire");
        fc.setFileFilter(fcFilter);

        int rep = fc.showOpenDialog(frame);
        if (rep != JFileChooser.APPROVE_OPTION) return;

        fichier = fc.getSelectedFile();

        readData();
        openedFile();
    }

    /**
     * Créer un nouvel inventaire
     */
    private void miNewAction() {
        if (fileAlreadyLoaded()) return;

        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter fcFilter = new FileNameExtensionFilter("*.dat", "dat");

        fc.setDialogTitle("Nouveau inventaire");
        fc.setFileFilter(fcFilter);

        int rep = fc.showSaveDialog(frame);
        if (rep != JFileChooser.APPROVE_OPTION) return;

        fichier = fc.getSelectedFile();

        saveData();
        openedFile();
    }

    /**
     * Fermer l'inventaire en cours
     */
    private void miCloseAction() {
        if (isNotOpen()) return;

        if (fileAlreadyLoaded()) return;
        closeFile();
    }

    /**
     * Sauvegarder l'inventaire présent
     */
    private void miSaveAction() {
        if (isNotOpen()) return;
        saveData();
    }

    /**
     * Sauvegarder l'inventaire présent dans un nouveau fichier
     */
    private void miSaveAsAction() {
        if (isNotOpen()) return;

        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter fcFilter = new FileNameExtensionFilter("*.dat", "dat");

        fc.setDialogTitle("Enregistrement inventaire");
        fc.setFileFilter(fcFilter);

        int rep = fc.showSaveDialog(frame);
        if (rep != JFileChooser.APPROVE_OPTION) return;

        fichier = fc.getSelectedFile();

        saveData();
        openedFile();
    }

    /**
     * Exporter l'inventaire en fichier .txt
     */
    private void miExportAction() {
        if (isNotOpen()) return;

        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter fcFilter = new FileNameExtensionFilter("*.txt", "txt");

        fc.setDialogTitle("Exporter l'inventaire");
        fc.setFileFilter(fcFilter);

        int rep = fc.showSaveDialog(frame);
        if (rep != JFileChooser.APPROVE_OPTION) return;

        String output = fc.getSelectedFile().getPath();
        if (!output.endsWith("txt")) output = output.concat(".txt");

        saveExport(output);
    }

    // --- Action Listeners des boutons --- //

    /**
     * Filtrer le tableau selon le filtre donner
     */
    private void btnFilterAction() {
        String text = txfFilter.getText();
        if (text.isBlank()) {
            sorter.setRowFilter(null);
            return;
        }

        try {
            sorter.setRowFilter(RowFilter.regexFilter(text));
            tabInventaire.getSelectionModel().clearSelection();
        } catch (PatternSyntaxException e) {
            Utils.sendErrorMessage(frame, "Erreur de filtre");
        }

    }

    /**
     * Quitter l'application
     */
    private void btnQuitAction() {
        exitApp();
    }

    // --- Inventaire --- //

    /**
     * Ajout d'un nouvel inventaire dans la liste d'inventaire
     */
    private void btnAddInvAction() {
        int row; // Ligne de l'inventaire ajoutée
        Inventaire inv; // Inventaire à ajouter

        // Aucun fichier en cours
        if (isNotOpen()) return;

        AddInventaire newInv = new AddInventaire();
        // L'inventaire retournée n'est pas valid
        if (!newInv.hasValidEntry()) return;

        inv = new Inventaire(newInv.getNom(), newInv.getDescription(), newInv.getCategorie(), newInv.getDate(), newInv.getNbSerie(), newInv.getPrix());
        ListeInventaire.add(inv);
        updateTabInv();

        row = tabInventaire.convertRowIndexToView(ListeInventaire.indexOf(inv));
        // Ligne pas dans le tableau (eg. il est filtrer)
        if (row == -1) return;

        tabInventaire.setRowSelectionInterval(row, row);
        tabInventaireSelectionChange();
    }

    /**
     * Modifie un inventaire dans la liste d'inventaire
     */
    private void modifyInventaire() {
        int row = tabInventaire.convertRowIndexToModel(tabInventaire.getSelectedRow()); // Ligne de l'inventaire à modifier
        Inventaire inv = ListeInventaire.get(row); // Inventaire à modifier

        ModifInventaire invModif = new ModifInventaire(inv);
        // La modification retournée n'est pas valide
        if (!invModif.hasValidEntry()) return;

        inv.modify(invModif.getNom(), invModif.getDescription(), invModif.getCategorie(), invModif.getDate(), invModif.getNbSerie(), invModif.getPrix());

        updateTabInv();
        tabInventaire.setRowSelectionInterval(row, row);
        tabInventaireSelectionChange();
    }

    /**
     * Supprime un inventaire dans la liste d'inventaire
     */
    private void btnDelInvAction() {
        int row; // Ligne de l'inventaire

        // Aucun fichier en cours
        if (isNotOpen()) return;

        row = tabInventaire.getSelectedRow();
        // Aucune ligne sélectionnée
        if (row == -1) return;

        ListeInventaire.remove(tabInventaire.convertRowIndexToModel(row));
        updateTabInv();

        tabInventaireSelectionChange();
    }

    // --- Entretien --- //

    /**
     * Ajout d'un entretien dans l'inventaire sélectionnée
     */
    private void btnAddEntAction() {
        Inventaire inv; // Inventaire à ajouter un entretien
        int row; // Ligne de l'entretien

        // Aucun fichier en cours
        if (isNotOpen()) return;

        row = tabInventaire.getSelectedRow();
        // Aucune ligne sélectionnée
        if (row == -1) return;

        inv = ListeInventaire.get(tabInventaire.convertRowIndexToModel(row));

        AddEntretien newEnt = new AddEntretien();
        // L'entretien retourné est invalid
        if (!newEnt.hasValidEntry()) return;

        inv.addEntretien(newEnt.getDate(), newEnt.getDescription());
        updateTabEnt(inv);
    }

    /**
     * Enlever un entretien dans l'inventaire sélectionnée
     */
    private void btnDelEntAction() {
        int rowInv; // Ligne de l'inventaire
        int rowEnt; // Ligne de l'entretien
        Inventaire inv; // Inventaire à enlever un entretien
        LocalDate key; // Clé de l'entretien

        // Aucun fichier en cours
        if (isNotOpen()) return;

        rowInv = tabInventaire.getSelectedRow();
        rowEnt = tabEntretien.getSelectedRow();
        // Ligne invalide
        if (rowInv == -1 || rowEnt == -1) return;

        inv = ListeInventaire.get(tabInventaire.convertRowIndexToModel(rowInv));
        key = (LocalDate) tabEntretien.getValueAt(rowEnt, 0);

        inv.delEntretien(key);
        updateTabEnt(inv);
    }

    // --- Méthodes --- //
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new Main();
    }

    /**
     * Sauvegarde de la liste d'inventaire dans un fichier .dat
     */
    private void saveData() {
        String filePath = fichier.getPath();
        if (!filePath.endsWith(".dat")) filePath = filePath.concat(".dat");

        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath));
            output.writeObject(ListeInventaire);
            output.close();
        } catch (IOException e) {
            Utils.sendErrorMessage(frame, "Erreur de sauvegarde");
        }
    }

    /**
     * Lecture d'un fichier .dat pour insérer dans la liste d'inventaire
     */
    @SuppressWarnings("unchecked")
    private void readData() {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(fichier.getPath()));
            ListeInventaire = (ArrayList<Inventaire>) input.readObject();
            input.close();
        } catch (ClassNotFoundException | IOException e) {
            Utils.sendErrorMessage(frame, "Erreur de lecture");
        }
    }

    /**
     * Exporter la liste d'inventaire dans un fichier .txt
     */
    private void saveExport(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
            for (Inventaire inv : ListeInventaire) {
                writer.write(inv.toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            Utils.sendErrorMessage(frame, "Erreur de sauvegarde");
        }
    }

    /**
     * Vérifier si le fichier est ouvert, et offrir la possibilité de sauvegarder
     *
     * @return true si il y a un erreur, sinon false
     */
    private boolean fileAlreadyLoaded() {
        if (!isLoaded) return false;

        int rep = JOptionPane.showConfirmDialog(frame, "Voulez-vous sauvegarder?", "Confirmation de sauvegarde", JOptionPane.YES_NO_CANCEL_OPTION);
        if (rep == JOptionPane.CANCEL_OPTION) return true;
        if (rep == JOptionPane.YES_OPTION) saveData();

        closeFile();
        return false;
    }

    /**
     * Mis à jour du tableau d'inventaire
     */
    private void updateTabInv() {
        mdlInventaire.setRowCount(0);
        for (Inventaire inventaire : ListeInventaire) mdlInventaire.addRow(inventaire.toObject());
    }

    /**
     * Mis à jour du tableau d'entretien
     * @param inv l'inventaire contenant les entretiens
     */
    private void updateTabEnt(Inventaire inv) {
        mdlEntretien.setRowCount(0);
        inv.getEntretiens().forEach((date, desc) -> mdlEntretien.addRow(new Object[]{date, desc}));
    }

    /**
     * Mis à jour de l'application entier
     */
    private void refreshApp() {
        updateTabInv();
        mdlEntretien.setRowCount(0);
    }

    /**
     * Mis à jour du titre, et définir au programme qu'il y a un fichier en cours
     */
    private void openedFile() {
        String fileName = fichier.getName();
        if (!fileName.endsWith(".dat")) fileName = fileName.concat(".dat");

        isLoaded = true;
        frame.setTitle(fileName + " " + title);
        refreshApp();
    }

    /**
     * Fermer le fichier en cours
     */
    private void closeFile() {
        isLoaded = false;
        ListeInventaire.clear();
        frame.setTitle(title);
        refreshApp();
    }

    /**
     * Fermeture de l'application
     */
    private void exitApp() {
        // Fichier en cours
        if (fileAlreadyLoaded()) return;

        int quitConfirm = JOptionPane.showConfirmDialog(frame, "Voulez-vous quitter?", "Confirmation de fermeture", JOptionPane.YES_NO_CANCEL_OPTION);
        if (quitConfirm != JOptionPane.YES_OPTION) return;

        System.exit(0);
    }

    /**
     * Vérifie si il n'a pas de fichier ouvert
     *
     * @return true si un fichier est ouvert, sinon false
     */
    private boolean isNotOpen() {
        if (!isLoaded) {
            Utils.sendErrorMessage(frame, "Aucun fichier d'ouvert.");
            return true;
        }
        return false;
    }
}
