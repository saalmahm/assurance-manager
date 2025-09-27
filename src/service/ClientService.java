package service;

import dao.ClientDAO;
import model.Client;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientService {
    private ClientDAO clientDAO = new ClientDAO();

    public boolean ajouterClient(Client client) { return clientDAO.addClient(client); }
    public boolean supprimerClientParId(int id) { return clientDAO.deleteClientById(id); }
    public Optional<Client> rechercherClientParId(int id) { return clientDAO.getClientById(id); }

    public List<Client> rechercherClientParNom(String nom) {
        return clientDAO.getAllClients().stream()
                .filter(c -> c.getNom().toLowerCase().contains(nom.toLowerCase()))
                .sorted((c1,c2) -> c1.getNom().compareToIgnoreCase(c2.getNom()))
                .collect(Collectors.toList());
    }

    public List<Client> getClientsByConseillerId(int conseillerId) {
        return clientDAO.getClientsByConseillerId(conseillerId);
    }

    public List<Client> getClientsSansConseiller() {
        return clientDAO.getAllClients().stream()
                .filter(c -> c.getConseillerId() == null)
                .collect(Collectors.toList());
    }
}
