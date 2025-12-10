 
package ma.cabinet.ui;

import ma.cabinet.dao.PaiementDAO;
import ma.cabinet.model.Paiement;
import ma.cabinet.model.Utilisateur;
import ma.cabinet.util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class PanneauPaiement extends JPanel {
    private DefaultTableModel model;
    private JTextField txtMontant = new JTextField();
    private JTextField txtIdConsult = new JTextField(); 
    private JComboBox<String> cbMode = new JComboBox<>(new String[]{"ESPECES", "CARTE", "CHEQUE"});
    private JComboBox<String> cbStatut = new JComboBox<>(new String[]{"EN_ATTENTE", "VALIDE", "ANNULE"});

    private PaiementDAO dao = new PaiementDAO();

    public PanneauPaiement() {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2));
        form.setBorder(BorderFactory.createTitledBorder("Nouveau Paiement"));
        form.add(new JLabel("Montant :"));        form.add(txtMontant);
        form.add(new JLabel("ID Consultation :")); form.add(txtIdConsult);
        form.add(new JLabel("Mode :"));           form.add(cbMode);
        form.add(new JLabel("Statut :"));         form.add(cbStatut);

        JButton btn = new JButton("Enregistrer Paiement");
        btn.addActionListener(e -> ajouter());

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(btn, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Date", "Montant", "Mode", "Statut"}, 0);
        add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);

        refresh();
    }

    private void refresh() {
        try {
            model.setRowCount(0);
            for (Paiement p : dao.findAll()) {
                model.addRow(new Object[]{
                        p.getId(),
                        p.getDatePaiement(),
                        p.getMontant(),
                        p.getModePaiement(),
                        p.getStatut()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ajouter() {
        try {
            
            Utilisateur current = Session.getCurrentUser();
            if (current == null) {
                JOptionPane.showMessageDialog(this,
                        "Aucun utilisateur connecté. Merci de vous reconnecter.");
                return;
            }
            int idAssistant = current.getId();   

            
            double montant = Double.parseDouble(txtMontant.getText().trim());
            int idConsult = Integer.parseInt(txtIdConsult.getText().trim());
            String mode = cbMode.getSelectedItem().toString();
            String statut = cbStatut.getSelectedItem().toString();

            
            Paiement p = new Paiement(
                    new Date(),     
                    montant,
                    mode,
                    statut,
                    idConsult,
                    idAssistant
            );

            
            dao.ajouter(p);
            refresh();
            JOptionPane.showMessageDialog(this, "Paiement validé !");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    "Montant ou ID consultation invalide.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur: " + e.getMessage());
        }
    }
}
