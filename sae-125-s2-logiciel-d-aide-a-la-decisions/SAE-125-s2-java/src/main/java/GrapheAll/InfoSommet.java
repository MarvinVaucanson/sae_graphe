package GrapheAll;

import javax.swing.*;
import java.awt.*;

public class InfoSommet extends JFrame {

    private JPanel pane;
    Graphe graphe = new Graphe();
    private JPanel infoPanel;
    private JPanel gsommet;
    private JLabel nomLabel;
    private JLabel typeLabel;
    private String voisinsString = "Voisins : ";
    private JLabel voisinsLabel;
    private JLabel dessinLabel;


    public InfoSommet(Graphe g, String sommet) {

        initComponents(g, sommet);
        setTitle("Informations sur l'arrete");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        Image icon = Toolkit.getDefaultToolkit().getImage("..\\ressource\\icone.png");
        setIconImage(icon);
        int width = 400;
        int height = 250;
        setPreferredSize(new Dimension(width, height));
        pack();

        setLocationRelativeTo(null);
    }

    public void initComponents(Graphe g, String sommet) {

        Graphe.MaillonGraphePrin tmp = g.getMaillonGraphePrin(sommet);
        Graphe.MaillonGrapheSec arrete = tmp.getlVois();

        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout()); // Utilisation de GridBagLayout pour le centrage
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER; // Alignement centré
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement

        Font labelFont = new Font("Arial", Font.BOLD, 16); // Police en gras et plus grande taille

        nomLabel = new JLabel("Sommet : " + sommet);
        nomLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(nomLabel, gbc);

        typeLabel = new JLabel("Type: " + tmp.getType());
        typeLabel.setFont(labelFont);
        gbc.gridy = 1;
        infoPanel.add(typeLabel, gbc);

        voisinsString += arrete.getDest();
        arrete = arrete.getSuiv();
        while (arrete != null) {
            voisinsString += ", ";
            voisinsString += (arrete.getDest());
            arrete = arrete.getSuiv();
        }
        voisinsLabel = new JLabel(voisinsString);
        voisinsLabel.setFont(labelFont);
        gbc.gridy = 2;
        infoPanel.add(voisinsLabel, gbc);

        gsommet = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int x = 180, y = 30;
                g.setColor(graphe.choosecolor(tmp.getType()));
                g.fillOval(x, y, 25, 25);
                g.drawString(sommet, x - 5, y - 5);
            }
        };

        gsommet.setBackground(Color.WHITE);
        gsommet.setPreferredSize(new Dimension(200, 100));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(gsommet, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }




}