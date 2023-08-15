package GrapheAll;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 *
 * N.A
 *
 *
 * @author Baptiste
 */

public class GrapheTraitement {

    private Graphe graphe1;

    //constructeur
    public GrapheTraitement(Graphe g){
        graphe1 = g;
    }

    //Donne tout les sommets adjacents à un sommet
    public String afficher_Svoisin(String sommet){

        Graphe.MaillonGraphePrin sommettmp = graphe1.premier;
        String s = "";

        while (sommettmp != null && !sommettmp.getName().equals(sommet)) {
            sommettmp = sommettmp.getSuiv();
        }
        s = s + sommet +","+ sommettmp.type+ " : ";
        Graphe.MaillonGrapheSec tmp2 = sommettmp.getlVois();
        while (tmp2 !=null){
            s = s +tmp2.getDest() + "(fiabilité :"+tmp2.getFiab() + ", distance :" + tmp2.getDist() + ",duree "+ tmp2.getDur() + "), ";
            tmp2 = tmp2.getSuiv();
        }
        return s;
    }

    //permet de recuperer le sommet juste avec son nom
    protected Graphe.MaillonGraphePrin gotosommet (String sommet){
        Graphe.MaillonGraphePrin sommettmp = graphe1.premier;
        while (sommettmp != null && !sommettmp.getName().equals(sommet)) {
            sommettmp = sommettmp.getSuiv();
        }
        return sommettmp;
    }

    //Donne les deux sommet d'une arrete
    public String[] afficher_Esommet(String arrete){
        return graphe1.getSommetFromArete(arrete);
    }

    //Affiche le chemin pour deux sommet si il sont à 2-distance
    public String deuxvoisin(String sommet1, String sommet2) {
        Graphe.MaillonGraphePrin sommet1tmp = graphe1.premier;
        Graphe.MaillonGraphePrin sommet2tmp = graphe1.premier;

        sommet1tmp = gotosommet(sommet1);
        sommet2tmp = gotosommet(sommet2);

        Graphe.MaillonGrapheSec voisin1 = sommet1tmp.getlVois();
        String s = "";

        while (voisin1 != null) {
            Graphe.MaillonGraphePrin voisin1Prin = graphe1.getMaillonGraphePrin(voisin1.getDest());
            Graphe.MaillonGrapheSec voisin2 = voisin1Prin.getlVois();

            while (voisin2 != null) {
                if (voisin2.getDest().equals(sommet2)) {
                    s = sommet1+","+sommet1tmp.getType()+";"+ voisin1.getDest() + ","+voisin1.getDest()+","+voisin1Prin.getType()+";"+sommet2+","+sommet2tmp.getType();
                    return s;
                }
                voisin2 = voisin2.getSuiv();
            }
            voisin1 = voisin1.getSuiv();
        }
        return s;
    }
    
    /* DISFONCTIONNEL
    public String pVoisins(String sommet1, String sommet2, int n) {
        Graphe.MaillonGraphePrin sommet1tmp = graphe1.premier;
        Graphe.MaillonGraphePrin sommet2tmp = graphe1.premier;

        sommet1tmp = gotosommet(sommet1);
        sommet2tmp = gotosommet(sommet2);

        Graphe.MaillonGrapheSec voisin1 = sommet1tmp.getlVois();
        String s = "";

        int count = 0;

        while (voisin1 != null && count < n) {
            Graphe.MaillonGraphePrin voisin1Prin = graphe1.getMaillonGraphePrin(voisin1.getDest());
            Graphe.MaillonGrapheSec voisin2 = voisin1Prin.getlVois();

            while (voisin2 != null) {
                if (voisin2.getDest().equals(sommet2)) {
                    s += sommet1 + "," + voisin1.getDest() + ","; 
                    return s + sommet2; 
                }
                voisin2 = voisin2.getSuiv();
            }

            s += sommet1 + ","; 
            voisin1 = voisin1.getSuiv();
            count++;
        }

        return s; 
    }*/ 

