package dao;

import model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDAO {

    public boolean addClient(Client client) {
        String sqlPersonne = "INSERT INTO personne (nom, prenom, email) VALUES (?, ?, ?) RETURNING id";
        String sqlClient = "INSERT INTO client (personne_id, conseiller_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement stmtPersonne = conn.prepareStatement(sqlPersonne);
            stmtPersonne.setString(1, client.getNom());
            stmtPersonne.setString(2, client.getPrenom());
            stmtPersonne.setString(3, client.getEmail());
            ResultSet rs = stmtPersonne.executeQuery();
            if (rs.next()) client.setId(rs.getInt(1));
            else { conn.rollback(); return false; }

            PreparedStatement stmtClient = conn.prepareStatement(sqlClient);
            stmtClient.setInt(1, client.getId());
            if (client.getConseillerId() != null) stmtClient.setInt(2, client.getConseillerId());
            else stmtClient.setNull(2, Types.INTEGER);
            stmtClient.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur ajout client: " + e.getMessage());
            return false;
        }
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT c.id, p.nom, p.prenom, p.email, c.conseiller_id " +
                "FROM client c JOIN personne p ON c.personne_id = p.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                int conseillerId = rs.getInt("conseiller_id");
                if (!rs.wasNull()) c.setConseillerId(conseillerId);
                clients.add(c);
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return clients;
    }

    public Optional<Client> getClientById(int id) {
        String sql = "SELECT c.id, p.nom, p.prenom, p.email, c.conseiller_id " +
                "FROM client c JOIN personne p ON c.personne_id = p.id WHERE c.id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                int conseillerId = rs.getInt("conseiller_id");
                if (!rs.wasNull()) c.setConseillerId(conseillerId);
                return Optional.of(c);
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return Optional.empty();
    }

    public List<Client> getClientsByConseillerId(int conseillerId) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT c.id, p.nom, p.prenom, p.email, c.conseiller_id " +
                "FROM client c JOIN personne p ON c.personne_id = p.id " +
                "WHERE c.conseiller_id=? ORDER BY p.nom";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, conseillerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                c.setConseillerId(rs.getInt("conseiller_id"));
                clients.add(c);
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return clients;
    }

    public boolean deleteClientById(int id) {
        String sqlClient = "DELETE FROM client WHERE id=?";
        String sqlPersonne = "DELETE FROM personne WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stmtClient = conn.prepareStatement(sqlClient);
            stmtClient.setInt(1, id);
            int affected = stmtClient.executeUpdate();
            if (affected == 0) { conn.rollback(); return false; }
            PreparedStatement stmtPersonne = conn.prepareStatement(sqlPersonne);
            stmtPersonne.setInt(1, id);
            stmtPersonne.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) { System.err.println(e.getMessage()); return false; }
    }

    public boolean updateClientConseiller(Client client) {
        String sql = "UPDATE client SET conseiller_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (client.getConseillerId() != null) {
                stmt.setInt(1, client.getConseillerId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }

            stmt.setInt(2, client.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur update client conseiller : " + e.getMessage());
            return false;
        }
    }

}
