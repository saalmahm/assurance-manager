package service;

import dao.ClientDAO;
import dao.ConseillerDAO;
import model.Client;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ClientService {
    private ClientDAO clientDAO = new ClientDAO();
    private ConseillerDAO conseillerDAO = new ConseillerDAO();
    private AtomicInteger idGenerator = new AtomicInteger(1);

    public void ajouterClient(String nom, String prenom, String email, int conseillerId) {
        Client client = new Client(idGenerator.getAndIncrement(), nom, prenom, email, conseillerId);
        clientDAO.save(client);
        System.out.println("Client ajouté avec succès: " + client);
    }

    public void supprimerClient(int id) {
        clientDAO.delete(id);
        System.out.println("Client supprimé avec l'ID: " + id);
    }

    public void rechercherClientParNom(String nom) {
        List<Client> clients = clientDAO.findAll().stream()
                .filter(c -> c.getNom().equalsIgnoreCase(nom))
                .sorted(Comparator.comparing(Client::getNom))
                .collect(Collectors.toList());

        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé avec le nom: " + nom);
        } else {
            clients.forEach(System.out::println);
        }
    }

    public Optional<Client> rechercherClientParId(int id) {
        return Optional.ofNullable(clientDAO.findById(id));
    }

    public void afficherClientsParConseiller(int conseillerId) {
        List<Client> clients = clientDAO.findAll().stream()
                .filter(c -> c.getConseillerId() == conseillerId)
                .collect(Collectors.toList());

        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé pour le conseiller ID: " + conseillerId);
        } else {
            System.out.println("Clients du conseiller " + conseillerId + ":");
            clients.forEach(System.out::println);
        }
    }
}