/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.ui;

import javax.swing.*;
import ma.cabinet.model.Utilisateur;
import ma.cabinet.model.Assistant;
import ma.cabinet.model.Medecin;
import ma.cabinet.util.Session;

public class MainFrame extends JFrame {

    public MainFrame() {
        Utilisateur u = Session.getCurrentUser();

        if (u != null) {
            setTitle("Gestion Cabinet Médical - Connecté : " + u.getLogin());
        } else {
            setTitle("Gestion Cabinet Médical");
        }

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Onglets communs à tout le monde
        tabbedPane.addTab("Planning RDV", new PanneauRendezVous());
        tabbedPane.addTab("Consultations", new PanneauConsultation());
        tabbedPane.addTab("Bilan mensuel", new PanneauBilanMensuel());

        // Onglets visibles uniquement pour l'assistant (profil "admin" du cabinet)
        if (u instanceof Assistant) {
            tabbedPane.insertTab("Gestion Patients", null, new PanneauPatient(), null, 0);
            tabbedPane.insertTab("Gestion RDV", null, new PanneauRendezVous(), null, 1);
            tabbedPane.insertTab("Paiement/caisse", null, new PanneauPaiement(), null, 2);
            tabbedPane.addTab("Gestion Categorie", new PanneauCategorie());
            tabbedPane.addTab("Gestion Assistant", new PanneauAssistant());
        }

        // Si tu veux donner quelques droits de gestion au médecin :
        if (u instanceof Medecin) {
            // Par ex. permettre de voir/modifier les patients :
            tabbedPane.insertTab("Gestion Patients", null, new PanneauPatient(), null, 0);
            // Mais ne pas afficher "Gestion Assistants"
        }

        this.add(tabbedPane);
    }
}
