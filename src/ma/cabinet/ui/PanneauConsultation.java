 
package ma.cabinet.ui;

import ma.cabinet.dao.*;
import ma.cabinet.model.*;
import ma.cabinet.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

public class PanneauConsultation extends JPanel {
    private DefaultTableModel model;

    private JTextField txtDate = new JTextField();
    private JTextField txtDesc = new JTextField();
    private JTextField txtPrix = new JTextField();
    private JComboBox<Patient> cbPatient = new JComboBox<>();
    private JComboBox<Medecin> cbMedecin = new JComboBox<>();
    private JComboBox<CategorieConsultation> cbCategorie = new JComboBox<>();
    private JComboBox<RendezVous> cbRdv = new JComboBox<>(); 

    private ConsultationDAO consultDAO = new ConsultationDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private MedecinDAO medecinDAO = new MedecinDAO();
    private CategorieConsultationDAO catDAO = new CategorieConsultationDAO();
    private RendezVousDAO rdvDAO = new RendezVousDAO();

    public PanneauConsultation() {
        setLayout(new BorderLayout());

        
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        JPanel form = new JPanel(new GridLayout(7, 2));
        form.setBorder(BorderFactory.createTitledBorder("Nouvelle Consultation"));
        form.add(new JLabel("Date (yyyy-MM-dd) :")); form.add(txtDate);
        form.add(new JLabel("Description :")); form.add(txtDesc);
        form.add(new JLabel("Prix :")); form.add(txtPrix);
        form.add(new JLabel("Patient :")); form.add(cbPatient);
        form.add(new JLabel("Médecin :")); form.add(cbMedecin);
        form.add(new JLabel("Catégorie :")); form.add(cbCategorie);
        form.add(new JLabel("Rendez-vous associé :")); form.add(cbRdv);

        JButton btn = new JButton("Enregistrer Consultation");
        btn.addActionListener(e -> enregistrer());

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(btn, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Date", "Desc", "Prix", "ID Patient", "ID Medecin"}, 0
        );
        add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);

        charger();
    }

    private void charger() {
        try {
            
            cbPatient.removeAllItems();
            for (Patient p : patientDAO.findAll()) cbPatient.addItem(p);

            cbMedecin.removeAllItems();
            for (Medecin m : medecinDAO.findAll()) cbMedecin.addItem(m);

            cbCategorie.removeAllItems();
            for (CategorieConsultation c : catDAO.findAll()) cbCategorie.addItem(c);

            cbRdv.removeAllItems();
            for (RendezVous r : rdvDAO.findAll()) cbRdv.addItem(r);

            
            model.setRowCount(0);

            Utilisateur u = Session.getCurrentUser();
            List<Consultation> data = consultDAO.findAll(); 

            for (Consultation c : data) {
                
                if (u instanceof Medecin) {
                    if (c.getIdMedecin() != u.getId()) {
                        continue; 
                    }
                }

                model.addRow(new Object[]{
                        c.getId(),
                        c.getDateConsultation(),
                        c.getDescription(),
                        c.getPrixConsultation(),
                        c.getIdPatient(),
                        c.getIdMedecin()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enregistrer() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Consultation c = new Consultation(
                    sdf.parse(txtDate.getText()),
                    txtDesc.getText(),
                    Double.parseDouble(txtPrix.getText()),
                    ((Patient) cbPatient.getSelectedItem()).getId(),
                    ((Medecin) cbMedecin.getSelectedItem()).getId(),
                    ((CategorieConsultation) cbCategorie.getSelectedItem()).getId(),
                    ((RendezVous) cbRdv.getSelectedItem()).getId()
            );
            consultDAO.ajouter(c);
            JOptionPane.showMessageDialog(this, "Consultation enregistrée !");
            charger();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}