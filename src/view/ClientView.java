package view;

import service.ClientService;
import model.Client;
import java.util.Scanner;
import java.util.Optional;
import java.util.List;

public class ClientView {
    private ClientService clientService = new ClientService();
    private Scanner sc = new Scanner(System.in);

    public void menu() {
        System.out.println("1. Ajouter client");
        System.out.println("2. Rechercher client par nom");
        System.out.println("3. Rechercher client par ID");
        System.out.println("4. Supprimer client par ID");
        System.out.println("5. Afficher les clients d’un conseiller");

        int choix = sc.nextInt(); sc.nextLine();

        switch (choix) {
            case 1:
                Client c = new Client();
                System.out.print("Nom : "); c.setNom(sc.nextLine());
                System.out.print("Prenom : "); c.setPrenom(sc.nextLine());
                System.out.print("Email : "); c.setEmail(sc.nextLine());
                System.out.print("ID Conseiller (0 si aucun) : ");
                int idC = sc.nextInt(); sc.nextLine();
                if (idC > 0) c.setConseillerId(idC);

                // récupérer le résultat de l'ajout
                boolean ajoute = clientService.ajouterClient(c);
                if (ajoute) {
                    System.out.println("✅ Client ajouté avec succès !");
                } else {
                    System.out.println("❌ Échec de l'ajout du client.");
                }
                break;

            case 2:
                System.out.print("Nom à rechercher : ");
                String nom = sc.nextLine();
                List<Client> clients = clientService.rechercherClientParNom(nom);

                if (clients.isEmpty()) {
                    System.out.println("⚠️ Aucun client trouvé pour le nom : " + nom);
                } else {
                    for (Client cl : clients) {
                        System.out.println("Nom : " + cl.getNom());
                        System.out.println("Prenom : " + cl.getPrenom());
                        System.out.println("Email : " + cl.getEmail());
                        System.out.println("Conseiller ID : " + (cl.getConseillerId() != null ? cl.getConseillerId() : "aucun"));
                    }
                }
                break;


            case 3:
                System.out.print("ID client : ");
                int id = sc.nextInt(); sc.nextLine();
                Optional<Client> clientOpt = clientService.rechercherClientParId(id);

                if (clientOpt.isPresent()) {
                    Client cl = clientOpt.get();
                    System.out.println("Nom : " + cl.getNom());
                    System.out.println("Prenom : " + cl.getPrenom());
                    System.out.println("Email : " + cl.getEmail());
                    System.out.println("Conseiller ID : " + (cl.getConseillerId() != null ? cl.getConseillerId() : "aucun"));
                } else {
                    System.out.println("⚠️ Client introuvable pour l'ID : " + id);
                }
                break;

            case 4:
                System.out.print("ID du client à supprimer : ");
                int idSup = sc.nextInt(); sc.nextLine();
                boolean supprime = clientService.supprimerClientParId(idSup);

                if (supprime) {
                    System.out.println("✅ Client avec l'ID " + idSup + " supprimé avec succès !");
                } else {
                    System.out.println("⚠️ Échec : aucun client trouvé avec l'ID " + idSup);
                }
                break;

            case 5:
                System.out.print("ID du conseiller : ");
                int idConseiller = sc.nextInt();
                clientService.getClientsByConseillerId(idConseiller)
                        .forEach(cl -> System.out.println(cl));
                break;
        }
    }
}
