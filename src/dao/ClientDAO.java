package dao;

import model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public void addClient(Client client) {
        String sqlPersonne = "INSERT INTO personne (nom, prenom, email) VALUES (?, ?, ?) RETURNING id";
        String sqlClient = "INSERT INTO client (personne_id, conseiller_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Insérer la personne
            PreparedStatement stmtPersonne = conn.prepareStatement(sqlPersonne);
            stmtPersonne.setString(1, client.getNom());
            stmtPersonne.setString(2, client.getPrenom());
            stmtPersonne.setString(3, client.getEmail());

            ResultSet rs = stmtPersonne.executeQuery();
            if (rs.next()) {
                int personneId = rs.getInt(1);
                client.setId(personneId); // mettre à jour l'id du client
            }

            // Insérer le client avec personne_id et conseiller_id
            PreparedStatement stmtClient = conn.prepareStatement(sqlClient);
            stmtClient.setInt(1, client.getId());
            if (client.getConseiller() != null) {
                stmtClient.setInt(2, client.getConseillerId());
            } else {
                stmtClient.setNull(2, java.sql.Types.INTEGER);
            }
            stmtClient.executeUpdate();

            System.out.println("Client ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
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
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setEmail(rs.getString("email"));
                client.setConseillerId(rs.getInt("conseiller_id"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public void supprimerClientParId(int id) {
        String sql = "DELETE FROM client WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                System.out.println("Client supprimé avec succès !");
            } else {
                System.out.println("Aucun client trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getClientsByConseillerId(int conseillerId) {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT c.id, p.nom, p.prenom, p.email, c.conseiller_id " +
                "FROM client c JOIN personne p ON c.personne_id = p.id WHERE c.conseiller_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, conseillerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setEmail(rs.getString("email"));
                client.setConseillerId(rs.getInt("conseiller_id"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
