 
 
package ma.cabinet.ui;

import ma.cabinet.dao.PaiementDAO;
import ma.cabinet.model.BilanMensuel;
import ma.cabinet.model.BilanJour;
import ma.cabinet.model.Medecin;           
import ma.cabinet.model.Utilisateur;       
import ma.cabinet.util.Session;            

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

        top.add(new JLabel());  
        top.add(btnCalculer);
        top.add(new JLabel());  
        top.add(new JLabel());  

        add(top, BorderLayout.NORTH);

        
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

            
            Utilisateur currentUser = Session.getCurrentUser();
            Integer idMedecin = null; 

            
            if (currentUser instanceof Medecin) {
                idMedecin = currentUser.getId();
            }

            
            
            
            BilanMensuel bilan = paiementDAO.getBilanMensuel(annee, mois, idMedecin);
            lblNbConsult.setText(String.valueOf(bilan.getNbConsultations()));
            lblCA.setText(String.format("%.2f", bilan.getChiffreAffaires()));

            
            modelDetails.setRowCount(0);
            List<BilanJour> details = paiementDAO.getDetailsBilanMensuel(annee, mois, idMedecin);
            
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
