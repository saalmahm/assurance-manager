package view;

import service.ConseillerService;
import model.Conseiller;
import model.Client;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConseillerView {
    private ConseillerService service = new ConseillerService();
    private Scanner sc = new Scanner(System.in);

    public void menu() {
        System.out.println("1. Ajouter conseiller");
        System.out.println("2. Supprimer conseiller par ID");
        System.out.println("3. Rechercher conseiller par ID");
        System.out.println("4. Afficher clients d’un conseiller");
        System.out.println("5. Supprimer un client d’un conseiller");

        int choix = sc.nextInt(); sc.nextLine();

        switch (choix) {
            case 1:
                Conseiller c = new Conseiller();
                System.out.print("Nom : "); c.setNom(sc.nextLine());
                System.out.print("Prenom : "); c.setPrenom(sc.nextLine());
                System.out.print("Email : "); c.setEmail(sc.nextLine());

                boolean ajoute = service.ajouterConseiller(c);
                if (ajoute) {
                    System.out.println("✅ Conseiller ajouté avec succès !");
                } else {
                    System.out.println("❌ Échec de l'ajout du conseiller.");
                }
                break;

            case 2:
                System.out.print("ID du conseiller à supprimer : ");
                int idSup = sc.nextInt();
                boolean supprime = service.supprimerConseillerParId(idSup);

                if (supprime) {
                    System.out.println("✅ Conseiller avec l'ID " + idSup + " supprimé avec succès !");
                } else {
                    System.out.println("⚠️ Échec : aucun conseiller trouvé avec l'ID " + idSup);
                }
                break;

            case 3:
                System.out.print("ID du conseiller : ");
                int idRecherche = sc.nextInt();
                Optional<Conseiller> conseillerOpt = service.rechercherConseillerParId(idRecherche);
                if (conseillerOpt.isPresent()) {
                    Conseiller conseiller = conseillerOpt.get();
                    System.out.println(conseiller);
                } else {
                    System.out.println("⚠️ Conseiller introuvable pour l'ID " + idRecherche);
                }
                break;

            case 4:
                System.out.print("ID du conseiller : ");
                int idConseiller = sc.nextInt();

                Optional<List<Client>> clientsOpt = service.getClientsByConseillerIdOptional(idConseiller);

                if (!clientsOpt.isPresent()) {
                    System.out.println("⚠️ Conseiller introuvable pour l'ID " + idConseiller);
                } else if (clientsOpt.get().isEmpty()) {
                    System.out.println("✅ Aucun client pour ce conseiller.");
                } else {
                    System.out.println("Clients du conseiller :");
                    clientsOpt.get().forEach(client -> System.out.println("- " + client.getNom() + " " + client.getPrenom()));
                }
                break;

            case 5: // Supprimer client d’un conseiller
                System.out.print("ID du conseiller : ");
                int idConsSup = sc.nextInt();
                System.out.print("ID du client à supprimer : ");
                int idClientSup = sc.nextInt();
                boolean supClient = service.supprimerClientDeConseiller(idConsSup, idClientSup);
                System.out.println(supClient ? "✅ Client retiré du conseiller !"
                        : "⚠️ Échec : conseiller ou client introuvable.");
                break;

            default:
                System.out.println("⚠️ Choix invalide !");
        }
    }
}