    //Affiche les sommets voisins du sommet
    public String affichervoisinonlytype(String sommet) {
        Graphe.MaillonGraphePrin sommettmp = graphe1.premier;
        String s = "";

        while (sommettmp != null && !sommettmp.getName().equals(sommet)) {
            sommettmp = sommettmp.getSuiv();
        }
        s = s + sommet + "," + sommettmp.type + ";";
        Graphe.MaillonGrapheSec sommettmp2 = sommettmp.getlVois();
        while (sommettmp2 != null) {
            s = s + sommettmp2.getDest()+"," + graphe1.getMaillonGraphePrin(sommettmp2.getDest()).getType() + ";";
            sommettmp2 = sommettmp2.getSuiv();
        }
        return s;
    }

    //Affiche les voisins de meme type d'un sommet
    public String unvoisintype(String sommet){
        Graphe.MaillonGraphePrin sommettmp = graphe1.premier;
        String s = "";

        while (sommettmp != null && !sommettmp.getName().equals(sommet)) {
            sommettmp = sommettmp.getSuiv();
        }
        s = s + sommet + "," + sommettmp.type + ";";
        Graphe.MaillonGrapheSec sommettmp2 = sommettmp.getlVois();
        while (sommettmp2 != null) {

            if (graphe1.getMaillonGraphePrin(sommettmp2.getDest()).getType().equals(sommettmp.type)){
                s = s + sommettmp2.getDest() + "," + graphe1.getMaillonGraphePrin(sommettmp2.getDest()).getType() + ";";
            }

            sommettmp2 = sommettmp2.getSuiv();
        }
        return s;
    }

    //parcour les dispensiaire jusqu'a qu'il y en ait un qu'on ait jamais vu
    public String parcourschaquedisp(String sommet) {
        Graphe.MaillonGraphePrin sommettmp = graphe1.getMaillonGraphePrin(sommet);
        String s = "";
        HashSet<String> types = new HashSet<>();

        if (sommettmp != null) {
            while (sommettmp != null && !(types.contains("N") && types.contains("M") && types.contains("O"))) {
                String typeoption = sommettmp.type;

                if (!types.contains(typeoption)) {
                    s += sommettmp.getName() + "," + sommettmp.type + ";";
                    types.add(typeoption);
                }

                Graphe.MaillonGrapheSec voisin = sommettmp.getlVois();
                boolean dejavu = false;

                while (voisin != null) {
                    Graphe.MaillonGraphePrin sommetVoisin = graphe1.getMaillonGraphePrin(voisin.getDest());

                    if (sommetVoisin != null && !types.contains(sommetVoisin.getType())) {
                        sommettmp = sommetVoisin;
                        dejavu = true;
                        break;
                    }

                    voisin = voisin.getSuiv();
                }

                if (!dejavu) {
                    sommettmp = sommettmp.getSuiv();
                }
            }
        }

        System.out.println(s);
        return s;
    }

    //liste tous les sommets
    public String listerSommet() {
        String s = "";
        int i = 1;
        Graphe.MaillonGraphePrin tmp = graphe1.premier;
        while (tmp.getSuiv() != null) {
            s += tmp.getName() + ", ";
            i += 1;
            tmp = tmp.getSuiv();
        }
        s += tmp.getName() + "\nIl y a " + i + " sommets";
        return s;
    }

    public String[] listeSommets() {
        String[] s = new String[graphe1.nbSommets];
        int i = 0;
        Graphe.MaillonGraphePrin tmp = graphe1.getPremier();
        while (tmp.getSuiv() != null) {
            s[i] = tmp.getName();
            i += 1;
            tmp = tmp.getSuiv();
        }
        s[i] = tmp.getName();
        return s;
    }

    //liste tous les sommets d'un type
    public String listerSommet(String type) {
        String s = "";
        int i = 0;
        Graphe.MaillonGraphePrin tmp = graphe1.premier;
        while (tmp.getSuiv() != null) {
            if (tmp.getType().equals(type)) {
                s += tmp.getName() + ", ";
                i += 1;
            }
            tmp = tmp.getSuiv();
        }
        if (tmp.getType().equals(type)) {
            s += tmp.getName();
            i += 1;
        }
        s += "\nIl y a " + i + " sommets de type " + type;
        return s;
    }

