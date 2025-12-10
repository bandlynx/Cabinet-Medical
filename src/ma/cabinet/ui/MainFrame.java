 
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

        
        tabbedPane.addTab("Planning RDV", new PanneauRendezVous());
        tabbedPane.addTab("Consultations", new PanneauConsultation());
        tabbedPane.addTab("Bilan mensuel", new PanneauBilanMensuel());

        
        if (u instanceof Assistant) {
            tabbedPane.insertTab("Gestion Patients", null, new PanneauPatient(), null, 0);
            tabbedPane.insertTab("Gestion RDV", null, new PanneauRendezVous(), null, 1);
            tabbedPane.insertTab("Paiement/caisse", null, new PanneauPaiement(), null, 2);
            tabbedPane.addTab("Gestion Categorie", new PanneauCategorie());
            tabbedPane.addTab("Gestion Assistant", new PanneauAssistant());
        }

        
        if (u instanceof Medecin) {
            
            tabbedPane.insertTab("Gestion Patients", null, new PanneauPatient(), null, 0);
            
        }

        this.add(tabbedPane);
    }
}
