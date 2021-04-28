import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame{
    JFrame frame;
    JMenuBar menuBar;
    JMenu menuTP2, menuFichier;
    JMenuItem miAPropos, miQuitter, miNouveau, miOuvrir, miFermer, miEnregistrer, miEnregistrerSous, miExporter;
    JTextField txfFiltre;
    JButton btnFiltrer;
    JTable tabInventaire;
    DefaultTableModel mdlInventaire;
    JTable tabEntretien;
    DefaultTableModel mdlEntretien;
    JButton btnPlusInv;
    JButton btnMoinsInv;
    JButton btnPlusEnt;
    JButton btnMoinsEnt;
    JButton btnQuitter;

    JPanel panHaut;
    JPanel panGauche;
    JPanel panDroite;
    JPanel panBas;

    String[] colInventaire = {"Nom", "Catégorie", "Prix", "Date achat", "Description"};
    String[] colEntretien = {"Date", "Description"};

    Dimension dimTxf = new Dimension(150, 30);
    Dimension dimBtn = new Dimension(100, 25);
    Dimension dimHaut = new Dimension(1300, 40);
    Dimension dimDroite = new Dimension(500, 700);
    Dimension dimGauche = new Dimension(800, 700);
    Dimension dimBas = new Dimension(1300, 50);

//-------------------------------------------- Constructeur des composants des JPanels --------------------------------------------//

    public Main() {
        frame = new JFrame("Marc-Antoine Gagnon 2026821");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1325, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());


        panHaut = new JPanel();
        panHaut.setLayout(new FlowLayout(FlowLayout.LEFT));
        panHaut.setPreferredSize(dimHaut);
        panDroite = new JPanel();
        panDroite.setLayout(new FlowLayout(FlowLayout.LEFT));
        panDroite.setPreferredSize(dimDroite);
        panGauche = new JPanel();
        panGauche.setLayout(new FlowLayout(FlowLayout.LEFT));
        panGauche.setPreferredSize(dimGauche);
        panBas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panBas.setPreferredSize(dimBas);

        menuBar = new JMenuBar();

        menuTP2 = new JMenu("TP2");
        miAPropos = new JMenuItem("À propos");
        miAPropos.addActionListener(e -> miAProposAction());
        miQuitter = new JMenuItem("Quitter");
        miQuitter.addActionListener(e -> miQuitterAction());

        menuBar.add(menuTP2);
        menuTP2.add(miAPropos);
        menuTP2.addSeparator();
        menuTP2.add(miQuitter);

        menuFichier = new JMenu("Fichier");
        miNouveau = new JMenuItem("Nouveau...");
        miNouveau.addActionListener(e -> miNouveauAction());
        miOuvrir = new JMenuItem("Ouvrir...");
        miOuvrir.addActionListener(e -> miOuvrirAction());
        miFermer = new JMenuItem("Fermer");
        miFermer.addActionListener(e -> miFermerAction());
        miEnregistrer = new JMenuItem("Enregistrer");
        miEnregistrer.addActionListener(e -> miEnregistrerAction());
        miEnregistrerSous = new JMenuItem("Enregistrer sous...");
        miEnregistrerSous.addActionListener(e -> miEnregistrersousAction());
        miExporter = new JMenuItem("Exporter...");
        miExporter.addActionListener(e -> miExporterAction());

        menuBar.add(menuFichier);
        menuFichier.add(miNouveau);
        menuFichier.add(miOuvrir);
        menuFichier.add(miFermer);
        menuFichier.addSeparator();
        menuFichier.add(miEnregistrer);
        menuFichier.add(miEnregistrerSous);
        menuFichier.addSeparator();
        menuFichier.add(miExporter);

        txfFiltre = new JTextField();
        txfFiltre.setPreferredSize(dimTxf);

        btnFiltrer = new JButton("Filtrer");
        btnFiltrer.setPreferredSize(dimBtn);
        btnFiltrer.addActionListener(e -> btnFiltrerAction());


        mdlInventaire = new DefaultTableModel(colInventaire, 1) {
            @Override
            public boolean isCellEditable(int row, int column ) {
                return false;
            }
        };

        tabInventaire = new JTable(mdlInventaire);
        tabInventaire.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabInventaire.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    tabInventaireSelectionChange();
                }
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
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    ModificationInventaire modifInv = new ModificationInventaire();
                }
            }
        });

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
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    tabEntretienSelectionChange();
                }
            }
        });

        JScrollPane scrollInv = new JScrollPane(tabInventaire);
        scrollInv.setPreferredSize(new Dimension(790, 600));

        JScrollPane scrollEnt = new JScrollPane(tabEntretien);
        scrollEnt.setPreferredSize(new Dimension(490, 600));


        btnPlusInv = new JButton("+");
        btnPlusInv.setPreferredSize(dimBtn);
        btnPlusInv.addActionListener(e -> btnPlusInvAction());

        btnMoinsInv = new JButton("-");
        btnMoinsInv.setPreferredSize(dimBtn);
        btnMoinsInv.addActionListener(e -> btnMoinsInvAction());

        btnPlusEnt = new JButton("+");
        btnPlusEnt.setPreferredSize(dimBtn);
        btnPlusEnt.addActionListener(e -> btnPlusEntAction());

        btnMoinsEnt = new JButton("-");
        btnMoinsEnt.setPreferredSize(dimBtn);
        btnMoinsEnt.addActionListener(e -> btnMoinsEntAction());

        btnQuitter = new JButton("Quitter");
        btnQuitter.setPreferredSize(dimBtn);
        btnQuitter.addActionListener(e -> btnQuitterAction());


        panHaut.add(txfFiltre);
        panHaut.add(btnFiltrer);

        panGauche.add(scrollInv);
        panGauche.add(btnPlusInv);
        panGauche.add(btnMoinsInv);

        panDroite.add(scrollEnt);
        panDroite.add(btnPlusEnt);
        panDroite.add(btnMoinsEnt);

        panBas.add(btnQuitter);

        frame.setJMenuBar(menuBar);
        frame.add(panHaut, BorderLayout.NORTH);
        frame.add(panGauche, BorderLayout.WEST);
        frame.add(panDroite, BorderLayout.EAST);
        frame.add(panBas, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void miOuvrirAction() {
    }

    private void miFermerAction() {
    }

    private void miEnregistrersousAction() {
    }

    private void miExporterAction() {
    }

    private void btnFiltrerAction() {
    }

    private void miEnregistrerAction() {
    }

    private void miNouveauAction() {
    }

    private void tabInventaireSelectionChange() {
    }

    private void tabEntretienSelectionChange() {
    }

    private void btnMoinsInvAction() {
    }

    private void btnPlusEntAction() {
    }

    private void btnMoinsEntAction() {
    }

    private void btnQuitterAction() {
    }

    private void miQuitterAction() {
    }

    private void btnPlusInvAction() {
        new AjoutInventaire();
    }

    private void miAProposAction() {
    }

    private void btnAboutAction() {
        JOptionPane.showMessageDialog(frame,"Travail Pratique 2\nJean-Philippe Miguel-Gagnon + 1927230\nSession H2021\nDans le cadre du cours 420-C27", "À propos", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Main();
    }
}