    //liste toutes les arretes (nom et sommets concernés)
    public String listerArretes() {
        String s = "";
        int nbArretes = 0;
        for (String nomArrete : graphe1.edgename.keySet()) {
            nbArretes += 1;
            String[] arrete = graphe1.getSommetFromArete(nomArrete);
            s += nomArrete + " " + Arrays.toString(arrete) + " ,";
        }
        s += "\nIl y a un total de " + nbArretes + " arretes";
        return s;
    }


    //#############//
    //Djikstra zone//
    //#############//

    //initialise les valeurs de fiabilité, distance ou duree dans la SDD
    public void initVal(String sommet,  String type) {
        Graphe.MaillonGraphePrin tmp = graphe1.premier;
        switch (type) {
            case "fiab" -> {
                while (tmp != null) {
                    tmp.setFiab(Double.POSITIVE_INFINITY);
                    if (tmp.getName().equals(sommet)) {
                        tmp.setFiab(10);
                    }
                    tmp = tmp.suiv;
                }
            }
            case "dist" -> {
                while (tmp != null) {
                    tmp.setDist(Double.POSITIVE_INFINITY);
                    if (tmp.getName().equals(sommet)) {
                        tmp.setDist(0);
                    }
                    tmp = tmp.suiv;
                }
            }
            case "dur" -> {
                while (tmp != null) {
                    tmp.setDur(Double.POSITIVE_INFINITY);
                    if (tmp.getName().equals(sommet)) {
                        tmp.setDur(0);
                    }
                    tmp = tmp.suiv;
                }
            }
        }
    }

    //relacher arrete de Djikstra d'unu fiabilité d'une distance ou d'une duree
    public void relacheArrete(String depart, String arriver, String type) {
        Graphe.MaillonGraphePrin source = graphe1.getMaillonGraphePrin(depart);
        Graphe.MaillonGraphePrin destination = graphe1.getMaillonGraphePrin(arriver);
        Graphe.MaillonGrapheSec arrete = graphe1.getSommetVoisinDeSommet(depart, arriver);
        switch (type){
            case "fiab" -> {
                if(destination.getFiab() < (source.getFiab() / 10) * (arrete.getFiab() / 10) * 10 || Double.isInfinite(destination.getFiab())) {
                    destination.setFiab((source.getFiab() / 10) * (arrete.getFiab() / 10) * 10);
                    destination.setPredecesseur(depart);
                }
            }
            case "dist" -> {
                if(destination.getDist() > source.getDist() + arrete.getDist() || Double.isInfinite(destination.getDist())) {
                    destination.setDist(source.getDist() + arrete.getDist());
                    destination.setPredecesseur(depart);
                }
            }
            case "dur" -> {
                if(destination.getDur() > source.getDur() + arrete.getDur() || Double.isInfinite(destination.getDur())) {
                    destination.setDur(source.getDur() + arrete.getDur());
                    destination.setPredecesseur(depart);
                }
            }
        }
    }

    //supprime une valeur d'un tableau
    public String[] supprimerValeur(String[] tableau, String valeurASupprimer) {

        String[] nouveauTableau = new String[tableau.length - 1];

        int j = 0;
        for (int i = 0; i < tableau.length; i++) {
            if (!tableau[i].equals(valeurASupprimer)) {
                nouveauTableau[j] = tableau[i];
                j++;
            }
        }
        return nouveauTableau;
    }

    //renetialise les predecesseurs dans SDD
    public void resetPred() {
        Graphe.MaillonGraphePrin tmp = graphe1.premier;
        while(tmp != null) {
            tmp.setPredecesseur("");
            tmp = tmp.getSuiv();
        }
    }

