

import GrapheAll.AccueilIHM;
import GrapheAll.GrapheTraitement;
import GrapheAll.Graphe;
import GrapheAll.Dataload;
import GrapheAll.DeuxVoisinIHM;
import GrapheAll.DjikstraIHM;
import GrapheAll.GrapheIHM;
import GrapheAll.VoisinIHM;
import javax.swing.*;


public class Application {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AccueilIHM accueil = new AccueilIHM();
                accueil.setVisible(true);
            }
        });

    }
}

