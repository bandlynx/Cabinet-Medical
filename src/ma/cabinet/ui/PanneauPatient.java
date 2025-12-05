/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.ui;

import ma.cabinet.dao.PatientDAO;
import ma.cabinet.model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanneauPatient extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    
    // Champs du formulaire
    private JTextField txtNom = new JTextField();
    private JTextField txtTel = new JTextField();
    private JTextField txtEmail = new JTextField();
    
    private PatientDAO patientDAO = new PatientDAO();

    public PanneauPatient() {
        setLayout(new BorderLayout());

        // --- HAUT : Formulaire d'ajout ---
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Nouveau Patient"));

        formPanel.add(new JLabel("Nom complet :"));
        formPanel.add(txtNom);
        formPanel.add(new JLabel("Téléphone :"));
        formPanel.add(txtTel);
        formPanel.add(new JLabel("Email :"));
        formPanel.add(txtEmail);

        JButton btnAjouter = new JButton("Ajouter Patient");
        btnAjouter.addActionListener(e -> ajouterPatient());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnAjouter, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // --- CENTRE : Tableau ---
        String[] colonnes = {"ID", "Nom", "Téléphone", "Email"};
        model = new DefaultTableModel(colonnes, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        chargerDonnees();
    }

    private void chargerDonnees() {
        try {
            model.setRowCount(0);
            List<Patient> list = patientDAO.findAll();
            for (Patient p : list) {
                model.addRow(new Object[]{
                    p.getId(), p.getNom(), p.getTelephone(), p.getEmail()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ajouterPatient() {
        try {
        String nom = txtNom.getText().trim();
        String tel = txtTel.getText().trim();
        String email = txtEmail.getText().trim();

        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire");
            return;
        }

        Patient p = new Patient(nom, tel, email);
        patientDAO.ajouter(p);

        JOptionPane.showMessageDialog(this, "Patient ajouté avec succès !");
        txtNom.setText("");
        txtTel.setText("");
        txtEmail.setText("");
        chargerDonnees();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
    }
    }
}
