package GrapheAll;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.*;

/**
 *
 * @author Baptiste
 */
public class VoisinIHM extends JFrame {

    private JPanel pane;
    private JTextField textField;
    private String resultat;
    private JPanel gsommet;
    private Map<String, Point> sommetPoints;


    //constructeur
    public VoisinIHM(Graphe g) {

        initComponents(g);
        setTitle("Afficher Voisin");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        Image icon = Toolkit.getDefaultToolkit().getImage("..\\ressource\\icone.png");
        setIconImage(icon);
        int width = 500;
        int height = 500;
        setPreferredSize(new Dimension(width, height));
        pack();

        setLocationRelativeTo(null);
    }

    //tout ce qu'il y a dans la fenetre
    public void initComponents(Graphe graphe) {
        GrapheTraitement t = new GrapheTraitement(graphe);
        pane = new JPanel(new BorderLayout());
        getContentPane().add(pane);

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setPreferredSize(new Dimension(200, getHeight()));
        sideBar.setMinimumSize(new Dimension(200, getHeight()));
        sideBar.setMaximumSize(new Dimension(200, getHeight()));
        sideBar.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        pane.add(sideBar, BorderLayout.WEST);

        textField = new JTextField();
        textField.setMaximumSize(new Dimension(180, 30));
        sideBar.add(textField);

        JLabel resultLabel = new JLabel();

        JButton button1 = new JButton("Afficher tout");
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(button1);
        button1.setMaximumSize(new Dimension(200, button1.getPreferredSize().height));

        //afficher tous les voisins d'un sommet
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                resultat = t.affichervoisinonlytype(input);
                gsommet.repaint();
                String resultat2 = resultat;
                resultat2 = resultat2.replace(";", "<br>");
                resultat2 = "<html>" + resultat2 + "</html>";
                resultat2 = resultat2.replace(",", " de type ");
                resultLabel.setText(resultat2);
            }
        });

        JButton button2 = new JButton("Afficher seulement type");
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(button2);
        button2.setMaximumSize(new Dimension(200, button2.getPreferredSize().height));

        //Afficher tous les voisins du meme type que le sommmet
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                resultat = t.unvoisintype(input);
                gsommet.repaint();
                String resultat2 = resultat;
                resultat2 = resultat2.replace(";", "<br>");
                resultat2 = "<html>" + resultat2 + "</html>";
                resultat2 = resultat2.replace(",", " de  type ");
                resultLabel.setText(resultat2);
            }
        });


        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(resultLabel);

        sommetPoints = new HashMap<>();

        gsommet = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (resultat == null) {
                    return;
                }

                String[] sommets = resultat.split(";");
                int circleSize = 20;

                int xOffset = 50;
                int yOffset = 50;
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                double angle = 0;
                double angleIncrement = 2 * Math.PI / 9;

                // Dessine les sommets
                for (int i = 0; i < sommets.length; i++) {
                    String[] sommetInfo = sommets[i].split(",");

                    String sommetName = sommetInfo[0].trim();
                    String sommetType = sommetInfo[1].trim();

                    sommetPoints.put(sommetName, new Point(
                            (int) (centerX + Math.cos(angle) * (getWidth() / 2 - xOffset)),
                            (int) (centerY + Math.sin(angle) * (getHeight() / 2 - yOffset))
                    ));

                    g.setColor(graphe.choosecolor(sommetType));
                    int x = sommetPoints.get(sommetName).x;
                    int y = sommetPoints.get(sommetName).y;
                    g.fillOval(x - circleSize / 2, y - circleSize / 2, circleSize, circleSize);
                    g.drawString(sommetName, x - 5, y - 10);

                    angle += angleIncrement;
                }

                //Dessine les arretes
                g.setColor(Color.BLACK);
                Point s1Point = sommetPoints.get(sommets[0].split(",")[0].trim());

                for (int i = 1; i < sommets.length; i++) {
                    String[] sommetInfo = sommets[i].split(",");
                    String sommetName = sommetInfo[0].trim();
                    Point sommetPoint = sommetPoints.get(sommetName);

                    if (sommetPoint != null) {
                        g.drawLine(s1Point.x, s1Point.y, sommetPoint.x, sommetPoint.y);
                    }
                }
            }
        };
        pane.add(gsommet, BorderLayout.CENTER);

        gsommet.setBackground(Color.WHITE);
        gsommet.setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");

        menuBar.add(fileMenu);
        fileMenu.setEnabled(false);
        JMenuItem loadItem = new JMenuItem("Open File");
        fileMenu.add(loadItem);

        JMenu formeMenu = new JMenu("Forme Affichage");
        formeMenu.setEnabled(false);
        menuBar.add(formeMenu);
        JMenuItem cerclesItem = new JMenuItem("Cercles");
        formeMenu.add(cerclesItem);
        JMenuItem carreItem = new JMenuItem("Carré");
        formeMenu.add(carreItem);

        JMenu traitementMenu = new JMenu("Traitement");
        menuBar.add(traitementMenu);
        JMenuItem voisinItem = new JMenuItem("Afficher Voisin");
        traitementMenu.add(voisinItem);
        voisinItem.setEnabled(false);
        JMenuItem deuxvoisinItem = new JMenuItem("Afficher Deux Voisin");
        traitementMenu.add(deuxvoisinItem);
        JMenuItem dispItem = new JMenuItem("Afficher Chaque Dispensaire");
        traitementMenu.add(dispItem);
        JMenuItem djikstraItem = new JMenuItem("Afficher Dijkstra");
        traitementMenu.add(djikstraItem);

        voisinItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                VoisinIHM fen2 = new VoisinIHM(graphe);
                fen2.pack();
                fen2.setVisible(true);
            }
        });

        deuxvoisinItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                DeuxVoisinIHM fen3 = new DeuxVoisinIHM(graphe);
                fen3.pack();
                fen3.setVisible(true);
            }
        });

        dispItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                DispensaireIHM fen4 = new DispensaireIHM(graphe);
                fen4.pack();
                fen4.setVisible(true);
            }
        });

        djikstraItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                DjikstraIHM fen5 = new DjikstraIHM(graphe);
                fen5.pack();
                fen5.setVisible(true);
            }
        });
    }
}
