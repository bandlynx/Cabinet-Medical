 
package ma.cabinet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ma.cabinet.model.Medecin;
import ma.cabinet.util.DBConnection;

public class MedecinDAO {

    public void ajouter(Medecin m) throws SQLException {
        
        String sqlUser = "INSERT INTO utilisateur (login, password) VALUES (?, ?)";
        String sqlMed = "INSERT INTO medecin (id, nom, specialite) VALUES (?, ?, ?)";

        try (Connection cnx = DBConnection.getConnection()) {

            
            cnx.setAutoCommit(false);

            try (PreparedStatement psUser = cnx.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {

                psUser.setString(1, m.getLogin());
                psUser.setString(2, m.getPassword());
                psUser.executeUpdate();

                
                int idGenere;
                try (ResultSet rs = psUser.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenere = rs.getInt(1);
                    } else {
                        cnx.rollback();
                        throw new SQLException("Impossible de récupérer l'id utilisateur généré");
                    }
                }

                
                try (PreparedStatement psMed = cnx.prepareStatement(sqlMed)) {
                    psMed.setInt(1, idGenere);
                    psMed.setString(2, m.getNom());
                    psMed.setString(3, m.getSpecialite());
                    psMed.executeUpdate();
                }

                cnx.commit();
            } catch (SQLException e) {
                cnx.rollback();
                throw e;
            } finally {
                cnx.setAutoCommit(true);
            }
        }
    }

    public List<Medecin> findAll() throws SQLException {
        List<Medecin> liste = new ArrayList<>();

        String sql = """
            SELECT u.id, u.login, u.password, m.nom, m.specialite
            FROM utilisateur u
            JOIN medecin m ON u.id = m.id
            """;

        try (Connection cnx = DBConnection.getConnection();
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Medecin m = new Medecin(
    rs.getInt("id"),
    rs.getString("login"),
    rs.getString("password"),
    rs.getString("nom"),          
    rs.getString("specialite")
);
                liste.add(m);
            }
        }

        return liste;
    }
}
