package GrapheAll;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Baptiste
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;


public class Dataload {

    Graphe g = new Graphe();
    static int i = 0;
    protected int nbsommet = 0;
    public String nomFichier = "";

    public void loadgraphe(){
        //load("C:\\Users\\Fujitsu\\Desktop\\Cours\\S2\\SAE\\Test SAE\\Ressource\\data.csv");
        load(choosefile());
    }

    //choisir le fichier a ouvrir
    public String choosefile(){
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            nomFichier = fileChooser.getSelectedFile().getAbsolutePath();
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }


    //chargement du graphe
    public void load(String path){
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                loadsommet(data);
            }

            Scanner myReaderA = new Scanner(myObj);

            while (myReaderA.hasNextLine()) {
                String data = myReaderA.nextLine();
                loadarrete(data);
            }
            myReaderA.close();


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //chargement des sommets
    public void loadsommet(String line) {
        String cS1;
        String[] tab = line.split(":");
        cS1 = tab[0];
        String[] tab2 = cS1.split(",");
        g.addMain(tab2[0],tab2[1]);
        nbsommet += 1;
    }

    //chargement des arretes
    public void loadarrete(String line){
        String cS1,cS2 ;
        String cA;
        String[] tab = line.split(":");
        cS1 = tab[0];
        String[] tab2 = cS1.split(",");
        cS1 = tab2[0];

        cS2 = tab[1];
        String[] tab3 = cS2.split(";");
        for(String n : tab3){
            i=i+1;
            String[] tab4 = n.split(",");
            g.addEdge(cS1,tab4[0],Double.parseDouble(tab4[1]),Double.parseDouble(tab4[2]),Double.parseDouble(tab4[3]),i);
        }
    }

    //getteur
    public Graphe getgraphe(){
        return g;
    }

    public int getSize(){
        return nbsommet;
    }
}


