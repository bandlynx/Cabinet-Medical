 
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

    
    private JTextField txtDateHeure = new JTextField("2025-01-01 10:00");
    private JComboBox<String> cbStatut =
            new JComboBox<>(new String[]{"PLANIFIE", "ANNULE"});
    private JTextField txtMotif = new JTextField();
    private JComboBox<Patient> cbPatient = new JComboBox<>();
    private JComboBox<Medecin> cbMedecin = new JComboBox<>();

    
    private DefaultTableModel model;
    private JTable table;

    
    private RendezVousDAO rdvDAO = new RendezVousDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private MedecinDAO medecinDAO = new MedecinDAO();

    
    private List<RendezVous> listeRdv = new ArrayList<>();

    
    private Integer idSelectionne = null;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public PanneauRendezVous() {
        setLayout(new BorderLayout());

        
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

        
        model = new DefaultTableModel(
                new String[]{"ID", "Date", "Statut", "Motif", "ID Patient", "ID Médecin"}, 0
        );
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        
        table.getSelectionModel().addListSelectionListener(selectionListener());

        add(new JScrollPane(table), BorderLayout.CENTER);

        
        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnAnnuler.addActionListener(e -> annuler());
        btnRecharger.addActionListener(e -> charger());

        
        charger();
    }

    
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

    
    private void remplirFormulaire(RendezVous r) {
        txtDateHeure.setText(sdf.format(r.getDateHeure()));
        cbStatut.setSelectedItem(r.getStatut());
        txtMotif.setText(r.getMotif());

        
        for (int i = 0; i < cbPatient.getItemCount(); i++) {
            if (cbPatient.getItemAt(i).getId() == r.getIdPatient()) {
                cbPatient.setSelectedIndex(i);
                break;
            }
        }

        
        for (int i = 0; i < cbMedecin.getItemCount(); i++) {
            if (cbMedecin.getItemAt(i).getId() == r.getIdMedecin()) {
                cbMedecin.setSelectedIndex(i);
                break;
            }
        }
    }

    
    private void charger() {
        try {
            
            cbPatient.removeAllItems();
            for (Patient p : patientDAO.findAll()) {
                cbPatient.addItem(p);
            }

            
            cbMedecin.removeAllItems();
            for (Medecin m : medecinDAO.findAll()) {
                cbMedecin.addItem(m);
            }

            
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
