 
package ma.cabinet.dao;

import ma.cabinet.model.RendezVous;
import ma.cabinet.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAO {

    
    
    
    public void ajouter(RendezVous r) throws SQLException {
        String sql = "INSERT INTO rendezvous (date_heure, statut, motif, id_patient, id_medecin) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(r.getDateHeure().getTime()));
            ps.setString(2, r.getStatut());
            ps.setString(3, r.getMotif());
            ps.setInt(4, r.getIdPatient());
            ps.setInt(5, r.getIdMedecin());

            ps.executeUpdate();
        }
    }

    
    
    
    public List<RendezVous> findAll() throws SQLException {
        List<RendezVous> liste = new ArrayList<>();

        String sql = "SELECT * FROM rendezvous ORDER BY date_heure";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                RendezVous r = new RendezVous(
                        rs.getInt("id"),
                        rs.getTimestamp("date_heure"),
                        rs.getString("statut"),
                        rs.getString("motif"),
                        rs.getInt("id_patient"),
                        rs.getInt("id_medecin")
                );
                liste.add(r);
            }
        }

        return liste;
    }

    
    
    
    public void update(RendezVous r) throws SQLException {
        String sql = "UPDATE rendezvous " +
                     "SET date_heure = ?, statut = ?, motif = ?, id_patient = ?, id_medecin = ? " +
                     "WHERE id = ?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(r.getDateHeure().getTime()));
            ps.setString(2, r.getStatut());
            ps.setString(3, r.getMotif());
            ps.setInt(4, r.getIdPatient());
            ps.setInt(5, r.getIdMedecin());
            ps.setInt(6, r.getId());

            ps.executeUpdate();
        }
    }

    
    
    
    public void annuler(int idRdv) throws SQLException {
        String sql = "UPDATE rendezvous SET statut = 'ANNULE' WHERE id = ?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, idRdv);
            ps.executeUpdate();
        }
    }

    
    public void delete(int idRdv) throws SQLException {
        String sql = "DELETE FROM rendezvous WHERE id = ?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, idRdv);
            ps.executeUpdate();
        }
    }
}