    //Meilleure fiabilité a partir d'un sommet pour chaque sommet
    public String djikstraFiab(String depart, String arriver) {
        String s = "";
        String nomSommet = "";
        int i = 0;
        double fiabMax = 0;
        boolean succes = false;
        Graphe.MaillonGraphePrin sommet = graphe1.premier;
        Graphe.MaillonGrapheSec arrete;
        String[] F = new String[sommet.getNbSommets()];

        while (sommet != null) {
            F[i] = sommet.getName();
            sommet = sommet.getSuiv();
            i += 1;
        }

        sommet = graphe1.premier;
        initVal(depart, "fiab");

        while (!Arrays.stream(F).toList().isEmpty()) {
            while (sommet != null) {
                if (Arrays.asList(F).contains(sommet.getName()) && sommet.getFiab() > fiabMax && !Double.isInfinite(sommet.getFiab())) {
                    fiabMax = sommet.getFiab();
                    nomSommet = sommet.getName();
                }
                sommet = sommet.getSuiv();
            }

            sommet = graphe1.getMaillonGraphePrin(nomSommet);

            for (i = 0; i < F.length; i += 1) {
                if (sommet.getName().equals(F[i])) {
                    F = supprimerValeur(F, sommet.getName());
                }
            }


            arrete = sommet.getlVois();
            while (arrete != null) {
                if (Arrays.asList(F).contains(arrete.getDest())) {
                    relacheArrete(sommet.getName(), arrete.getDest(), "fiab");
                }
                arrete = arrete.getSuiv();
            }

            while(sommet != null) {
                sommet = sommet.getSuiv();
            }
            sommet = graphe1.premier;
            fiabMax = 0;
        }
        sommet = graphe1.getMaillonGraphePrin(arriver);

        int nbCase = 1;
        while(!sommet.getPredecesseur().equals(depart)) {
            sommet = graphe1.getMaillonGraphePrin(sommet.getPredecesseur());
            nbCase += 1;
        }

        String[] pred = new String[nbCase];
        sommet = graphe1.getMaillonGraphePrin(arriver);

        int j = 0;
        while(!succes) {
            pred[j] = sommet.getPredecesseur();
            if(sommet.getPredecesseur().equals(depart)) {
                succes = true;
            } else {
                sommet = graphe1.getMaillonGraphePrin(sommet.getPredecesseur());
            }
            j += 1;
        }

        for (i = pred.length - 1; i >= 0; i--) {
            s +=pred[i]+","+graphe1.getMaillonGraphePrin(pred[i]).getType()+";";
        }

        s +=arriver+","+graphe1.getMaillonGraphePrin(arriver).getType()+";"/*+graphe1.getMaillonGraphePrin(arriver).getFiab()*/;

        resetPred();

        return s;
    }

    //Meilleure distance a partir d'un sommet pour chaque sommet
    public String djikstraDist (String depart, String arriver) {
        String s = "";
        String nomSommet = "";
        int i = 0;
        double distMin = Double.POSITIVE_INFINITY;
        boolean succes = false;
        Graphe.MaillonGraphePrin sommet = graphe1.premier;
        Graphe.MaillonGrapheSec arrete;
        String[] F = new String[sommet.getNbSommets()];

        while (sommet != null) {
            F[i] = sommet.getName();
            sommet = sommet.getSuiv();
            i += 1;
        }

        sommet = graphe1.premier;
        initVal(depart, "dist");

        while (!Arrays.stream(F).toList().isEmpty()) {
            while (sommet != null) {
                if (Arrays.asList(F).contains(sommet.getName()) && sommet.getDist() < distMin && !Double.isInfinite(sommet.getDist())) {
                    distMin = sommet.getDist();
                    nomSommet = sommet.getName();
                }
                sommet = sommet.getSuiv();
            }

            sommet = graphe1.getMaillonGraphePrin(nomSommet);

            for (i = 0; i < F.length; i += 1) {
                if (sommet.getName().equals(F[i])) {
                    F = supprimerValeur(F, sommet.getName());
                }
            }


            arrete = sommet.getlVois();
            while (arrete != null) {
                if (Arrays.asList(F).contains(arrete.getDest())) {
                    relacheArrete(sommet.getName(), arrete.getDest(), "dist");
                }
                arrete = arrete.getSuiv();
            }

            while(sommet != null) {
                sommet = sommet.getSuiv();
            }
            sommet = graphe1.premier;
            distMin = Double.POSITIVE_INFINITY;
        }
        sommet = graphe1.getMaillonGraphePrin(arriver);

        int nbCase = 1;
        while(!sommet.getPredecesseur().equals(depart)) {
            sommet = graphe1.getMaillonGraphePrin(sommet.getPredecesseur());
            nbCase += 1;
        }

        String[] pred = new String[nbCase];
        sommet = graphe1.getMaillonGraphePrin(arriver);

        int j = 0;
        while(!succes) {
            pred[j] = sommet.getPredecesseur();
            if(sommet.getPredecesseur().equals(depart)) {
                succes = true;
            } else {
                sommet = graphe1.getMaillonGraphePrin(sommet.getPredecesseur());
            }
            j += 1;
        }

        for (i = pred.length - 1; i >= 0; i--) {
            s +=pred[i]+","+graphe1.getMaillonGraphePrin(pred[i]).getType()+";";
        }

        s +=arriver+","+graphe1.getMaillonGraphePrin(arriver).getType()+";";

        resetPred();

        return s;
    }

