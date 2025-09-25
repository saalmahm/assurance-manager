package view;

import service.ConseillerService;
import model.Conseiller;
import model.Client;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConseillerView {

    private ConseillerService conseillerService = new ConseillerService();
    private Scanner sc = new Scanner(System.in);

    public void menu() {
        System.out.println("1. Ajouter conseiller");
        System.out.println("2. Supprimer conseiller par ID");
        System.out.println("3. Rechercher conseiller par ID");
        System.out.println("4. Afficher les clients d’un conseiller");

        int choix = sc.nextInt();
        sc.nextLine();

        switch (choix) {
            case 1:
                Conseiller c = new Conseiller();
                System.out.print("Nom : ");
                c.setNom(sc.nextLine());
                System.out.print("Prenom : ");
                c.setPrenom(sc.nextLine());
                System.out.print("Email : ");
                c.setEmail(sc.nextLine());
                conseillerService.ajouterConseiller(c);
                break;

            case 2:
                System.out.print("ID du conseiller à supprimer : ");
                int idSup = sc.nextInt();
                boolean supprime = conseillerService.supprimerConseillerParId(idSup);
                System.out.println(supprime ? "Conseiller supprimé !" : "Conseiller introuvable");
                break;

            case 3:
                System.out.print("ID du conseiller : ");
                int idRecherche = sc.nextInt();
                Optional<Conseiller> conseillerOpt = conseillerService.rechercherConseillerParId(idRecherche);
                if (conseillerOpt.isPresent()) {
                    Conseiller conseiller = conseillerOpt.get();
                    System.out.println(conseiller.getNom() + " " + conseiller.getPrenom());
                } else {
                    System.out.println("Conseiller introuvable");
                }
                break;
            case 4:
                System.out.print("ID du conseiller : ");
                int idConseiller = sc.nextInt();
                List<Client> clients = conseillerService.getClientsByConseillerId(idConseiller);

                if (clients == null) {
                    System.out.println("Conseiller introuvable !");
                } else if (clients.isEmpty()) {
                    System.out.println("Aucun client pour ce conseiller.");
                } else {
                    clients.forEach(client -> System.out.println(client.getNom() + " " + client.getPrenom()));
                }
                break;

        }
    }
}
