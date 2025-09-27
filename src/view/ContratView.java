package view;

import service.ContratService;
import model.Contrat;
import model.Client;
import model.TypeContrat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ContratView {
    private ContratService contratService = new ContratService();
    private Scanner sc = new Scanner(System.in);

    public void menu() {
        System.out.println("1. Ajouter un contrat");
        System.out.println("2. Rechercher un contrat par ID");
        System.out.println("3. Supprimer un contrat par ID");
        System.out.println("4. Afficher les contrats d’un client");

        int choix = sc.nextInt(); sc.nextLine();

        switch (choix) {
            case 1:
                Contrat c = new Contrat();
                Client client = new Client();
                System.out.print("ID client : ");
                client.setId(sc.nextInt()); sc.nextLine();
                c.setClient(client);

                System.out.print("Type contrat (AUTOMOBILE, MAISON, MALADIE) : ");
                c.setTypeContrat(TypeContrat.valueOf(sc.nextLine().toUpperCase()));

                System.out.print("Date début (yyyy-mm-dd) : ");
                c.setDateDebut(LocalDate.parse(sc.nextLine()));

                System.out.print("Date fin (yyyy-mm-dd) : ");
                c.setDateFin(LocalDate.parse(sc.nextLine()));

                boolean ajoute = contratService.ajouterContrat(c);
                System.out.println(ajoute ? "✅ Contrat ajouté avec succès !" : "❌ Échec de l'ajout du contrat.");
                break;

            case 2:
                System.out.print("ID contrat : ");
                int id = sc.nextInt(); sc.nextLine();
                Optional<Contrat> contratOpt = contratService.rechercherContratParId(id);
                if (contratOpt.isPresent()) {
                    Contrat contrat = contratOpt.get();
                    System.out.println("ID: " + contrat.getId() +
                            "\nType: " + contrat.getTypeContrat() +
                            "\nDate début: " + contrat.getDateDebut() +
                            "\nDate fin: " + contrat.getDateFin() +
                            "\nClient ID: " + contrat.getClient().getId());
                } else {
                    System.out.println("⚠️ Contrat introuvable pour l'ID " + id);
                }
                break;

            case 3:
                System.out.print("ID contrat à supprimer : ");
                int idSup = sc.nextInt(); sc.nextLine();
                boolean supprime = contratService.supprimerContratParId(idSup);
                System.out.println(supprime ? "✅ Contrat supprimé !" : "⚠️ Contrat introuvable ou erreur !");
                break;

            case 4:
                System.out.print("ID client : ");
                int clientId = sc.nextInt(); sc.nextLine();
                List<Contrat> contrats = contratService.getContratsParClientId(clientId);
                if (contrats.isEmpty()) {
                    System.out.println("⚠️ Aucun contrat trouvé pour ce client.");
                } else {
                    System.out.println("Contrats du client :");
                    for (Contrat ctr : contrats) {
                        System.out.println("ID: " + ctr.getId() +
                                ", Type: " + ctr.getTypeContrat() +
                                ", Début: " + ctr.getDateDebut() +
                                ", Fin: " + ctr.getDateFin());
                    }
                }
                break;

            default:
                System.out.println("⚠️ Choix invalide !");
        }
    }
}