    //Meilleure durée a partir d'un sommet pour chaque sommet
    public String djikstraDur (String depart, String arriver) {
        String s = "";
        String nomSommet = "";
        int i = 0;
        double durMin = Double.POSITIVE_INFINITY;
        boolean succes = false;
        Graphe.MaillonGraphePrin sommet = graphe1.premier;
        Graphe.MaillonGrapheSec arrete;
        String[] F = new String[sommet.getNbSommets()];

        while (sommet != null) {
            F[i] = sommet.getName();
            sommet = sommet.getSuiv();
            i += 1;
        }

        sommet = graphe1.premier;
        initVal(depart, "dur");

        while (!Arrays.stream(F).toList().isEmpty()) {
            while (sommet != null) {
                if (Arrays.asList(F).contains(sommet.getName()) && sommet.getDur() < durMin && !Double.isInfinite(sommet.getDur())) {
                    durMin = sommet.getDur();
                    nomSommet = sommet.getName();
                }
                sommet = sommet.getSuiv();
            }

            sommet = graphe1.getMaillonGraphePrin(nomSommet);

            for (i = 0; i < F.length; i += 1) {
                if (sommet.getName().equals(F[i])) {
                    F = supprimerValeur(F, sommet.getName());
                }
            }


            arrete = sommet.getlVois();
            while (arrete != null) {
                if (Arrays.asList(F).contains(arrete.getDest())) {
                    relacheArrete(sommet.getName(), arrete.getDest(), "dur");
                }
                arrete = arrete.getSuiv();
            }

            while(sommet != null) {
                sommet = sommet.getSuiv();
            }
            sommet = graphe1.premier;
            durMin = Double.POSITIVE_INFINITY;
        }
        sommet = graphe1.getMaillonGraphePrin(arriver);

        int nbCase = 1;
        while(!sommet.getPredecesseur().equals(depart)) {
            sommet = graphe1.getMaillonGraphePrin(sommet.getPredecesseur());
            nbCase += 1;
        }

        String[] pred = new String[nbCase];
        sommet = graphe1.getMaillonGraphePrin(arriver);

        int j = 0;
        while(!succes) {
            pred[j] = sommet.getPredecesseur();
            if(sommet.getPredecesseur().equals(depart)) {
                succes = true;
            } else {
                sommet = graphe1.getMaillonGraphePrin(sommet.getPredecesseur());
            }
            j += 1;
        }

        for (i = pred.length - 1; i >= 0; i--) {
            s +=pred[i]+","+graphe1.getMaillonGraphePrin(pred[i]).getType()+";";
        }

        s +=arriver+","+graphe1.getMaillonGraphePrin(arriver).getType()+";";

        resetPred();

        return s;
    }

    //Meilleure fiabilité, distance ou durée a partir d'un sommet pour chaque sommets
    public String djikstra (String depart, String arriver, String type) {
        switch (type) {
            case "fiab" -> {
                return djikstraFiab(depart, arriver);
            }
            case "dist" -> {
                return djikstraDist(depart, arriver);
            }
            case "dur" -> {
                return djikstraDur(depart, arriver);
            }
            default -> {
                return "non";
            }
        }
    }

}