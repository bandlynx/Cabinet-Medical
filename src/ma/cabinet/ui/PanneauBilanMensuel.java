/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.ui;

import ma.cabinet.dao.PaiementDAO;
import ma.cabinet.model.BilanMensuel;
import ma.cabinet.model.BilanJour;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PanneauBilanMensuel extends JPanel {

    private JComboBox<Integer> cbMois;
    private JSpinner spAnnee;

    private JLabel lblNbConsult = new JLabel("0");
    private JLabel lblCA = new JLabel("0.0");

    private DefaultTableModel modelDetails;
    private PaiementDAO paiementDAO = new PaiementDAO();

    public PanneauBilanMensuel() {
        setLayout(new BorderLayout());

        // --------- Haut : sélection du mois / année ----------
        JPanel top = new JPanel(new GridLayout(2, 4, 5, 5));
        top.setBorder(BorderFactory.createTitledBorder("Paramètres du bilan"));

        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        cbMois = new JComboBox<>();
        for (int m = 1; m <= 12; m++) cbMois.addItem(m);
        cbMois.setSelectedItem(currentMonth);

        spAnnee = new JSpinner(new SpinnerNumberModel(currentYear, 2000, 2100, 1));

        JButton btnCalculer = new JButton("Calculer");
        btnCalculer.addActionListener(e -> calculerBilan());

        top.add(new JLabel("Mois (1-12) :"));
        top.add(cbMois);
        top.add(new JLabel("Année :"));
        top.add(spAnnee);

        top.add(new JLabel());  // vide
        top.add(btnCalculer);
        top.add(new JLabel());  // vide
        top.add(new JLabel());  // vide

        add(top, BorderLayout.NORTH);

        // --------- Centre : résumé + détails ----------
        JPanel center = new JPanel(new BorderLayout());

        JPanel resume = new JPanel(new GridLayout(2, 2, 5, 5));
        resume.setBorder(BorderFactory.createTitledBorder("Résumé du mois"));

        resume.add(new JLabel("Nombre de consultations :"));
        resume.add(lblNbConsult);
        resume.add(new JLabel("Chiffre d'affaires (DH) :"));
        resume.add(lblCA);

        center.add(resume, BorderLayout.NORTH);

        modelDetails = new DefaultTableModel(
                new String[]{"Jour", "Nb consultations", "Chiffre d'affaires"}, 0
        );
        JTable table = new JTable(modelDetails);
        center.add(new JScrollPane(table), BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private void calculerBilan() {
        try {
            int mois = (Integer) cbMois.getSelectedItem();
            int annee = (Integer) spAnnee.getValue();

            // Résumé
            BilanMensuel bilan = paiementDAO.getBilanMensuel(annee, mois);
            lblNbConsult.setText(String.valueOf(bilan.getNbConsultations()));
            lblCA.setText(String.format("%.2f", bilan.getChiffreAffaires()));

            // Détails par jour
            modelDetails.setRowCount(0);
            List<BilanJour> details = paiementDAO.getDetailsBilanMensuel(annee, mois);
            for (BilanJour bj : details) {
                modelDetails.addRow(new Object[]{
                        bj.getJour(),
                        bj.getNbConsultations(),
                        String.format("%.2f", bj.getChiffreAffaires())
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du calcul du bilan : " + ex.getMessage());
        }
    }
}
