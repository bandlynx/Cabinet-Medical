 
package ma.cabinet.dao;

import ma.cabinet.model.Consultation;
import ma.cabinet.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDAO {

    
    public void ajouter(Consultation c) throws SQLException {
        String sql = """
            INSERT INTO consultation
            (date_consultation, description, prix_consultation,
             id_patient, id_medecin, id_categorie, id_rendezvous)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(c.getDateConsultation().getTime()));
            ps.setString(2, c.getDescription());
            ps.setDouble(3, c.getPrixConsultation());
            ps.setInt(4, c.getIdPatient());
            ps.setInt(5, c.getIdMedecin());
            ps.setInt(6, c.getIdCategorie());
            ps.setInt(7, c.getIdRendezVous());

            ps.executeUpdate();
        }
    }

    
    public List<Consultation> findAll() throws SQLException {
        List<Consultation> liste = new ArrayList<>();

        String sql = "SELECT * FROM consultation ORDER BY date_consultation DESC";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                liste.add(mapRow(rs));
            }
        }

        return liste;
    }

    
    public List<Consultation> findByMedecinAndDate(int idMedecin, java.sql.Date jour) throws SQLException {
        List<Consultation> liste = new ArrayList<>();

        String sql = """
            SELECT *
            FROM consultation
            WHERE id_medecin = ?
              AND DATE(date_consultation) = ?
            ORDER BY date_consultation
            """;

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, idMedecin);
            ps.setDate(2, jour);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(mapRow(rs));
                }
            }
        }

        return liste;
    }

    
    private Consultation mapRow(ResultSet rs) throws SQLException {
        Consultation c = new Consultation();

        c.setId(rs.getInt("id"));
        c.setDateConsultation(rs.getTimestamp("date_consultation"));
        c.setDescription(rs.getString("description"));
        c.setPrixConsultation(rs.getDouble("prix_consultation"));
        c.setIdPatient(rs.getInt("id_patient"));
        c.setIdMedecin(rs.getInt("id_medecin"));
        c.setIdCategorie(rs.getInt("id_categorie"));
        c.setIdRendezVous(rs.getInt("id_rendezvous"));

        return c;
    }
}

