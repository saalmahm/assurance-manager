package view;

import service.ContratService;
import model.Contrat;
import model.TypeContrat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class ContratView {
    private Scanner scanner = new Scanner(System.in);
    private ContratService contratService = new ContratService();

    public void afficherMenu() {
        while (true) {
            System.out.println("\n=== GESTION DES CONTRATS ===");
            System.out.println("1. Ajouter un contrat");
            System.out.println("2. Afficher un contrat par ID");
            System.out.println("3. Supprimer un contrat");
            System.out.println("4. Afficher les contrats d'un client");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix: ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterContrat();
                    break;
                case 2:
                    afficherContratParId();
                    break;
                case 3:
                    supprimerContrat();
                    break;
                case 4:
                    afficherContratsParClient();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }

    private void ajouterContrat() {
        System.out.println("Types de contrat disponibles:");
        for (TypeContrat type : TypeContrat.values()) {
            System.out.println("- " + type);
        }
        System.out.print("Type de contrat: ");
        String typeStr = scanner.nextLine().toUpperCase();

        try {
            TypeContrat type = TypeContrat.valueOf(typeStr);

            System.out.print("Date de début (YYYY-MM-DD): ");
            LocalDate dateDebut = LocalDate.parse(scanner.nextLine());
            System.out.print("Date de fin (YYYY-MM-DD): ");
            LocalDate dateFin = LocalDate.parse(scanner.nextLine());
            System.out.print("ID du client: ");
            int clientId = scanner.nextInt();

            contratService.ajouterContrat(type, dateDebut, dateFin, clientId);
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void afficherContratParId() {
        System.out.print("ID du contrat: ");
        int id = scanner.nextInt();
        Optional<Contrat> contrat = contratService.afficherContratParId(id);

        if (contrat.isPresent()) {
            System.out.println(contrat.get());
        } else {
            System.out.println("Aucun contrat trouvé avec l'ID: " + id);
        }
    }

    private void supprimerContrat() {
        System.out.print("ID du contrat à supprimer: ");
        int id = scanner.nextInt();
        contratService.supprimerContrat(id);
    }

    private void afficherContratsParClient() {
        System.out.print("ID du client: ");
        int id = scanner.nextInt();
        contratService.afficherContratsParClient(id);
    }
}