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
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PanneauRendezVous extends JPanel {

    // Champs du formulaire
    private JTextField txtDateHeure = new JTextField("2025-01-01 10:00");
    private JComboBox<String> cbStatut =
            new JComboBox<>(new String[]{"PLANIFIE", "ANNULE"});
    private JTextField txtMotif = new JTextField();
    private JComboBox<Patient> cbPatient = new JComboBox<>();
    private JComboBox<Medecin> cbMedecin = new JComboBox<>();

    // Table + modèle
    private DefaultTableModel model;
    private JTable table;

    // DAO
    private RendezVousDAO rdvDAO = new RendezVousDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private MedecinDAO medecinDAO = new MedecinDAO();

    // Liste en mémoire pour retrouver facilement le RDV sélectionné
    private List<RendezVous> listeRdv = new ArrayList<>();

    // Id du RDV sélectionné (null si rien)
    private Integer idSelectionne = null;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public PanneauRendezVous() {
        setLayout(new BorderLayout());

        // ----- Formulaire haut -----
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Nouveau Rendez-vous"));

        form.add(new JLabel("Date (yyyy-MM-dd HH:mm) :"));
        form.add(txtDateHeure);

        form.add(new JLabel("Statut :"));
        form.add(cbStatut);

        form.add(new JLabel("Motif :"));
        form.add(txtMotif);

        form.add(new JLabel("Patient :"));
        form.add(cbPatient);

        form.add(new JLabel("Médecin :"));
        form.add(cbMedecin);

        // Boutons
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnAnnuler = new JButton("Annuler RDV");
        JButton btnRecharger = new JButton("Recharger");

        JPanel boutons = new JPanel();
        boutons.add(btnAjouter);
        boutons.add(btnModifier);
        boutons.add(btnAnnuler);
        boutons.add(btnRecharger);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(boutons, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);

        // ----- Tableau -----
        model = new DefaultTableModel(
                new String[]{"ID", "Date", "Statut", "Motif", "ID Patient", "ID Médecin"}, 0
        );
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Quand on sélectionne une ligne -> remplir le formulaire
        table.getSelectionModel().addListSelectionListener(selectionListener());

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Actions des boutons
        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnAnnuler.addActionListener(e -> annuler());
        btnRecharger.addActionListener(e -> charger());

        // Chargement initial
        charger();
    }

    // Listener de sélection dans la JTable
    private ListSelectionListener selectionListener() {
        return e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0 && row < listeRdv.size()) {
                    RendezVous r = listeRdv.get(row);
                    idSelectionne = r.getId();
                    remplirFormulaire(r);
                }
            }
        };
    }

    // Remplir les champs à partir d'un RDV
    private void remplirFormulaire(RendezVous r) {
        txtDateHeure.setText(sdf.format(r.getDateHeure()));
        cbStatut.setSelectedItem(r.getStatut());
        txtMotif.setText(r.getMotif());

        // Sélection du patient
        for (int i = 0; i < cbPatient.getItemCount(); i++) {
            if (cbPatient.getItemAt(i).getId() == r.getIdPatient()) {
                cbPatient.setSelectedIndex(i);
                break;
            }
        }

        // Sélection du médecin
        for (int i = 0; i < cbMedecin.getItemCount(); i++) {
            if (cbMedecin.getItemAt(i).getId() == r.getIdMedecin()) {
                cbMedecin.setSelectedIndex(i);
                break;
            }
        }
    }

    // Rechargement complet (patients, médecins, RDV)
    private void charger() {
        try {
            // Patients
            cbPatient.removeAllItems();
            for (Patient p : patientDAO.findAll()) {
                cbPatient.addItem(p);
            }

            // Médecins
            cbMedecin.removeAllItems();
            for (Medecin m : medecinDAO.findAll()) {
                cbMedecin.addItem(m);
            }

            // RDV
            listeRdv = rdvDAO.findAll();
            model.setRowCount(0);
            for (RendezVous r : listeRdv) {
                model.addRow(new Object[]{
                        r.getId(),
                        sdf.format(r.getDateHeure()),
                        r.getStatut(),
                        r.getMotif(),
                        r.getIdPatient(),
                        r.getIdMedecin()
                });
            }

            idSelectionne = null;
            table.clearSelection();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Ajout d'un nouveau RDV
    private void ajouter() {
        try {
            Date date = sdf.parse(txtDateHeure.getText().trim());
            String statut = cbStatut.getSelectedItem().toString();
            String motif = txtMotif.getText().trim();
            int idPatient = ((Patient) cbPatient.getSelectedItem()).getId();
            int idMedecin = ((Medecin) cbMedecin.getSelectedItem()).getId();

            RendezVous r = new RendezVous(date, statut, motif, idPatient, idMedecin);
            rdvDAO.ajouter(r);

            JOptionPane.showMessageDialog(this, "Rendez-vous ajouté !");
            charger();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur ajout RDV : " + ex.getMessage());
        }
    }

    // Modification d'un RDV existant
    private void modifier() {
        if (idSelectionne == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un rendez-vous dans le tableau.");
            return;
        }

        try {
            Date date = sdf.parse(txtDateHeure.getText().trim());
            String statut = cbStatut.getSelectedItem().toString();
            String motif = txtMotif.getText().trim();
            int idPatient = ((Patient) cbPatient.getSelectedItem()).getId();
            int idMedecin = ((Medecin) cbMedecin.getSelectedItem()).getId();

            RendezVous r = new RendezVous(idSelectionne, date, statut, motif, idPatient, idMedecin);
            rdvDAO.update(r);

            JOptionPane.showMessageDialog(this, "Rendez-vous modifié !");
            charger();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur modification RDV : " + ex.getMessage());
        }
    }

    // Annulation (changement de statut)
    private void annuler() {
        if (idSelectionne == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un rendez-vous à annuler.");
            return;
        }

        int rep = JOptionPane.showConfirmDialog(
                this,
                "Confirmer l'annulation du rendez-vous ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (rep != JOptionPane.YES_OPTION) return;

        try {
            rdvDAO.annuler(idSelectionne);
            JOptionPane.showMessageDialog(this, "Rendez-vous annulé.");
            charger();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur annulation RDV : " + ex.getMessage());
        }
    }
}
