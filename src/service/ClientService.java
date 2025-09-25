package service;

import dao.ClientDAO;
import model.Client;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientService {
    private ClientDAO clientDAO = new ClientDAO();

    public void ajouterClient(Client client) {
        clientDAO.addClient(client);
    }

    public Optional<Client> rechercherClientParId(int id) {
        return clientDAO.getAllClients().stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    public List<Client> rechercherClientParNom(String nom) {
        return clientDAO.getAllClients().stream()
                .filter(c -> c.getNom().equalsIgnoreCase(nom))
                .sorted((c1, c2) -> c1.getNom().compareToIgnoreCase(c2.getNom()))
                .collect(Collectors.toList());
    }

    // 🔹 Supprimer un client
    public boolean supprimerClientParId(int id) {
        return clientDAO.deleteClientById(id);
    }

    // 🔹 Récupérer clients d’un conseiller
    public List<Client> getClientsByConseillerId(int conseillerId) {
        return clientDAO.getClientsByConseillerId(conseillerId)
                .stream()
                .sorted((c1, c2) -> c1.getNom().compareToIgnoreCase(c2.getNom()))
                .collect(Collectors.toList());
    }
}
