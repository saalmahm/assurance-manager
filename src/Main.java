import dao.DatabaseConnection;
import view.ClientView;
import view.ConseillerView;
import view.ContratView;
import view.SinistreView;

import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ConseillerView conseillerView = new ConseillerView();
    private ClientView clientView = new ClientView();
    private ContratView contratView = new ContratView();
    private SinistreView sinistreView = new SinistreView();

    public void afficherMenuPrincipal() {
        while (true) {
            System.out.println("\n=== SYSTÈME DE GESTION D'ASSURANCE ===");
            System.out.println("1. Gérer les conseillers");
            System.out.println("2. Gérer les clients");
            System.out.println("3. Gérer les contrats");
            System.out.println("4. Gérer les sinistres");
            System.out.println("0. Quitter");
            System.out.print("Votre choix: ");

            int choix = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choix) {
                case 1:
                    conseillerView.afficherMenu();
                    break;
                case 2:
                    clientView.afficherMenu();
                    break;
                case 3:
                    contratView.afficherMenu();
                    break;
                case 4:
                    sinistreView.afficherMenu();
                    break;
                case 0:
                    System.out.println("Au revoir!");
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }

    public static void main(String[] args) {

        try {
            // Test database connection
            DatabaseConnection.getConnection();

            // Start application
            Main mainView = new Main();
            mainView.afficherMenuPrincipal();

        } catch (Exception e) {
            System.err.println("Erreur de démarrage: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
        }
    }
}
