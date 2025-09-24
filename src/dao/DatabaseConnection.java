package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/assurance_manager";
    private static final String USER = "postgres";
    private static final String PASSWORD = "salmahm";

    // Méthode utilitaire pour récupérer une connexion
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Connexion réussie à PostgreSQL !");
            return conn;
        } catch (SQLException e) {
            System.out.println(" Erreur de connexion à la base : " + e.getMessage());
            return null;
        }
    }
}
