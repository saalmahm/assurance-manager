package view;

import service.ConseillerService;
import model.Conseiller;
import java.util.Optional;
import java.util.Scanner;

public class ConseillerView {
    private Scanner scanner = new Scanner(System.in);
    private ConseillerService conseillerService = new ConseillerService();

    public void afficherMenu() {
        while (true) {
            System.out.println("\n=== GESTION DES CONSEILLERS ===");
            System.out.println("1. Ajouter un conseiller");
            System.out.println("2. Supprimer un conseiller");
            System.out.println("3. Rechercher un conseiller par ID");
            System.out.println("4. Afficher les clients d'un conseiller");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix: ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterConseiller();
                    break;
                case 2:
                    supprimerConseiller();
                    break;
                case 3:
                    rechercherConseiller();
                    break;
                case 4:
                    afficherClientsConseiller();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }

    private void ajouterConseiller() {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Prénom: ");
        String prenom = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        conseillerService.ajouterConseiller(nom, prenom, email);
    }

    private void supprimerConseiller() {
        System.out.print("ID du conseiller à supprimer: ");
        int id = scanner.nextInt();
        conseillerService.supprimerConseiller(id);
    }

    private void rechercherConseiller() {
        System.out.print("ID du conseiller: ");
        int id = scanner.nextInt();
        Optional<Conseiller> conseiller = conseillerService.rechercherConseillerParId(id);

        System.out.println(
                conseiller.map(Object::toString)
                        .orElse("Aucun conseiller trouvé avec l'ID: " + id)
        );
    }

    private void afficherClientsConseiller() {
        System.out.print("ID du conseiller: ");
        int id = scanner.nextInt();
        conseillerService.afficherClientsParConseiller(id);
    }
}