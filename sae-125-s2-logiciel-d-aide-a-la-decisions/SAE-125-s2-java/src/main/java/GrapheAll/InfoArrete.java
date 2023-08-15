package GrapheAll;

import javax.swing.*;
import java.awt.*;

public class InfoArrete extends JFrame {

    private JPanel pane;
    Graphe graphe = new Graphe();
    private JPanel gsommet;


    public InfoArrete(Graphe g, String depart, String arriver) {

        initComponents(g, depart, arriver);
        setTitle("Informations sur l'arrete");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        Image icon = Toolkit.getDefaultToolkit().getImage("..\\ressource\\icone.png");
        setIconImage(icon);
        int width = 300;
        int height = 250;
        setPreferredSize(new Dimension(width, height));
        pack();

        setLocationRelativeTo(null);
    }

    public void initComponents(Graphe g, String depart, String arriver) {

        Graphe.MaillonGraphePrin sommet1 = g.getMaillonGraphePrin(depart);
        Graphe.MaillonGraphePrin sommet2 = g.getMaillonGraphePrin(arriver);
        Graphe.MaillonGrapheSec arrete = g.getSommetVoisinDeSommet(depart, arriver);

        pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.setBackground(new Color(240, 240, 240));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Color labelTextColor = new Color(30, 30, 30);

        JLabel labelSommet1 = createLabel("Sommet 1: " + sommet1.getName() + " (Type: " + sommet1.getType() + ")", labelFont, labelTextColor);
        labelSommet1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelSommet2 = createLabel("Sommet 2: " + sommet2.getName() + " (Type: " + sommet2.getType() + ")", labelFont, labelTextColor);
        labelSommet2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelFiabilite = createLabel("Fiabilité: " + arrete.getFiab(), labelFont, labelTextColor);
        labelFiabilite.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel labelDistance = createLabel("Distance: " + arrete.getDist(), labelFont, labelTextColor);
        labelDistance.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel labelDuree = createLabel("Durée: " + arrete.getDur(), labelFont, labelTextColor);
        labelDuree.setAlignmentX(Component.CENTER_ALIGNMENT);

        gsommet = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(graphe.choosecolor(sommet1.getType()));
                g.fillOval(50, 50, 20, 20);
                g.drawString(depart, 45, 45);

                g.setColor(graphe.choosecolor(sommet2.getType()));
                g.fillOval(210, 50, 20, 20);
                g.drawString(arriver, 205, 45);

                g.setColor(Color.BLACK);
                g.drawLine(70, 60, 210, 60);

            }
        };
        gsommet.setBackground(Color.WHITE);
        gsommet.setLayout(null);

        pane.add(Box.createVerticalGlue());
        pane.add(labelSommet1);
        pane.add(labelSommet2);
        pane.add(labelFiabilite);
        pane.add(labelDistance);
        pane.add(labelDuree);
        pane.add(Box.createVerticalGlue());

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(pane, BorderLayout.NORTH);
        this.getContentPane().add(gsommet, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }




    private static JLabel createLabel(String text, Font font, Color textColor) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(textColor);
        return label;
    }
}
