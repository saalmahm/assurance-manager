package dao;

import model.Sinistre;
import model.TypeSinistre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SinistreDAO {

    public void save(Sinistre sinistre) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO sinistres (id, type_sinistre, date_time, cout, description, contrat_id) VALUES (?, ?::type_sinistre, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, sinistre.getId());
            stmt.setString(2, sinistre.getTypeSinistre().name());
            stmt.setTimestamp(3, Timestamp.valueOf(sinistre.getDateTime()));
            stmt.setDouble(4, sinistre.getCout());
            stmt.setString(5, sinistre.getDescription());
            stmt.setInt(6, sinistre.getContratId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveWithAutoId(Sinistre sinistre) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO sinistres (type_sinistre, date_time, cout, description, contrat_id) VALUES (?::type_sinistre, ?, ?, ?, ?) RETURNING id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sinistre.getTypeSinistre().name());
            stmt.setTimestamp(2, Timestamp.valueOf(sinistre.getDateTime()));
            stmt.setDouble(3, sinistre.getCout());
            stmt.setString(4, sinistre.getDescription());
            stmt.setInt(5, sinistre.getContratId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sinistre.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Sinistre> findAll() {
        List<Sinistre> sinistres = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM sinistres ORDER BY date_time DESC";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                sinistres.add(new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getDouble("cout"),
                        rs.getString("description"),
                        rs.getInt("contrat_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sinistres;
    }

    public Sinistre findById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM sinistres WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getDouble("cout"),
                        rs.getString("description"),
                        rs.getInt("contrat_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM sinistres WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour trouver les sinistres d'un contrat
    public List<Sinistre> findByContratId(int contratId) {
        List<Sinistre> sinistres = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM sinistres WHERE contrat_id = ? ORDER BY date_time DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, contratId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sinistres.add(new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getDouble("cout"),
                        rs.getString("description"),
                        rs.getInt("contrat_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sinistres;
    }

    // Méthode pour trouver les sinistres par type
    public List<Sinistre> findByType(TypeSinistre typeSinistre) {
        List<Sinistre> sinistres = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM sinistres WHERE type_sinistre = ?::type_sinistre ORDER BY date_time DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, typeSinistre.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sinistres.add(new Sinistre(
                        rs.getInt("id"),
                        TypeSinistre.valueOf(rs.getString("type_sinistre")),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getDouble("cout"),
                        rs.getString("description"),
                        rs.getInt("contrat_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sinistres;
    }

    // Méthode pour calculer le coût total des sinistres d'un contrat
    public double calculateTotalCostByContrat(int contratId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT COALESCE(SUM(cout), 0) as total FROM sinistres WHERE contrat_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, contratId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Méthode pour mettre à jour un sinistre
    public void update(Sinistre sinistre) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE sinistres SET type_sinistre = ?::type_sinistre, date_time = ?, cout = ?, description = ?, contrat_id = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sinistre.getTypeSinistre().name());
            stmt.setTimestamp(2, Timestamp.valueOf(sinistre.getDateTime()));
            stmt.setDouble(3, sinistre.getCout());
            stmt.setString(4, sinistre.getDescription());
            stmt.setInt(5, sinistre.getContratId());
            stmt.setInt(6, sinistre.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}