package view;

import service.ClientService;
import model.Client;
import java.util.Optional;
import java.util.Scanner;

public class ClientView {
    private Scanner scanner = new Scanner(System.in);
    private ClientService clientService = new ClientService();

    public void afficherMenu() {
        while (true) {
            System.out.println("\n=== GESTION DES CLIENTS ===");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Supprimer un client");
            System.out.println("3. Rechercher un client par nom");
            System.out.println("4. Rechercher un client par ID");
            System.out.println("5. Afficher les clients d'un conseiller");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix: ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterClient();
                    break;
                case 2:
                    supprimerClient();
                    break;
                case 3:
                    rechercherClientParNom();
                    break;
                case 4:
                    rechercherClientParId();
                    break;
                case 5:
                    afficherClientsParConseiller();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Choix invalide!");
            }
        }
    }

    private void ajouterClient() {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Prénom: ");
        String prenom = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("ID du conseiller: ");
        int conseillerId = scanner.nextInt();

        clientService.ajouterClient(nom, prenom, email, conseillerId);
    }

    private void supprimerClient() {
        System.out.print("ID du client à supprimer: ");
        int id = scanner.nextInt();
        clientService.supprimerClient(id);
    }

    private void rechercherClientParNom() {
        System.out.print("Nom du client: ");
        String nom = scanner.nextLine();
        clientService.rechercherClientParNom(nom);
    }

    private void rechercherClientParId() {
        System.out.print("ID du client: ");
        int id = scanner.nextInt();
        Optional<Client> client = clientService.rechercherClientParId(id);

        if (client.isPresent()) {
            System.out.println(client.get());
        } else {
            System.out.println("Aucun client trouvé avec l'ID: " + id);
        }
    }

    private void afficherClientsParConseiller() {
        System.out.print("ID du conseiller: ");
        int id = scanner.nextInt();
        clientService.afficherClientsParConseiller(id);
    }
}