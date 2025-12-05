/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.dao;
import ma.cabinet.model.BilanMensuel;
import ma.cabinet.model.BilanJour;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.cabinet.model.Paiement;
import ma.cabinet.util.DBConnection;

public class PaiementDAO {

    public void ajouter(Paiement p) throws SQLException {
        String sql = """
            INSERT INTO paiement
            (date_paiement, montant, mode_paiement, statut,
             id_consultation, id_assistant)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(p.getDatePaiement().getTime()));
            ps.setDouble(2, p.getMontant());
            ps.setString(3, p.getModePaiement());
            ps.setString(4, p.getStatut());
            ps.setInt(5, p.getIdConsultation());
            ps.setInt(6, p.getIdAssistant());

            ps.executeUpdate();
        }
    }

    public List<Paiement> findAll() throws SQLException {
        List<Paiement> liste = new ArrayList<>();

        String sql = "SELECT * FROM paiement";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Paiement p = new Paiement(
                        rs.getInt("id"),
                        new Date(rs.getTimestamp("date_paiement").getTime()),
                        rs.getDouble("montant"),
                        rs.getString("mode_paiement"),
                        rs.getString("statut"),
                        rs.getInt("id_consultation"),
                        rs.getInt("id_assistant")
                );
                liste.add(p);
            }
        }

        return liste;
    }
      public BilanMensuel getBilanMensuel(int annee, int mois) throws SQLException {
        String sql = """
            SELECT COUNT(*) AS nbConsult,
                   COALESCE(SUM(montant), 0) AS ca
            FROM paiement
            WHERE statut = 'VALIDE'
              AND YEAR(date_paiement) = ?
              AND MONTH(date_paiement) = ?
            """;

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, annee);
            ps.setInt(2, mois);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int nbConsult = rs.getInt("nbConsult");
                    double ca = rs.getDouble("ca");
                    return new BilanMensuel(annee, mois, nbConsult, ca);
                }
            }
        }
        return new BilanMensuel(annee, mois, 0, 0.0);
    }

    /**
     * Détail jour par jour : évolution du nombre de consultations
     * et du chiffre d'affaires dans le mois.
     */
    public List<BilanJour> getDetailsBilanMensuel(int annee, int mois) throws SQLException {
        List<BilanJour> liste = new ArrayList<>();

        String sql = """
            SELECT DATE(date_paiement) AS jour,
                   COUNT(*) AS nbConsult,
                   COALESCE(SUM(montant), 0) AS ca
            FROM paiement
            WHERE statut = 'VALIDE'
              AND YEAR(date_paiement) = ?
              AND MONTH(date_paiement) = ?
            GROUP BY DATE(date_paiement)
            ORDER BY jour
            """;

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, annee);
            ps.setInt(2, mois);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String jour = rs.getString("jour"); // "2025-12-05"
                    int nb = rs.getInt("nbConsult");
                    double ca = rs.getDouble("ca");
                    liste.add(new BilanJour(jour, nb, ca));
                }
            }
        }

        return liste;
    }
}
