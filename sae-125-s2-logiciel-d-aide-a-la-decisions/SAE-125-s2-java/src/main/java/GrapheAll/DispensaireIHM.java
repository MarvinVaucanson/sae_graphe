

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GrapheAll;

/**
 *
 * @author Baptiste
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class DispensaireIHM extends JFrame {
    private JPanel pane;
    private String resultat;
    private JPanel gsommet;
    protected ArrayList<String> selectedOptions;
    private Map<String, Point> sommetPoints;
    private JTextField textField;

    //constructeur
    public DispensaireIHM(Graphe g) {

        initComponents(g);
        setTitle("Dispensaire");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        int width = 500;
        int height = 500;
        
        Image icon = Toolkit.getDefaultToolkit().getImage("..\\ressource\\icone.png");  
        setIconImage(icon);  

        
        setPreferredSize(new Dimension(width, height));
        pack();

        setLocationRelativeTo(null);
    }

    //choisir la couleur du sommet en fonction de son type
    public Color choosecolor(String type){
        Color couleur = null;

        switch (type) {
            case "M":
                couleur = Color.RED;
                break;
            case "N":
                couleur = Color.BLUE;
                break;
            case "O":
                couleur = Color.ORANGE;
                break;
            default:
                System.out.println("Un des sommets a un type invalide");
                break;
        }
        return couleur;
    }

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

        JButton button1 = new JButton("Afficher");
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(button1);
        button1.setMaximumSize(new Dimension(200, button1.getPreferredSize().height));

        //afficher tous les voisins d'un sommet
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                resultat = t.parcourschaquedisp(input);
                gsommet.repaint();
                String resultat2 = resultat;
                resultat2 = resultat2.replace(";", "<br>");
                resultat2 = "<html>" + resultat2 + "</html>";
                resultat2 = resultat2.replace(",", " de type ");
                resultLabel.setText(resultat2);
            }
        });
        
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
                double angleIncrement = 2 * Math.PI / sommets.length;
                double angle = 0;

                // Dessine les sommets
                for (String sommet : sommets) {
                    String[] sommetInfo = sommet.split(",");
                    String sommetName = sommetInfo[0].trim();
                    String sommetType = sommetInfo[1].trim();

                    sommetPoints.put(sommetName, new Point(
                            (int) (centerX + Math.cos(angle) * (getWidth() / 2 - xOffset)),
                            (int) (centerY + Math.sin(angle) * (getHeight() / 2 - yOffset))
                    ));

                    g.setColor(choosecolor(sommetType));
                    int x = sommetPoints.get(sommetName).x;
                    int y = sommetPoints.get(sommetName).y;
                    g.fillOval(x - circleSize / 2, y - circleSize / 2, circleSize, circleSize);
                    g.drawString(sommetName, x - 5, y - 10);

                    angle += angleIncrement;
                }

                // Relie les sommets dans l'ordre de la chaîne de caractères résultat
                g.setColor(Color.BLACK);
                Point startPoint = null;

                for (String sommet : sommets) {
                    String[] sommetInfo = sommet.split(",");
                    String sommetName = sommetInfo[0].trim();
                    Point sommetPoint = sommetPoints.get(sommetName);

                    if (startPoint != null && sommetPoint != null) {
                        g.drawLine(startPoint.x, startPoint.y, sommetPoint.x, sommetPoint.y);
                    }

                    startPoint = sommetPoint;
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
        JMenuItem deuxvoisinItem = new JMenuItem("Afficher Deux Voisin");
        traitementMenu.add(deuxvoisinItem);
        JMenuItem dispItem = new JMenuItem("Afficher Chaque Dispensaire");
        traitementMenu.add(dispItem);
        dispItem.setEnabled(false);
        JMenuItem djikstraItem = new JMenuItem("Afficher Djikstra");
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

