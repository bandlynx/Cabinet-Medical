/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ma.cabinet.model.CategorieConsultation;
import ma.cabinet.util.DBConnection;

public class CategorieConsultationDAO {

    public void ajouter(CategorieConsultation c) throws SQLException {
        String sql = "INSERT INTO categorie_consultation (designation, description) VALUES (?, ?)";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, c.getDesignation());
            ps.setString(2, c.getDescription());
            ps.executeUpdate();
        }
    }

    public List<CategorieConsultation> findAll() throws SQLException {
        List<CategorieConsultation> liste = new ArrayList<>();
        String sql = "SELECT id, designation, description FROM categorie_consultation";

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                CategorieConsultation c = new CategorieConsultation(
                        rs.getInt("id"),
                        rs.getString("designation"),
                        rs.getString("description")
                );
                liste.add(c);
            }
        }

        return liste;
    }
}