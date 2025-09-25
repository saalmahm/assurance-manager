package view;

import service.ClientService;
import model.Client;

import java.util.Scanner;
import java.util.Optional;

public class ClientView {
    private ClientService clientService = new ClientService();
    private Scanner sc = new Scanner(System.in);

    public void menu() {
        System.out.println("1. Ajouter client");
        System.out.println("2. Rechercher client par nom");
        System.out.println("3. Rechercher client par ID");
        System.out.println("4. Supprimer client par ID");
        System.out.println("5. Afficher les clients d’un conseiller");

        int choix = sc.nextInt();
        sc.nextLine();

        switch (choix) {
            case 1:
                Client c = new Client();
                System.out.print("Nom : ");
                c.setNom(sc.nextLine());
                System.out.print("Prenom : ");
                c.setPrenom(sc.nextLine());
                System.out.print("Email : ");
                c.setEmail(sc.nextLine());
                clientService.ajouterClient(c);
                break;

            case 2:
                System.out.print("Nom à rechercher : ");
                String nom = sc.nextLine();
                clientService.rechercherClientParNom(nom)
                        .forEach(client -> System.out.println(client.getNom() + " " + client.getPrenom()));
                break;

            case 3:
                System.out.print("ID client : ");
                int id = sc.nextInt();
                Optional<Client> clientOpt = clientService.rechercherClientParId(id);
                if (clientOpt.isPresent()) {
                    Client client = clientOpt.get();
                    System.out.println(client.getNom() + " " + client.getPrenom());
                } else {
                    System.out.println("Client introuvable");
                }
                break;

            case 4:
                System.out.print("ID du client à supprimer : ");
                int idSup = sc.nextInt();
                boolean supprime = clientService.supprimerClientParId(idSup);
                if (supprime) {
                    System.out.println("Client supprimé avec succès !");
                } else {
                    System.out.println("Échec : client introuvable.");
                }
                break;

            case 5:
                System.out.print("ID du conseiller : ");
                int conseillerId = sc.nextInt();
                clientService.getClientsByConseillerId(conseillerId)
                        .forEach(client -> System.out.println(client.getNom() + " " + client.getPrenom()));
                break;
        }
    }
}
