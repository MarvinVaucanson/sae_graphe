package GrapheAll;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.*;
import java.util.HashMap;


public class Graphe {

    protected int nbSommets = 0;

    public class MaillonGraphePrin {
        private String name;
        public String type;
        private double fiab = Double.POSITIVE_INFINITY;
        private double dist = Double.POSITIVE_INFINITY;
        private double dur = Double.POSITIVE_INFINITY;
        private String predecesseur = "";
        private MaillonGrapheSec lVois;
        MaillonGraphePrin suiv;
        //private boolean listed

        //constructeur
        MaillonGraphePrin(String n, String t){
            name = n;
            type = t;
            lVois = null;
            suiv = null;
            nbSommets += 1;
        }

        //getteurs
        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public double getFiab() {
            return fiab;
        }

        public double getDist() {
            return dist;
        }

        public double getDur() {
            return dur;
        }

        public String getPredecesseur() {
            return predecesseur;
        }

        public int getNbSommets() {
            return nbSommets;
        }

        public MaillonGrapheSec getlVois() {
            return lVois;
        }

        public MaillonGraphePrin getSuiv() {
            return suiv;
        }

        //setteurs
        public void setFiab(double fiab) {
            this.fiab = fiab;
        }

        public void setDist(double dist) {
            this.dist = dist;
        }

        public void setDur(double dur) {
            this.dur = dur;
        }

        public void setPredecesseur(String predecesseur) {
            this.predecesseur = predecesseur;
        }

    }

    public class MaillonGrapheSec {
        private double fiab;
        private double dist;
        private double dur;
        private String dest;
        private MaillonGrapheSec suiv;

        //constructeur
        MaillonGrapheSec(double f, double dt, double dr, String d){
            fiab = f;
            dist = dt;
            dur = dr;
            dest = d;
            suiv = null;
        }

        //getteurs
        public double getFiab() {
            return fiab;
        }

        public double getDist() {
            return dist;
        }

        public double getDur() {
            return dur;
        }

        public String getDest() {
            return dest;
        }

        public MaillonGrapheSec getSuiv() {
            return suiv;
        }
    }

    public MaillonGraphePrin premier;

    //listes des arretes avec le sommet1 et le sommet2
    public HashMap<String, String[]> edgename;

    //constructeur
    public Graphe() {
        premier = null;
        edgename = new HashMap<>();
    }

    public MaillonGraphePrin getPremier(){
        return premier;
    }

    //ajout d'un sommet dans la SDD
    public void addMain(String ori, String t){
        MaillonGraphePrin nouv = new MaillonGraphePrin(ori,t);
        nouv.suiv = this.premier;
        this.premier = nouv;
    }

    //ajout d'une arrete dans la SDD
    public void addEdge(String ori, String d, double f, double dt, double dr ,int i) {
        MaillonGrapheSec edgenouv = new MaillonGrapheSec(f,dt,dr,d);
        MaillonGraphePrin tmp = this.premier;
        while (!tmp.name.equals(ori)) {
            tmp = tmp.suiv;
        }
        edgenouv.suiv = tmp.lVois;
        tmp.lVois = edgenouv;

        MaillonGrapheSec nouv2 = new MaillonGrapheSec(f, dt, dr, ori);
        tmp = this.premier;
        while (!tmp.name.equals(d)) {
            tmp = tmp.suiv;
        }
        nouv2.suiv = tmp.lVois;
        tmp.lVois = nouv2;

        String[] sommetsvois = new String[]{ori, d};
        edgename.put("A"+i, sommetsvois);
    }

    //methode String
    public String toString(){
        String s="";
        int i = 0;
        MaillonGraphePrin tmp = this.premier;
        while (tmp != null) {
            s = s + tmp.name +","+ tmp.type + " : ";
            MaillonGrapheSec tmp2 = tmp.lVois;
            while (tmp2 !=null){
                i = i +1;
                s = s + tmp2.dest + "(fiabilité :"+tmp2.fiab + ", distance :" + tmp2.dist + ",duree "+ tmp2.dur + "), ";
                tmp2 = tmp2.suiv;
            }
            s =s + "\n";
            tmp = tmp.suiv;
        }
        return s;
    }

    //recuperer les noms des sommets d'une arrete
    public String[] getSommetFromArete(String nomArete) {
        return edgename.get(nomArete);
    }

    //recuperer le sommet avec son nom
    public MaillonGraphePrin getMaillonGraphePrin (String sommet) {
        MaillonGraphePrin tmp = premier;
        while (tmp != null && !tmp.getName().equals(sommet)) {
            tmp = tmp.suiv;
        }
        return tmp;
    }

    //recuperer une arrete avec le sommet1 et le sommet2
    public MaillonGrapheSec getSommetVoisinDeSommet(String depart, String arriver) {
        MaillonGraphePrin source = getMaillonGraphePrin(depart);
        MaillonGrapheSec destination = source.lVois;
        while (destination != null) {
            if(destination.getDest().equals(arriver)) {
                return destination;
            }
            destination = destination.getSuiv();
        }
        return destination;
    }

    //recuperer la liste des arretes
    public HashMap<String, String[]> getedgename(){
        return edgename;
    }

    //changer toute la liste des arretes
    public void setedgename(HashMap<String, String[]> newedge){
        edgename = newedge;
    }


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
}

