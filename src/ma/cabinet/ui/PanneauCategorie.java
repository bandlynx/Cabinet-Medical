/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.ui;

import ma.cabinet.dao.CategorieConsultationDAO;
import ma.cabinet.model.CategorieConsultation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanneauCategorie extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtDesignation = new JTextField();
    private JTextField txtDescription = new JTextField();
    private CategorieConsultationDAO dao = new CategorieConsultationDAO();

    public PanneauCategorie() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.setBorder(BorderFactory.createTitledBorder("Nouvelle Catégorie"));
        formPanel.add(new JLabel("Désignation :")); formPanel.add(txtDesignation);
        formPanel.add(new JLabel("Description :")); formPanel.add(txtDescription);
        
        JButton btnAdd = new JButton("Ajouter Catégorie");
        btnAdd.addActionListener(e -> ajouter());
        
        JPanel top = new JPanel(new BorderLayout());
        top.add(formPanel, BorderLayout.CENTER);
        top.add(btnAdd, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Désignation", "Description"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        refresh();
    }

    private void refresh() {
        try {
            model.setRowCount(0);
            List<CategorieConsultation> list = dao.findAll();
            for (CategorieConsultation c : list) {
                model.addRow(new Object[]{c.getId(), c.getDesignation(), c.getDescription()});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void ajouter() {
        try {
            CategorieConsultation c = new CategorieConsultation(txtDesignation.getText(), txtDescription.getText());
            dao.ajouter(c);
            refresh();
            txtDesignation.setText(""); 
            txtDescription.setText("");
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage()); }
    }
}
