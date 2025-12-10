 
package ma.cabinet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ma.cabinet.model.Assistant;
import ma.cabinet.util.DBConnection;

public class AssistantDAO {

     
    public void ajouter(Assistant a) throws SQLException {
        String sql = "INSERT INTO assistant (login, password, nom) VALUES (?, ?, ?)";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, a.getLogin());
            ps.setString(2, a.getPassword());
            ps.setString(3, a.getNom());

            ps.executeUpdate();
        }
    }

     
    public List<Assistant> findAll() throws SQLException {
        List<Assistant> liste = new ArrayList<>();

        String sql = "SELECT id, login, password, nom FROM assistant";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Assistant a = new Assistant(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("nom")
                );
                liste.add(a);
            }
        }

        return liste;
    }
}
