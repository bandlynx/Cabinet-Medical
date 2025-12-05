Cabinet Médical – Application Java

Ce projet est une application Desktop réalisée en Java Swing avec une base de données MySQL.
Elle permet de gérer les principales opérations d’un cabinet médical : patients, médecins, consultations, paiements et rendez-vous.

I.Fonctionnalités :

-Gestion des patients

-Gestion des médecins

-Gestion des consultations

-Gestion des rendez-vous

-Gestion des paiements

-Bilans journaliers et mensuels

-Authentification (écran de connexion)

II.Structure du projet

model : classes représentant les entités (Patient, Medecin, Consultation…)

dao : accès à la base de données (CRUD via JDBC)

ui : interfaces graphiques (Java Swing)

util : connexion à la base (DBConnection) et gestion de session

III.Technologies utilisées :

-Java (Swing)

-MySQL

-JDBC

-NetBeans

IV.Configuration 

Modifier les identifiants de connexion dans :

src/ma/cabinet/util/DBConnection.java

Exemple :

String url = "jdbc:mysql://localhost:3306/cabinet_medical";
String user = "root";
String password = "";

Exécution

Ouvrir le projet dans NetBeans et cliquer sur Run Project.
