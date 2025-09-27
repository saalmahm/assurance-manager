import view.ClientView;
import view.ConseillerView;
import view.ContratView;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choix;

        do {
            System.out.println("===== MENU PRINCIPAL =====");
            System.out.println("1. Gérer Clients");
            System.out.println("2. Gérer Conseillers");
            System.out.println("3. Gérer Contrats");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:
                    new ClientView().menu();
                    break;
                case 2:
                    new ConseillerView().menu();
                    break;
                case 3:
                    new ContratView().menu();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("❌ Choix invalide");
            }

            if (choix != 0) {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                sc.nextLine();
            }

        } while (choix != 0);

        sc.close();
    }
}
