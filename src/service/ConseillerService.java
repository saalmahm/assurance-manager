package service;

import dao.ConseillerDAO;
import dao.ClientDAO;
import model.Conseiller;
import model.Client;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConseillerService {
    private ConseillerDAO conseillerDAO = new ConseillerDAO();
    private ClientDAO clientDAO = new ClientDAO();

    public boolean ajouterConseiller(Conseiller c) { return conseillerDAO.addConseiller(c); }
    public boolean supprimerConseillerParId(int id) { return conseillerDAO.deleteConseillerById(id); }
    public Optional<Conseiller> rechercherConseillerParId(int id) { return conseillerDAO.getConseillerById(id); }

    public List<Conseiller> rechercherConseillerParNom(String nom) {
        return conseillerDAO.getAllConseillers().stream()
                .filter(c -> c.getNom().toLowerCase().contains(nom.toLowerCase()))
                .sorted((c1,c2) -> c1.getNom().compareToIgnoreCase(c2.getNom()))
                .collect(Collectors.toList());
    }

    public Optional<List<Client>> getClientsByConseillerIdOptional(int conseillerId) {
        // Vérifier si le conseiller existe
        Optional<Conseiller> conseillerOpt = conseillerDAO.getConseillerById(conseillerId);
        if (!conseillerOpt.isPresent()) {
            return Optional.empty(); // Conseiller introuvable
        }

        // Récupérer la liste des clients du conseiller
        List<Client> clients = clientDAO.getClientsByConseillerId(conseillerId);
        return Optional.of(clients); // Même si la liste est vide
    }

    public boolean supprimerClientDeConseiller(int conseillerId, int clientId) {
        Optional<Client> clientOpt = clientDAO.getClientById(clientId);

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            Integer clientConseillerId = client.getConseillerId();

            if (clientConseillerId != null && clientConseillerId == conseillerId) {
                client.setConseillerId(null); // délie le client du conseiller
                return clientDAO.updateClientConseiller(client);
            }
        }
        return false;
    }

}
