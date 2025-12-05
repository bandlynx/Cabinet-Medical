/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.ui;

import ma.cabinet.dao.MedecinDAO;
import ma.cabinet.dao.PatientDAO;
import ma.cabinet.dao.RendezVousDAO;
import ma.cabinet.model.Medecin;
import ma.cabinet.model.Patient;
import ma.cabinet.model.RendezVous;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RendezVousFrame extends JFrame {

    private JComboBox<String> cbPatient;
    private JComboBox<String> cbMedecin;
    private JComboBox<String> cbStatut;
    private JTextField txtDateHeure;
    private JTextField txtMotif;
    private JTable tblRdv;
    private DefaultTableModel tableModel;

    private PatientDAO patientDAO = new PatientDAO();
    private MedecinDAO medecinDAO = new MedecinDAO();
    private RendezVousDAO rdvDAO = new RendezVousDAO();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // pour afficher les noms dans le tableau
    private Map<Integer, String> mapPatients = new HashMap<>();
    private Map<Integer, String> mapMedecins = new HashMap<>();

    public RendezVousFrame() {
        setTitle("Gestion des rendez-vous");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        initComponents();
        remplirCombos();
        remplirComboStatut();
        chargerRendezVous();
    }

    // ===================== UI ======================
    private void initComponents() {
        // panneau formulaire (gauche)
        JPanel formPanel = new JPanel(new GridBagLayout());
GridBagConstraints gc = new GridBagConstraints();
gc.insets = new Insets(5, 5, 5, 5);
gc.anchor = GridBagConstraints.WEST;
gc.fill = GridBagConstraints.HORIZONTAL; // <<< ajouter
gc.weightx = 1.0;  

        JLabel lblPatient = new JLabel("Patient :");
        JLabel lblMedecin = new JLabel("Médecin :");
        JLabel lblDate = new JLabel("Date & heure (yyyy-MM-dd HH:mm:ss) :");
        JLabel lblMotif = new JLabel("Motif :");
        JLabel lblStatut = new JLabel("Statut :");

        cbPatient = new JComboBox<>();
        cbMedecin = new JComboBox<>();
        cbStatut = new JComboBox<>();

        txtDateHeure = new JTextField(20);
        txtMotif = new JTextField(20);

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnRecharger = new JButton("Recharger");

        // ligne 0
        gc.gridx = 0; gc.gridy = 0;
        formPanel.add(lblPatient, gc);
        gc.gridx = 1;
        formPanel.add(cbPatient, gc);

        // ligne 1
        gc.gridx = 0; gc.gridy = 1;
        formPanel.add(lblMedecin, gc);
        gc.gridx = 1;
        formPanel.add(cbMedecin, gc);

        // ligne 2
        gc.gridx = 0; gc.gridy = 2;
        formPanel.add(lblDate, gc);
        gc.gridx = 1;
        formPanel.add(txtDateHeure, gc);

        // ligne 3
        gc.gridx = 0; gc.gridy = 3;
        formPanel.add(lblMotif, gc);
        gc.gridx = 1;
        formPanel.add(txtMotif, gc);

        // ligne 4
        gc.gridx = 0; gc.gridy = 4;
        formPanel.add(lblStatut, gc);
        gc.gridx = 1;
        formPanel.add(cbStatut, gc);

        // ligne 5 : boutons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(btnAjouter);
        btnPanel.add(btnRecharger);

        gc.gridx = 0; gc.gridy = 5; gc.gridwidth = 2;
        formPanel.add(btnPanel, gc);

        // tableau (droite)
        String[] colonnes = {"ID", "Date & heure", "Patient", "Médecin", "Statut", "Motif"};
        tableModel = new DefaultTableModel(colonnes, 0);
        tblRdv = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(tblRdv);

        // layout principal
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, scroll);
        split.setDividerLocation(350);
        getContentPane().add(split, BorderLayout.CENTER);

        // actions
        btnAjouter.addActionListener(e -> ajouterRendezVous());
        btnRecharger.addActionListener(e -> chargerRendezVous());
    }

    // ===================== LOGIQUE ======================

    private void remplirCombos() {
        try {
            cbPatient.removeAllItems();
            mapPatients.clear();
            for (Patient p : patientDAO.findAll()) {
                cbPatient.addItem(p.getId() + " - " + p.getNom());
                mapPatients.put(p.getId(), p.getNom());
            }

            cbMedecin.removeAllItems();
            mapMedecins.clear();
            for (Medecin m : medecinDAO.findAll()) {
                cbMedecin.addItem(m.getId() + " - " + m.getNom());
                mapMedecins.put(m.getId(), m.getNom());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur chargement patients/médecins : " + e.getMessage());
        }
    }

    private void remplirComboStatut() {
        cbStatut.removeAllItems();
        cbStatut.addItem("PLANIFIE");
        cbStatut.addItem("EN_ATTENTE");
        cbStatut.addItem("TERMINE");
        cbStatut.addItem("ANNULE");
    }

    private int getIdFromCombo(JComboBox<String> combo) {
        String item = (String) combo.getSelectedItem();
        if (item == null || item.isEmpty()) return 0;
        return Integer.parseInt(item.split(" - ")[0]);
    }

    private void chargerRendezVous() {
        try {
            tableModel.setRowCount(0);

            for (RendezVous r : rdvDAO.findAll()) {
                String patientNom = mapPatients.getOrDefault(r.getIdPatient(),
                        "(" + r.getIdPatient() + ")");
                String medecinNom = mapMedecins.getOrDefault(r.getIdMedecin(),
                        "(" + r.getIdMedecin() + ")");

                tableModel.addRow(new Object[]{
                        r.getId(),
                        sdf.format(r.getDateHeure()),
                        patientNom,
                        medecinNom,
                        r.getStatut(),
                        r.getMotif()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur chargement rendez-vous : " + e.getMessage());
        }
    }

    private void ajouterRendezVous() {
        try {
            String dateStr = txtDateHeure.getText().trim();
            String motif = txtMotif.getText().trim();
            String statut = (String) cbStatut.getSelectedItem();

            if (dateStr.isEmpty() || motif.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Date et motif sont obligatoires");
                return;
            }

            Date date;
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Format de date invalide. Exemple : 2025-12-04 18:30:00");
                return;
            }

            int idPatient = getIdFromCombo(cbPatient);
            int idMedecin = getIdFromCombo(cbMedecin);

            RendezVous rdv = new RendezVous(date, statut, motif, idPatient, idMedecin);
            rdvDAO.ajouter(rdv);

            JOptionPane.showMessageDialog(this, "Rendez-vous ajouté !");
            chargerRendezVous();

            txtDateHeure.setText("");
            txtMotif.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur ajout rendez-vous : " + e.getMessage());
        }
    }
}
