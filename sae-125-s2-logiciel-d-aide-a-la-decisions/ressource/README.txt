###############################
# Projet de Gestion de Graphe #
###############################

Ce projet est un programme de gestion de graphe écrit en Java. 
Il permet de créer, afficher et traiter des graphes en utilisant une interface graphique.
Tout cela à partir d'un fichier csv, vous trouverez dans le dossier ressource le fichier data.csv qui est au bon format.

####                       ####
#S1#----Fonctionnalités----#S2#
####                       ####

Toutes ces fonctionnalités sont présente dans le Jmenu supérieur. 
- Chargement d'un graphe à partir d'un fichier.
- Affichage du graphe dans une fenêtre graphique.
- Gestion des sommets du graphe.
- Gestion des arêtes du graphe.
- Choix du style d'affichage du graphe (cercles ou carrés).
- Affichage des voisins d'un sommet.
- Affichage des deux voisins d'un sommet.
- Affichage du plus court chemin entre deux sommets (algorithme de Dijkstra).

####                       ####
#S3#------Utilisation------#S4#
####                       ####

- Au lancement du programme, une fenêtre graphique permettant de choisir un premier fichier.
- Cliquez sur les sommets pour les déplacer en restant appuyé sur votre souris.
- Utilisez le menu "Forme Affichage" pour choisir le style d'affichage du graphe.
- Utilisez le menu "Traitement" pour effectuer différentes opérations sur le graphe.

####                       ####
#S4#--------Données--------#S5#
####                       ####

Le fichier cs doit contenir une ligne par sommets. Chaque arretes n'apparait qu'une seul fois dans le fichier.
Voici la structure d'utilisation :

Sommet1,type:Sommetadjacent1,fiabilité,distance,durée;

Voici un exemple d'utilisation :
S2,M:S4,9,28,37;S22,6,12,24;

####                       ####
#S4#--------Autres---------#S5#
####                       ####

Dans le dossier ressource vous trouverez une disposition parfaite pour le graphe issu de data.csv
Cependant data.csv n'étant qu'un exemple cette disposition de sera valable que pour lui

####                       ####
#S6#-----Contributions-----#S7#
####                       ####

Ce projet a été réalisé par Axel Raimondo et Baptiste Rousselot.