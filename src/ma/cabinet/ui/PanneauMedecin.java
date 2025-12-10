 
package ma.cabinet.ui;

import ma.cabinet.dao.MedecinDAO;
import ma.cabinet.model.Medecin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanneauMedecin extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    
    
    private JTextField txtNom = new JTextField();
    private JTextField txtSpecialite = new JTextField();
    private JTextField txtLogin = new JTextField();
    private JPasswordField txtPassword = new JPasswordField(); 
    
    private MedecinDAO medecinDAO = new MedecinDAO();

    public PanneauMedecin() {
        setLayout(new BorderLayout());

        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Nouveau Médecin"));

        formPanel.add(new JLabel("Nom du Médecin :"));
        formPanel.add(txtNom);
        formPanel.add(new JLabel("Spécialité :"));
        formPanel.add(txtSpecialite);
        formPanel.add(new JLabel("Login (Utilisateur) :"));
        formPanel.add(txtLogin);
        formPanel.add(new JLabel("Mot de passe :"));
        formPanel.add(txtPassword);

        JButton btnAjouter = new JButton("Ajouter Médecin");
        btnAjouter.addActionListener(e -> ajouterMedecin());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnAjouter, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        
        String[] colonnes = {"ID", "Nom", "Spécialité", "Login"};
        model = new DefaultTableModel(colonnes, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        chargerDonnees();
    }

    private void chargerDonnees() {
        try {
            model.setRowCount(0);
            List<Medecin> list = medecinDAO.findAll();
            for (Medecin m : list) {
                model.addRow(new Object[]{
                    m.getId(), m.getNom(), m.getSpecialite(), m.getLogin()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ajouterMedecin() {
        try {
            
            Medecin m = new Medecin(0, 
                txtLogin.getText(), 
                new String(txtPassword.getPassword()), 
                txtNom.getText(), 
                txtSpecialite.getText()
            );
            
            medecinDAO.ajouter(m);
            JOptionPane.showMessageDialog(this, "Médecin ajouté !");
            
            
            txtNom.setText("");
            txtSpecialite.setText("");
            txtLogin.setText("");
            txtPassword.setText("");
            chargerDonnees();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }
}
