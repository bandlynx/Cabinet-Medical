 
package ma.cabinet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ma.cabinet.model.Patient;
import ma.cabinet.util.DBConnection;

public class PatientDAO {

    public void ajouter(Patient p) throws SQLException {
        String sql = "INSERT INTO patient (nom, telephone, email) VALUES (?, ?, ?)";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, p.getNom());
            ps.setString(2, p.getTelephone());
            ps.setString(3, p.getEmail());

            ps.executeUpdate();
        }
    }

    public List<Patient> findAll() throws SQLException {
        List<Patient> liste = new ArrayList<>();
        String sql = "SELECT id, nom, telephone, email FROM patient";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("telephone"),
                        rs.getString("email")
                );
                liste.add(p);
            }
        }

        return liste;
    }
}
