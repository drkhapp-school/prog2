import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Main extends JFrame{
    public static final ArrayList<Inventaire> ListeInventaire = new ArrayList<>();
    public static boolean success = false;

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

    JPanel panNorth;
    JPanel panWest;
    JPanel panEast;
    JPanel panSouth;

    String[] colInventaire = {"Nom", "Catégorie", "Prix", "Date achat", "Description"};
    String[] colEntretien = {"Date", "Description"};

    Dimension dimTxf = new Dimension(150, 30);
    Dimension dimBtn = new Dimension(100, 25);
    Dimension dimNorth = new Dimension(1300, 40);
    Dimension dimEast = new Dimension(500, 700);
    Dimension dimWest = new Dimension(800, 700);
    Dimension dimSouth = new Dimension(1300, 50);

    // --- Constructeur --- //

    public Main() {
        frame = new JFrame("Jean-Philippe Miguel-Gagnon - 1927230");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1325, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        createMenuBar();
        createPanNorth();
        createPanWest();
        createPanEast();
        createPanSouth();
        frame.setVisible(true);
    }

    private void createMenuBar(){
        menuBar = new JMenuBar();

        // Menu "TP2"
        menuTP2 = new JMenu("TP2");
        miAbout = new JMenuItem("À propos");
        miAbout.addActionListener(e -> miAboutAction());
        miQuit = new JMenuItem("Quitter");
        miQuit.addActionListener(e -> miQuitAction());

        menuBar.add(menuTP2);
        menuTP2.add(miAbout);
        menuTP2.addSeparator();
        menuTP2.add(miQuit);

        // Menu "Fichier"
        menuFichier = new JMenu("Fichier");
        miNew = new JMenuItem("Nouveau...");
        miNew.addActionListener(e -> miNewAction());
        miOpen = new JMenuItem("Ouvrir...");
        miOpen.addActionListener(e -> miOpenAction());
        miClose = new JMenuItem("Fermer");
        miClose.addActionListener(e -> miCloseAction());
        miSave = new JMenuItem("Enregistrer");
        miSave.addActionListener(e -> miSaveAction());
        miSaveAs = new JMenuItem("Enregistrer sous...");
        miSaveAs.addActionListener(e -> miSaveAsAction());
        miExport = new JMenuItem("Exporter...");
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

    private void createPanNorth(){
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

    private void createPanEast(){
        panEast = new JPanel();
        panEast.setLayout(new FlowLayout(FlowLayout.LEFT));
        panEast.setPreferredSize(dimEast);

        // Tableau d'entretien
        mdlEntretien = new DefaultTableModel(colEntretien, 1) {
            @Override
            public boolean isCellEditable(int row, int column ) {
                return false;
            }
        };

        tabEntretien = new JTable(mdlEntretien);
        tabEntretien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabEntretien.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                tabEntretienSelectionChange();
            }
        });

        tabEntretien.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tabEntretienSelectionChange();
                }
            }
        });

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

    private void createPanWest(){
        panWest = new JPanel();
        panWest.setLayout(new FlowLayout(FlowLayout.LEFT));
        panWest.setPreferredSize(dimWest);

        // Tableau d'inventaire
        mdlInventaire = new DefaultTableModel(colInventaire, 0) {
            @Override
            public boolean isCellEditable(int row, int column ) {
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
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tabInventaireSelectionChange();
                }
            }
        });

        tabInventaire.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
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

    private void createPanSouth(){
        panSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panSouth.setPreferredSize(dimSouth);

        btnQuit = new JButton("Quitter");
        btnQuit.setPreferredSize(dimBtn);
        btnQuit.addActionListener(e -> btnQuitAction());

        panSouth.add(btnQuit);

        frame.add(panSouth, BorderLayout.SOUTH);
    }

    // --- Bar de Menu: TP2 --- //

    private void miAboutAction() {
        JOptionPane.showMessageDialog(frame,"Travail Pratique 2\nJean-Philippe Miguel-Gagnon - 1927230\nSession H2021\nDans le cadre du cours 420-C27", "À propos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void miQuitAction() {
    }

    private void miNewAction() {
    }

    // --- Bar de Menu: Fichier --- //

    private void miOpenAction() {
    }

    private void miCloseAction() {
    }

    private void miSaveAction() {
    }

    private void miSaveAsAction() {
    }

    private void miExportAction() {
    }


    // --- Action Listeners --- //

    private void tabInventaireSelectionChange() {
    }

    private void tabEntretienSelectionChange() {
    }

    private void btnFilterAction() {
    }

    private void btnAddInvAction() {
        new AddInventaire();
        if (success) {
            updateInventaire();
            tabInventaire.setRowSelectionInterval(mdlInventaire.getRowCount() - 1, mdlInventaire.getRowCount() - 1);
            success = false;
        }
    }

    private void modifyInventaire() {
        new ModifInventaire(tabInventaire.getSelectedRow());
        updateInventaire();
    }

    private void btnDelInvAction() {
        int row = tabInventaire.getSelectedRow();
        if (row == -1) return;

        ListeInventaire.remove(row);
        updateInventaire();
    }

    private void btnAddEntAction() {
        new AddEntretien();
    }

    private void btnDelEntAction() {
    }

    private void btnQuitAction() {
    }

    // --- Méthodes --- //
    public static void main(String[] args) {
        new Main();
    }

    private void updateInventaire() {
        mdlInventaire.setRowCount(0);
        for (Inventaire inventaire : ListeInventaire) {
            mdlInventaire.addRow(inventaire.toObject());
        }
    }
}
