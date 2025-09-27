package dao;

import model.Conseiller;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConseillerDAO {

    public boolean addConseiller(Conseiller c) {
        String sqlPersonne = "INSERT INTO personne (nom, prenom, email) VALUES (?, ?, ?) RETURNING id";
        String sqlConseiller = "INSERT INTO conseiller (personne_id) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement stmtPersonne = conn.prepareStatement(sqlPersonne);
            stmtPersonne.setString(1, c.getNom());
            stmtPersonne.setString(2, c.getPrenom());
            stmtPersonne.setString(3, c.getEmail());
            ResultSet rs = stmtPersonne.executeQuery();
            if (rs.next()) c.setId(rs.getInt(1));
            else { conn.rollback(); return false; }

            PreparedStatement stmtCons = conn.prepareStatement(sqlConseiller);
            stmtCons.setInt(1, c.getId());
            stmtCons.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) { System.err.println(e.getMessage()); return false; }
    }

    public List<Conseiller> getAllConseillers() {
        List<Conseiller> list = new ArrayList<>();
        String sql = "SELECT c.id, p.nom, p.prenom, p.email FROM conseiller c JOIN personne p ON c.personne_id = p.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Conseiller c = new Conseiller();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                list.add(c);
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return list;
    }

    public Optional<Conseiller> getConseillerById(int id) {
        String sql = "SELECT c.id, p.nom, p.prenom, p.email FROM conseiller c JOIN personne p ON c.personne_id = p.id WHERE c.id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Conseiller c = new Conseiller();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                return Optional.of(c);
            }
        } catch (SQLException e) { System.err.println(e.getMessage()); }
        return Optional.empty();
    }

    public boolean deleteConseillerById(int id) {
        String sqlCons = "DELETE FROM conseiller WHERE id=?";
        String sqlPers = "DELETE FROM personne WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stmtCons = conn.prepareStatement(sqlCons);
            stmtCons.setInt(1, id);
            int affected = stmtCons.executeUpdate();
            if (affected == 0) { conn.rollback(); return false; }
            PreparedStatement stmtPers = conn.prepareStatement(sqlPers);
            stmtPers.setInt(1, id);
            stmtPers.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) { System.err.println(e.getMessage()); return false; }
    }
}
