package dao;

import model.Conseiller;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConseillerDAO {

    public void save(Conseiller conseiller) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO conseillers (id, nom, prenom, email) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, conseiller.getId());
            stmt.setString(2, conseiller.getNom());
            stmt.setString(3, conseiller.getPrenom());
            stmt.setString(4, conseiller.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Conseiller> findAll() {
        List<Conseiller> conseillers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM conseillers";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                conseillers.add(new Conseiller(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conseillers;
    }

    public Conseiller findById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM conseillers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Conseiller(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM conseillers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}