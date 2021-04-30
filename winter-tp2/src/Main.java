import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main extends JFrame {
    ArrayList<Inventaire> ListeInventaire = new ArrayList<>();
    boolean isLoaded = false;

    DefaultTableModel mdlInventaire;
    DefaultTableModel mdlEntretien;

    JFrame frame;
    JMenuBar menuBar;
    JMenu menuTP2, menuFichier;
    JMenuItem miAbout, miQuit, miNew, miOpen, miClose, miSave, miSaveAs, miExport;
    JTextField txfFilter;
    JButton btnFilter;
    JTable tabInventaire;
    JTable tabEntretien;
    JButton btnAddInv;
    JButton btnDelInv;
    JButton btnAddEnt;
    JButton btnDelEnt;
    JButton btnQuit;

    JFileChooser fc;

    JPanel panNorth;
    JPanel panWest;
    JPanel panEast;
    JPanel panSouth;

    String[] colInventaire = {"Nom", "Catégorie", "Prix", "Date achat", "Description"};
    String[] colEntretien = {"Date", "Description"};

    String filePath;
    String title = "Jean-Philippe Miguel-Gagnon";

    Dimension dimTxf = new Dimension(150, 30);
    Dimension dimBtn = new Dimension(100, 25);
    Dimension dimNorth = new Dimension(1300, 40);
    Dimension dimEast = new Dimension(500, 700);
    Dimension dimWest = new Dimension(800, 700);
    Dimension dimSouth = new Dimension(1300, 50);

    // --- Constructeur --- //

    public Main() {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1325, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    miQuitAction();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        createMenuBar();
        createPanNorth();
        createPanWest();
        createPanEast();
        createPanSouth();
        frame.setVisible(true);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        // Menu "TP2"
        menuTP2 = new JMenu("TP2");
        miAbout = new JMenuItem("À propos");
        miAbout.addActionListener(e -> miAboutAction());
        miAbout.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));

        miQuit = new JMenuItem("Quitter");
        miQuit.addActionListener(e -> {
            try {
                miQuitAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        miQuit.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));

        menuBar.add(menuTP2);
        menuTP2.add(miAbout);
        menuTP2.addSeparator();
        menuTP2.add(miQuit);

        // Menu "Fichier"
        menuFichier = new JMenu("Fichier");
        miNew = new JMenuItem("Nouveau...");
        miNew.addActionListener(e -> {
            try {
                miNewAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        miNew.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));

        miOpen = new JMenuItem("Ouvrir...");
        miOpen.addActionListener(e -> {
            try {
                miOpenAction();
            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        });
        miOpen.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));

        miClose = new JMenuItem("Fermer");
        miClose.addActionListener(e -> {
            try {
                miCloseAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        miClose.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_DOWN_MASK));

        miSave = new JMenuItem("Enregistrer");
        miSave.addActionListener(e -> {
            try {
                miSaveAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        miSave.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));

        miSaveAs = new JMenuItem("Enregistrer sous...");
        miSaveAs.addActionListener(e -> {
            try {
                miSaveAsAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        miSaveAs.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));

        miExport = new JMenuItem("Exporter...");
        miExport.addActionListener(e -> {
            try {
                miExportAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        miExport.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));

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

    private void createPanWest() {
        panWest = new JPanel();
        panWest.setLayout(new FlowLayout(FlowLayout.LEFT));
        panWest.setPreferredSize(dimWest);

        // Tableau d'inventaire
        mdlInventaire = new DefaultTableModel(colInventaire, 0) {
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

        JScrollPane scrollInv = new JScrollPane(tabInventaire);
        scrollInv.setPreferredSize(new Dimension(790, 600));

        // Bouton d'inventaire
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

    private void createPanSouth() {
        panSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panSouth.setPreferredSize(dimSouth);

        btnQuit = new JButton("Quitter");
        btnQuit.setPreferredSize(dimBtn);
        btnQuit.addActionListener(e -> {
            try {
                btnQuitAction();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        panSouth.add(btnQuit);

        frame.add(panSouth, BorderLayout.SOUTH);
    }

    // --- Bar de Menu: TP2 --- //

    private void miAboutAction() {
        JOptionPane.showMessageDialog(frame, "Travail Pratique 2\nJean-Philippe Miguel-Gagnon - 1927230\nSession H2021\nDans le cadre du cours 420-C27", "À propos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void miQuitAction() throws IOException {
        int quitConfirm = JOptionPane.showConfirmDialog(frame, "Voulez-vous quitter?", "Confirmation de fermeture", JOptionPane.YES_NO_OPTION);
        if (quitConfirm == JOptionPane.YES_OPTION) {
            if (isLoaded) {
                int rep = saveConfirm();
                if (rep == JOptionPane.YES_OPTION) {
                    miSaveAction();
                    isLoaded = false;
                } else if (rep == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            System.exit(0);
        }
    }

    // --- Bar de Menu: Fichier --- //

    private void miOpenAction() throws IOException, ClassNotFoundException {
        if (isLoaded) {
            int rep = saveConfirm();
            if (rep == JOptionPane.YES_OPTION) {
                miSaveAction();
            } else if (rep == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        fc = new JFileChooser();

        fc.setDialogTitle("Ouverture de fichier");
        FileNameExtensionFilter fcFilter = new FileNameExtensionFilter("*.dat", "dat");
        fc.setFileFilter(fcFilter);

        int rep = fc.showOpenDialog(frame);
        if (rep == JFileChooser.APPROVE_OPTION) {
            File fichier = fc.getSelectedFile();
            filePath = fichier.getPath();
            try {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath));
                ListeInventaire = (ArrayList<Inventaire>) input.readObject();
                input.close();
            } catch (FileNotFoundException ignored) {
            }

            resetFrame();

            isLoaded = true;
            frame.setTitle(fichier.getName() + " " + title);
        }
    }

    private void miNewAction() throws IOException {
        if (isLoaded) {
            int rep = saveConfirm();
            if (rep == JOptionPane.YES_OPTION) {
                miSaveAction();
                isLoaded = false;
                miCloseAction();
            } else if (rep == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        fc = new JFileChooser();

        fc.setDialogTitle("Nouveau inventaire");
        FileNameExtensionFilter fcFilter = new FileNameExtensionFilter("*.dat", "dat");
        fc.setFileFilter(fcFilter);

        int rep = fc.showSaveDialog(frame);
        if (rep == JFileChooser.APPROVE_OPTION) {
            File fichier = fc.getSelectedFile();
            String fileName = fichier.getName();
            filePath = fichier.getPath();

            if (!filePath.endsWith("dat"))
                filePath = filePath.concat(".dat");

            if (!fileName.endsWith("dat"))
                fileName = fileName.concat(".dat");

            writeFileObject(filePath);

            isLoaded = true;
            frame.setTitle(fileName + " " + title);
        }
    }

    private void miCloseAction() throws IOException {
        if (isLoaded) {
            int rep = saveConfirm();
            if (rep == JOptionPane.YES_OPTION) {
                miSaveAction();
                isLoaded = false;
            } else if (rep == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        ListeInventaire.clear();
        resetFrame();
        isLoaded = false;

        frame.setTitle(title);
    }

    private void miSaveAction() throws IOException {
        if (!isLoaded) return;
        writeFileObject(filePath);
    }


    private void miSaveAsAction() throws IOException {
        if (!isLoaded) return;
        fc = new JFileChooser();

        fc.setDialogTitle("Enregistrement inventaire");
        FileNameExtensionFilter fcFilter = new FileNameExtensionFilter("*.dat", "dat");
        fc.setFileFilter(fcFilter);

        int rep = fc.showSaveDialog(frame);
        if (rep == JFileChooser.APPROVE_OPTION) {
            File fichier = fc.getSelectedFile();
            String fileName = fichier.getName();
            filePath = fichier.getPath();

            if (!filePath.endsWith("dat"))
                filePath = filePath.concat(".dat");

            if (!fileName.endsWith("dat"))
                fileName = fileName.concat(".dat");

            writeFileObject(filePath);
            frame.setTitle(fileName + " " + title);
        }
    }

    private void miExportAction() throws IOException {
        if (!isLoaded) return;

        fc = new JFileChooser();

        fc.setDialogTitle("Enregistrement inventaire");
        FileNameExtensionFilter fcFilter = new FileNameExtensionFilter("*.dat", "dat");
        fc.setFileFilter(fcFilter);

        int rep = fc.showSaveDialog(frame);
        if (rep == JFileChooser.APPROVE_OPTION) {
            File fichier = fc.getSelectedFile();
            String output = fichier.getPath();

            if (!output.endsWith("txt"))
                output = output.concat(".txt");

            writeExport(output);
        }
    }

    private void writeFileObject(String fileName) throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName));
        output.writeObject(ListeInventaire);
        output.close();
    }

    private void writeExport(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));

        for (Inventaire inv : ListeInventaire) {
            writer.write(inv.toString());
            writer.newLine();
        }

        writer.close();
    }


    // --- Action Listeners --- //

    private void tabInventaireSelectionChange() {
        int row = tabInventaire.getSelectedRow();
        if (row == -1) return;

        updateEntretien(ListeInventaire.get(row));
    }

    private void btnFilterAction() {
    }

    private void btnQuitAction() throws IOException {
        miQuitAction();
    }

    // --- Inventaire --- //
    private void btnAddInvAction() {
        if (!isLoaded) return;

        int currentSize = ListeInventaire.size();

        new AddInventaire(ListeInventaire);
        if (currentSize != ListeInventaire.size()) {
            updateInventaire();
            tabInventaire.setRowSelectionInterval(mdlInventaire.getRowCount() - 1, mdlInventaire.getRowCount() - 1);
            tabInventaireSelectionChange();
        }
    }

    private void modifyInventaire() {
        Inventaire inv = ListeInventaire.get(tabInventaire.getSelectedRow());
        int row = ListeInventaire.indexOf(inv);

        new ModifInventaire(inv);
        updateInventaire();
        tabInventaire.setRowSelectionInterval(row, row);
        tabInventaireSelectionChange();
    }

    private void btnDelInvAction() {
        if (!isLoaded) return;

        int row = tabInventaire.getSelectedRow();
        if (row == -1) return;

        ListeInventaire.remove(row);
        tabInventaireSelectionChange();
        updateInventaire();
    }

    // --- Entretien --- //
    private void btnAddEntAction() {
        if (!isLoaded) return;

        int row = tabInventaire.getSelectedRow();
        if (row == -1) return;

        Inventaire inv = ListeInventaire.get(row);

        new AddEntretien(inv);
        updateEntretien(inv);
    }

    private void btnDelEntAction() {
        if (!isLoaded) return;

        int rowInv = tabInventaire.getSelectedRow();
        int rowEnt = tabEntretien.getSelectedRow();
        if (rowInv == -1 || rowEnt == -1) return;

        Inventaire inv = ListeInventaire.get(rowInv);
        LocalDate key = (LocalDate) tabEntretien.getValueAt(rowEnt, 0);

        inv.delEntretien(key);
        updateEntretien(inv);
    }

    // --- Méthodes --- //
    public static void main(String[] args) {
        new Main();
    }

    private void updateInventaire() {
        mdlInventaire.setRowCount(0);
        for (Inventaire inventaire : ListeInventaire) mdlInventaire.addRow(inventaire.toObject());
    }

    private void updateEntretien(Inventaire inv) {
        mdlEntretien.setRowCount(0);
        inv.getEntretiens().forEach((date, desc) -> mdlEntretien.addRow(new Object[]{date, desc}));
    }

    private int saveConfirm() {
        return JOptionPane.showConfirmDialog(frame, "Voulez-vous sauvegarder?", "Confirmation de sauvegarde", JOptionPane.YES_NO_CANCEL_OPTION);
    }

    private void resetFrame() {
        updateInventaire();
        mdlEntretien.setRowCount(0);
    }
}
