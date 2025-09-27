package dao;

import model.Contrat;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContratDAO {

    public boolean addContrat(Contrat contrat) {
        String sql = "INSERT INTO contrat (type_contrat, date_debut, date_fin, client_id) " +
                "VALUES (?::type_contrat, ?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contrat.getTypeContrat().name());
            stmt.setDate(2, Date.valueOf(contrat.getDateDebut()));
            stmt.setDate(3, Date.valueOf(contrat.getDateFin()));
            stmt.setInt(4, contrat.getClient().getId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                contrat.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du contrat: " + e.getMessage());
        }
        return false;
    }

    public Optional<Contrat> getContratById(int id) {
        String sql = "SELECT c.id, c.type_contrat, c.date_debut, c.date_fin, " +
                "cl.id AS client_id, p.nom, p.prenom, p.email " +
                "FROM contrat c " +
                "JOIN client cl ON c.client_id = cl.id " +
                "JOIN personne p ON cl.personne_id = p.id " +
                "WHERE c.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Contrat contrat = new Contrat();
                contrat.setId(rs.getInt("id"));
                contrat.setTypeContrat(model.TypeContrat.valueOf(rs.getString("type_contrat")));
                contrat.setDateDebut(rs.getDate("date_debut").toLocalDate());
                contrat.setDateFin(rs.getDate("date_fin").toLocalDate());

                Client client = new Client();
                client.setId(rs.getInt("client_id"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setEmail(rs.getString("email"));

                contrat.setClient(client);
                return Optional.of(contrat);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du contrat: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean deleteContratById(int id) {
        String sql = "DELETE FROM contrat WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du contrat: " + e.getMessage());
        }
        return false;
    }

    public List<Contrat> getContratsByClientId(int clientId) {
        List<Contrat> list = new ArrayList<>();
        String sql = "SELECT c.id, c.type_contrat, c.date_debut, c.date_fin, " +
                "p.nom, p.prenom, p.email " +
                "FROM contrat c " +
                "JOIN client cl ON c.client_id = cl.id " +
                "JOIN personne p ON cl.personne_id = p.id " +
                "WHERE cl.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Contrat contrat = new Contrat();
                contrat.setId(rs.getInt("id"));
                contrat.setTypeContrat(model.TypeContrat.valueOf(rs.getString("type_contrat")));
                contrat.setDateDebut(rs.getDate("date_debut").toLocalDate());
                contrat.setDateFin(rs.getDate("date_fin").toLocalDate());

                Client client = new Client();
                client.setId(clientId);
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setEmail(rs.getString("email"));

                contrat.setClient(client);
                list.add(contrat);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des contrats: " + e.getMessage());
        }
        return list;
    }
}
