package GrapheAll;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;


public class GrapheIHM extends JFrame {

    private JPanel pane;
    private JPanel gsommet;
    private String styleGraphe = "circle";
    private Point startPoint;
    public Graphe.MaillonGraphePrin draggedSommet;
    public HashMap<String, Point> sommetPoints = new HashMap<>();
    public Graphe graphe;
    public Dataload d = new Dataload();
    private String sommetClic1 = "";
    private String sommetClic2 = "";

    //constructeur
    public GrapheIHM(Graphe newgraphe, int size) {
        graphe = newgraphe;
        initComponents(graphe, size);
        setTitle("Graphe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        Image icon = Toolkit.getDefaultToolkit().getImage("..\\ressource\\icone.png");
        setIconImage(icon);
        int width = 900;
        int height = 700;
        setPreferredSize(new Dimension(width, height));
        pack();

        setLocationRelativeTo(null);
    }

    public void initComponents(Graphe newgraphe, int size) {
        graphe = newgraphe;
        int circleSize = 20;

        //GrapheTraitement t = new GrapheTraitement(graphe);
        pane = new JPanel(new BorderLayout());
        getContentPane().add(pane);

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setPreferredSize(new Dimension(300, getHeight()));
        sideBar.setMinimumSize(new Dimension(300, getHeight()));
        sideBar.setMaximumSize(new Dimension(300, getHeight()));
        sideBar.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        pane.add(sideBar, BorderLayout.WEST);
        
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Color labelTextColor = new Color(30, 30, 30);

        JLabel info1label = createLabel("<html> Cliquez deux fois sur un sommet<br> pour afficher les informations </html>",labelFont,labelTextColor);
        JLabel info2label = createLabel("<html> Cliquez sur deux sommets pour afficher les informations </html>",labelFont,labelTextColor);
        JLabel info3label = createLabel("<html> Restez appuyé sur un sommet <br>pour le déplacer </html>",labelFont,labelTextColor);
        
        sideBar.add(Box.createRigidArea(new Dimension(0, 30)));
        sideBar.add(info1label);
        sideBar.add(Box.createRigidArea(new Dimension(0, 30)));
        sideBar.add(info2label);
        sideBar.add(Box.createRigidArea(new Dimension(0, 30)));
        sideBar.add(info3label);

        gsommet = new JPanel() {

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (styleGraphe.equals("carre")) {

                    Graphe.MaillonGraphePrin tmp = graphe.premier;
                    int circleSize = 20;
                    int xOffset = 50;
                    int yOffset = 50;
                    int x = xOffset;
                    int y = yOffset;

                    while (tmp != null) {
                        sommetPoints.put(tmp.getName(), new Point(x, y));
                        String typeoption = tmp.type;
                        g.setColor(graphe.choosecolor(typeoption));
                        g.fillOval(x - circleSize / 2, y - circleSize / 2, circleSize, circleSize);
                        g.drawString(tmp.getName(), x - 5, y - 10);

                        x += 100;
                        if (x >= getWidth() - xOffset) {
                            x = 50;
                            y += 100;
                        }
                        tmp = tmp.suiv;
                    }

                    g.setColor(Color.BLACK);
                    for (String arete : graphe.getedgename().keySet()) {
                        String[] sommets = graphe.getedgename().get(arete);
                        Point point1 = sommetPoints.get(sommets[0]);
                        Point point2 = sommetPoints.get(sommets[1]);
                        if (point1 != null && point2 != null) {
                            g.drawLine(point1.x, point1.y, point2.x, point2.y);
                        }
                    }
                }
                else if(styleGraphe.equals("circle")) {

                    Graphe.MaillonGraphePrin tmp = graphe.premier;

                    int xOffset = 50;
                    int yOffset = 50;
                    int centerX = getWidth() / 2;
                    int centerY = getHeight() / 2;
                    double angle = 0;
                    double angleIncrement = 2 * Math.PI / size;

                    while (tmp != null) {
                        sommetPoints.put(tmp.getName(), new Point(
                                (int) (centerX + Math.cos(angle) * (getWidth() / 2 - xOffset)),
                                (int) (centerY + Math.sin(angle) * (getHeight() / 2 - yOffset))
                        ));

                        String typeoption = tmp.type;
                        g.setColor(graphe.choosecolor(typeoption));
                        int x = sommetPoints.get(tmp.getName()).x;
                        int y = sommetPoints.get(tmp.getName()).y;
                        g.fillOval(x - circleSize / 2, y - circleSize / 2, circleSize, circleSize);
                        g.drawString(tmp.getName(), x - 5, y - 10);

                        angle += angleIncrement;
                        tmp = tmp.suiv;
                    }

                    g.setColor(Color.BLACK);
                    for (String arete : graphe.getedgename().keySet()) {
                        String[] sommets = graphe.getedgename().get(arete);
                        Point point1 = sommetPoints.get(sommets[0]);
                        Point point2 = sommetPoints.get(sommets[1]);
                        if (point1 != null && point2 != null) {
                            g.drawLine(point1.x, point1.y, point2.x, point2.y);
                        }
                    }
                }
                else {
                    for (String sommet : sommetPoints.keySet()) {
                        Point sommetPoint = sommetPoints.get(sommet);
                        String typeoption = graphe.getMaillonGraphePrin(sommet).getType();
                        g.setColor(graphe.choosecolor(typeoption));
                        g.fillOval(sommetPoint.x - circleSize/2, sommetPoint.y - circleSize/2, circleSize, circleSize);

                        g.drawString(sommet, sommetPoint.x - 5, sommetPoint.y - 10);
                    }

                    //dessine les arretes

                    g.setColor(Color.BLACK);
                    for (String arete : graphe.getedgename().keySet()) {
                        String[] sommets = graphe.getedgename().get(arete);
                        Point point1 = sommetPoints.get(sommets[0]);
                        Point point2 = sommetPoints.get(sommets[1]);
                        if (point1 != null && point2 != null) {
                            g.drawLine(point1.x, point1.y, point2.x, point2.y);
                        }
                    }
                }

                if (draggedSommet != null) {
                    String typeoption = draggedSommet.type;
                    g.setColor(Color.YELLOW);
                    int x = sommetPoints.get(draggedSommet.getName()).x;
                    int y = sommetPoints.get(draggedSommet.getName()).y;
                    g.fillOval(x - circleSize / 2, y - circleSize / 2, circleSize, circleSize);
                    g.drawString(draggedSommet.getName(), x - 5, y - 10);
                }
            }

        };//complexité O(nbsommet + nbedge)

        pane.add(gsommet, BorderLayout.CENTER);


        gsommet.setBackground(Color.WHITE);
        gsommet.setLayout(null);

        gsommet.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                styleGraphe = "modification";

                for (Map.Entry <String, Point> s : sommetPoints.entrySet()) {
                    String sommetName = s.getKey();
                    Point sommetPoint = s.getValue();
                    if (sommetPoint != null && inCircle(x, y, sommetPoint.x, sommetPoint.y, circleSize / 2)) {
                        startPoint = new Point(x, y);
                        draggedSommet = graphe.getMaillonGraphePrin(sommetName);
                    }
                }
            }


            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for (Map.Entry <String, Point> s : sommetPoints.entrySet()) {
                    String sommetName = s.getKey();
                    Point sommetPoint = s.getValue();
                    if (sommetPoint != null && inCircle(x, y, sommetPoint.x, sommetPoint.y, circleSize / 2)) {
                        startPoint = new Point(x, y);
                        draggedSommet = graphe.getMaillonGraphePrin(sommetName);
                    }
                }
               try {
                   draggedSommet.getName();
                    if (e.getClickCount() == 2) {
                        InfoSommet fen4 = new InfoSommet(graphe, draggedSommet.getName());
                    } else if (e.getClickCount() == 1) {
                        sommetClic1 = sommetClic2;
                        sommetClic2 = draggedSommet.getName();
                        if (!sommetClic1.equals("") && graphe.getSommetVoisinDeSommet(sommetClic1, sommetClic2) != null) {
                            InfoArrete fen3 = new InfoArrete(graphe, sommetClic1, sommetClic2);
                            sommetClic1 = "";
                            sommetClic2 = "";
                        }
                    }
               }catch(Exception e1) {
                   JOptionPane.showMessageDialog(null, "Il n'y a pas de sommet à cet emplacement", "Erreur", JOptionPane.ERROR_MESSAGE);
               }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    Point sommetPoint = sommetPoints.get(draggedSommet.getName());
                    sommetPoints.put(draggedSommet.getName(), sommetPoint);
                    gsommet.repaint();
                    startPoint = null;
                    draggedSommet = null;
                }catch(Exception e1) {
                    JOptionPane.showMessageDialog(null, "Il n'y a pas de sommet à cet emplacement", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gsommet.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (startPoint != null && draggedSommet != null) {
                    int dx = e.getX() - startPoint.x;
                    int dy = e.getY() - startPoint.y;
                    startPoint = e.getPoint();
                    Point sommetPoint = sommetPoints.get(draggedSommet.getName());
                    sommetPoint.translate(dx, dy);
                    sommetPoints.put(draggedSommet.getName(), sommetPoint);
                    gsommet.repaint();
                }
            }
        });


        //add(gsommet);


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem loadItem = new JMenuItem("Open File");
        fileMenu.add(loadItem);

        JMenu formeMenu = new JMenu("Forme Affichage");
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
        JMenuItem djikstraItem = new JMenuItem("Afficher Dijkstra");
        traitementMenu.add(djikstraItem);



        loadItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                d.loadgraphe();
                graphe = d.getgraphe();
                gsommet.repaint();
            }
        });

        cerclesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                styleGraphe = "circle";
                gsommet.repaint();
            }
        });

        carreItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                styleGraphe = "carre";
                gsommet.repaint();
            }
        });

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

    private boolean inCircle(int x, int y, int circleX, int circleY, int radius) {
        int dx = x - circleX;
        int dy = y - circleY;
        return dx * dx + dy * dy <= radius * radius;
    }
    
    private static JLabel createLabel(String text, Font font, Color textColor) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(textColor);
        return label;
    }
}
