/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.cabinet.model.Consultation;
import ma.cabinet.util.DBConnection;

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

        String sql = "SELECT * FROM consultation";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Consultation c = new Consultation(
                        rs.getInt("id"),
                        new Date(rs.getTimestamp("date_consultation").getTime()),
                        rs.getString("description"),
                        rs.getDouble("prix_consultation"),
                        rs.getInt("id_patient"),
                        rs.getInt("id_medecin"),
                        rs.getInt("id_categorie"),
                        rs.getInt("id_rendezvous")
                );
                liste.add(c);
            }
        }

        return liste;
    }
    public List<Consultation> findByMedecinAndDate(int idMedecin, java.sql.Date jour) throws SQLException {
    List<Consultation> liste = new ArrayList<>();

    String sql = "SELECT * FROM consultation WHERE id_medecin = ? AND DATE(date_consultation) = ?";
    try (Connection cnx = DBConnection.getConnection();
         PreparedStatement ps = cnx.prepareStatement(sql)) {

        ps.setInt(1, idMedecin);
        ps.setDate(2, jour);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Consultation c = new Consultation(
                    rs.getInt("id"),
                    rs.getDate("date_consultation"),
                    rs.getString("description"),
                    rs.getDouble("prix"),
                    rs.getInt("id_patient"),
                    rs.getInt("id_medecin"),
                    rs.getInt("id_categorie"),
                    rs.getInt("id_rendezvous")
                );
                liste.add(c);
            }
        }
    }
    return liste;
}
}