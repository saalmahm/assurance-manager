package view;

import service.SinistreService;
import model.Sinistre;
import model.TypeSinistre;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;

public class SinistreView {
    private Scanner scanner = new Scanner(System.in);
    private SinistreService sinistreService = new SinistreService();

    public void afficherMenu() {
        while (true) {
            System.out.println("\n=== GESTION DES SINISTRES ===");
            System.out.println("1. Ajouter un sinistre");
            System.out.println("2. Supprimer un sinistre");
            System.out.println("3. Calculer le coût total des sinistres d'un client");
            System.out.println("4. Rechercher un sinistre par ID");
            System.out.println("5. Afficher les sinistres d'un contrat");
            System.out.println("6. Afficher les sinistres triés par montant décroissant");
            System.out.println("7. Afficher les sinistres d'un client");
            System.out.println("8. Afficher les sinistres avant une date");
            System.out.println("9. Afficher les sinistres avec coût supérieur à un montant");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix: ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterSinistre();
                    break;
                case 2:
                    supprimerSinistre();
                    break;
                case 3:
                    calculerCoutTotalClient();
                    break;
                case 4:
                    rechercherSinistreParId();
                    break;
                case 5:
                    afficherSinistresParContrat();
                    break;
                case 6:
                    afficherSinistresTriesParMontant();
                    break;
                case 7:
                    afficherSinistresParClient();
                    break;
                case 8:
                    afficherSinistresAvantDate();
                    break;
                case 9:
                    afficherSinistresCoutSuperieur();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }

    private void ajouterSinistre() {
        System.out.println("Types de sinistre disponibles:");
        for (TypeSinistre type : TypeSinistre.values()) {
            System.out.println("- " + type);
        }
        System.out.print("Type de sinistre: ");
        String typeStr = scanner.nextLine().toUpperCase();

        try {
            TypeSinistre type = TypeSinistre.valueOf(typeStr);

            System.out.print("Date et heure (YYYY-MM-DDTHH:MM:SS): ");
            LocalDateTime dateTime = LocalDateTime.parse(scanner.nextLine());
            System.out.print("Coût: ");
            double cout = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Description: ");
            String description = scanner.nextLine();
            System.out.print("ID du contrat: ");
            int contratId = scanner.nextInt();

            sinistreService.ajouterSinistre(type, dateTime, cout, description, contratId);
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void supprimerSinistre() {
        System.out.print("ID du sinistre à supprimer: ");
        int id = scanner.nextInt();
        sinistreService.supprimerSinistre(id);
    }

    private void calculerCoutTotalClient() {
        System.out.print("ID du client: ");
        int clientId = scanner.nextInt();
        double total = sinistreService.calculerCoutTotalSinistresClient(clientId);
        System.out.println("Coût total des sinistres pour le client " + clientId + ": " + total + "€");
    }

    private void rechercherSinistreParId() {
        System.out.print("ID du sinistre: ");
        int id = scanner.nextInt();
        Optional<Sinistre> sinistre = sinistreService.rechercherSinistreParId(id);

        if (sinistre.isPresent()) {
            System.out.println(sinistre.get());
        } else {
            System.out.println("Aucun sinistre trouvé avec l'ID: " + id);
        }
    }

    private void afficherSinistresParContrat() {
        System.out.print("ID du contrat: ");
        int id = scanner.nextInt();
        sinistreService.afficherSinistresParContrat(id);
    }

    private void afficherSinistresTriesParMontant() {
        System.out.println("Sinistres triés par montant décroissant:");
        sinistreService.afficherSinistresTriesParMontant();
    }

    private void afficherSinistresParClient() {
        System.out.print("ID du client: ");
        int id = scanner.nextInt();
        sinistreService.afficherSinistresParClient(id);
    }

    private void afficherSinistresAvantDate() {
        System.out.print("Date limite (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.println("Sinistres avant le " + date + ":");
        sinistreService.afficherSinistresAvantDate(date);
    }

    private void afficherSinistresCoutSuperieur() {
        System.out.print("Montant minimum: ");
        double montant = scanner.nextDouble();
        System.out.println("Sinistres avec coût supérieur à " + montant + "€:");
        sinistreService.afficherSinistresCoutSuperieur(montant);
    }
}