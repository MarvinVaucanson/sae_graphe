package GrapheAll;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Baptiste
 */
public class DjikstraIHM extends JFrame {


    private JPanel pane;
    private String resultat;
    private JPanel gsommet;
    protected ArrayList<String> selectedOptions;
    private JList<String> list;
    private Map<String, Point> sommetPoints;

    //constructeur
    public DjikstraIHM(Graphe g) {

        initComponents(g);
        setTitle("Dijkstra");
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

        JLabel titlelabel = new JLabel("Saisissez deux sommets");
        sideBar.add(titlelabel);

        String[] options = new String[30];
        Graphe.MaillonGraphePrin tmp = graphe.premier;
        int i = 0;
        while (tmp != null){
            options[i] = tmp.getName();
            tmp = tmp.getSuiv();
            i += 1;
        }

        selectedOptions = new ArrayList<>();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String option : options) {
            listModel.addElement(option);
        }

        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(5);

        list.addListSelectionListener(e -> {
            int[] selectedIndices = list.getSelectedIndices();
            if (selectedIndices.length > 2) {
                int[] newSelectedIndices = new int[2];
                newSelectedIndices[0] = selectedIndices[0];
                newSelectedIndices[1] = selectedIndices[1];
                list.setSelectedIndices(newSelectedIndices);
            }
            selectedOptions.clear();
            for (int index : list.getSelectedIndices()) {
                selectedOptions.add(options[index]);
            }
            System.out.println("Options sélectionnées : " + selectedOptions);
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(180, 120));
        sideBar.add(scrollPane);

        JLabel resultLabel = new JLabel();

        JButton buttonFiab = new JButton("Afficher Fiabilité");
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(buttonFiab);
        buttonFiab.setMaximumSize(new Dimension(200, buttonFiab.getPreferredSize().height));
        buttonFiab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input1 = selectedOptions.get(0);
                String input2 = selectedOptions.get(1);
                resultat=t.djikstraFiab(input1, input2);
                resultLabel.setText(resultat+"\n"+"La fiabilité est de");
                gsommet.repaint();
                JOptionPane.showMessageDialog(null, "La fiabilité de " + input1 + " à " + input2 + " à une fiabilité " + graphe.getMaillonGraphePrin(input2).getFiab()*10+"%", "Fiabilité", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton buttonDist = new JButton("Afficher distance");
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(buttonDist);
        buttonDist.setMaximumSize(new Dimension(200, buttonDist.getPreferredSize().height));
        buttonDist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input1 = selectedOptions.get(0);
                String input2 = selectedOptions.get(1);
                resultat=t.djikstraDist(input1, input2);
                gsommet.repaint();
                JOptionPane.showMessageDialog(null, "La distance de " + input1 + " à " + input2 + " à une distance " + graphe.getMaillonGraphePrin(input2).getDist(), "Distance", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton buttonDur = new JButton("Afficher Durée");
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(buttonDur);
        buttonDur.setMaximumSize(new Dimension(200, buttonDur.getPreferredSize().height));
        buttonDur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input1 = selectedOptions.get(0);
                String input2 = selectedOptions.get(1);
                resultat=t.djikstraDur(input1, input2);
                gsommet.repaint();
                JOptionPane.showMessageDialog(null, "La durée de " + input1 + " à " + input2 + " à une durée " + graphe.getMaillonGraphePrin(input2).getDur(), "Durée", JOptionPane.INFORMATION_MESSAGE);
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

                    g.setColor(graphe.choosecolor(sommetType));
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
        JMenuItem djikstraItem = new JMenuItem("Afficher Dijkstra");
        traitementMenu.add(djikstraItem);
        djikstraItem.setEnabled(false);

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
