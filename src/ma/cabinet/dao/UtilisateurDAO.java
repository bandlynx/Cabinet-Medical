/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.dao;

import ma.cabinet.model.Assistant;
import ma.cabinet.model.Medecin;
import ma.cabinet.model.Utilisateur;
import ma.cabinet.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAO {

    public Utilisateur authenticate(String login, String password) throws SQLException {

        Connection conn = DBConnection.getConnection();

        // 1) Vérifier Assistant
        String sqlAssistant = """
            SELECT id, login, password, nom 
            FROM assistant 
            WHERE login = ? AND password = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sqlAssistant)) {
            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Assistant(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("nom")
                );
            }
        }

        // 2) Vérifier Medecin
        String sqlMedecin = """
            SELECT id, login, password, nom, specialite 
            FROM medecin 
            WHERE login = ? AND password = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sqlMedecin)) {
            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Medecin(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("nom"),
                        rs.getString("specialite")
                );
            }
        }

        return null; // Aucun utilisateur trouvé
    }

    public String detectRole(int userId) throws SQLException {

        Connection conn = DBConnection.getConnection();

        // Assistant ?
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM assistant WHERE id = ?")) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return "ASSISTANT";
        }

        // Medecin ?
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM medecin WHERE id = ?")) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return "MEDECIN";
        }

        return "INCONNU";
    }
}
