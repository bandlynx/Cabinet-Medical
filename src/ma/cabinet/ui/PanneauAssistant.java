 
package ma.cabinet.ui;

import ma.cabinet.dao.AssistantDAO;
import ma.cabinet.model.Assistant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanneauAssistant extends JPanel {

    private JTextField txtLogin = new JTextField();
    private JPasswordField txtPassword = new JPasswordField();
    private JTextField txtNom = new JTextField();

    private DefaultTableModel model;
    private JTable table;

    private AssistantDAO assistantDAO = new AssistantDAO();

    public PanneauAssistant() {
        setLayout(new BorderLayout());

        
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Nouvel assistant"));

        form.add(new JLabel("Login :"));
        form.add(txtLogin);

        form.add(new JLabel("Mot de passe :"));
        form.add(txtPassword);

        form.add(new JLabel("Nom :"));
        form.add(txtNom);

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnRecharger = new JButton("Recharger");

        btnAjouter.addActionListener(e -> ajouterAssistant());
        btnRecharger.addActionListener(e -> chargerAssistants());

        JPanel boutons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        boutons.add(btnAjouter);
        boutons.add(btnRecharger);

        JPanel haut = new JPanel(new BorderLayout());
        haut.add(form, BorderLayout.CENTER);
        haut.add(boutons, BorderLayout.SOUTH);

        add(haut, BorderLayout.NORTH);

        
        String[] colonnes = {"ID", "Login", "Nom"};
        model = new DefaultTableModel(colonnes, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        
        chargerAssistants();
    }

    private void chargerAssistants() {
        try {
            model.setRowCount(0);
            for (Assistant a : assistantDAO.findAll()) {
                model.addRow(new Object[]{
                        a.getId(),
                        a.getLogin(),
                        a.getNom()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erreur chargement assistants : " + ex.getMessage());
        }
    }

    private void ajouterAssistant() {
        try {
            String login = txtLogin.getText().trim();
            String password = new String(txtPassword.getPassword());
            String nom = txtNom.getText().trim();

            if (login.isEmpty() || password.isEmpty() || nom.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir tous les champs.");
                return;
            }

            Assistant a = new Assistant(login, password, nom);
            assistantDAO.ajouter(a);

            JOptionPane.showMessageDialog(this, "Assistant ajouté avec succès !");

            
            txtLogin.setText("");
            txtPassword.setText("");
            txtNom.setText("");
            chargerAssistants();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erreur ajout assistant : " + ex.getMessage());
        }
    }
}
