import view.ClientView;
import view.ConseillerView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Gérer Clients");
        System.out.println("2. Gérer Conseillers");
        int choix = sc.nextInt();
        sc.nextLine();

        switch (choix) {
            case 1:
                new ClientView().menu();
                break;
            case 2:
                new ConseillerView().menu();
                break;
            default:
                System.out.println("Choix invalide");
        }
    }
}
