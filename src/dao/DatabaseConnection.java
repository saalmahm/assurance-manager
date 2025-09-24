package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // URL de connexion JDBC
    private static final String URL = "jdbc:postgresql://localhost:5432/assurance-manager";
    private static final String USER = "postgres";
    private static final String PASSWORD = "salmahm";

    public static void main(String[] args) {
        try {
            // Établir la connexion
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Connexion réussie à PostgreSQL !");

            // Fermer la connexion
            conn.close();
        } catch (SQLException e) {
            System.out.println(" Erreur de connexion : " + e.getMessage());
        }
    }
}
