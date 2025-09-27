package dao;

import model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public void save(Client client) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO clients (id, nom, prenom, email, conseiller_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, client.getId());
            stmt.setString(2, client.getNom());
            stmt.setString(3, client.getPrenom());
            stmt.setString(4, client.getEmail());
            stmt.setInt(5, client.getConseillerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM clients";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getInt("conseiller_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public Client findById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM clients WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getInt("conseiller_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM clients WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}