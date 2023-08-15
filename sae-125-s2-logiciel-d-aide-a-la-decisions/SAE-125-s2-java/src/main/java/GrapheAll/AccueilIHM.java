package GrapheAll;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilIHM extends JFrame {

    private Dataload d = new Dataload();
    private Graphe g = new Graphe();
    private JLabel labelTitre, labelTexte;
    private JButton boutonImporter;

    //constructeur fenetre d'accueil
    public AccueilIHM() {
        setTitle("Accueil");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("..\\ressource\\icone.png");
        setIconImage(icon);

        labelTitre = new JLabel("Bienvenue !");
        labelTexte = new JLabel("Application de visualisation de graphe par : Axel Raimondo et Baptiste Rousselot");
        boutonImporter = new JButton("Importer un fichier");

        labelTitre.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);

        boutonImporter.setFont(new Font("Arial", Font.PLAIN, 16));
        boutonImporter.setPreferredSize(new Dimension(200, 40));

        //importer son fichier .csv
        boutonImporter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    d.loadgraphe();
                    g = d.getgraphe();
                    int size = d.getSize();
                    GrapheIHM fen = new GrapheIHM(g, size);
                    dispose();
                }catch(Exception ignored){}
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(labelTitre, BorderLayout.NORTH);
        panel.add(labelTexte, BorderLayout.CENTER);
        panel.add(boutonImporter, BorderLayout.SOUTH);
        add(panel);
    }
}
