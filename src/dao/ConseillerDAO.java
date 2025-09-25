package dao;

import model.Conseiller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConseillerDAO {

    // Ajouter un conseiller (insère la personne puis le conseiller)
    public void addConseiller(Conseiller conseiller) {
        String sqlPersonne = "INSERT INTO personne (nom, prenom, email) VALUES (?, ?, ?) RETURNING id";
        String sqlConseiller = "INSERT INTO conseiller (personne_id) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Insérer la personne
            PreparedStatement stmtPersonne = conn.prepareStatement(sqlPersonne);
            stmtPersonne.setString(1, conseiller.getNom());
            stmtPersonne.setString(2, conseiller.getPrenom());
            stmtPersonne.setString(3, conseiller.getEmail());

            ResultSet rs = stmtPersonne.executeQuery();
            if (rs.next()) {
                int personneId = rs.getInt(1);
                conseiller.setId(personneId);
            }

            // Insérer le conseiller
            PreparedStatement stmtConseiller = conn.prepareStatement(sqlConseiller);
            stmtConseiller.setInt(1, conseiller.getId());
            stmtConseiller.executeUpdate();

            System.out.println("Conseiller ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer un conseiller par ID
    public boolean deleteConseillerById(int id) {
        String sql = "DELETE FROM conseiller WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer tous les conseillers
    public List<Conseiller> getAllConseillers() {
        List<Conseiller> conseillers = new ArrayList<>();
        String sql = "SELECT c.id, p.nom, p.prenom, p.email " +
                "FROM conseiller c JOIN personne p ON c.personne_id = p.id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Conseiller c = new Conseiller();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                conseillers.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conseillers;
    }
}
