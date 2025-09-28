package dao;

import model.Contrat;
import model.TypeContrat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratDAO {

    public void save(Contrat contrat) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO contrats (type_contrat, date_debut, date_fin, client_id) VALUES (?::type_contrat, ?, ?, ?) RETURNING id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, contrat.getTypeContrat().name());
            stmt.setDate(2, Date.valueOf(contrat.getDateDebut()));
            stmt.setDate(3, Date.valueOf(contrat.getDateFin()));
            stmt.setInt(4, contrat.getClientId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                contrat.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contrat> findAll() {
        List<Contrat> contrats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM contrats";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                contrats.add(new Contrat(
                        rs.getInt("id"),
                        TypeContrat.valueOf(rs.getString("type_contrat")),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getInt("client_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contrats;
    }

    public Contrat findById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM contrats WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Contrat(
                        rs.getInt("id"),
                        TypeContrat.valueOf(rs.getString("type_contrat")),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getInt("client_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM contrats WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contrat> findByClientId(int clientId) {
        List<Contrat> contrats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM contrats WHERE client_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                contrats.add(new Contrat(
                        rs.getInt("id"),
                        TypeContrat.valueOf(rs.getString("type_contrat")),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getInt("client_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contrats;
    }
}