package service;

import dao.ConseillerDAO;
import dao.ClientDAO;
import model.Conseiller;
import model.Client;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.ArrayList;


public class ConseillerService {

    private ConseillerDAO conseillerDAO = new ConseillerDAO();
    private ClientDAO clientDAO = new ClientDAO();

    public void ajouterConseiller(Conseiller conseiller) {
        conseillerDAO.addConseiller(conseiller);
    }

    public boolean supprimerConseillerParId(int id) {
        return conseillerDAO.deleteConseillerById(id);
    }

    public Optional<Conseiller> rechercherConseillerParId(int id) {
        return conseillerDAO.getAllConseillers().stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    public List<Client> getClientsByConseillerId(int id) {
        Optional<Conseiller> conseillerOpt = rechercherConseillerParId(id);
        if (!conseillerOpt.isPresent()) {
            return null; // conseiller n'existe pas
        }
        Map<Integer, Client> clientsMap = conseillerOpt.get().getClients();
        if (clientsMap == null || clientsMap.isEmpty()) {
            return new ArrayList<>(); // aucun client
        }
        return clientsMap.values().stream()
                .sorted((c1, c2) -> c1.getNom().compareToIgnoreCase(c2.getNom()))
                .collect(Collectors.toList());
    }

    public List<Conseiller> getAllConseillers() {
        return conseillerDAO.getAllConseillers().stream()
                .sorted((c1, c2) -> c1.getNom().compareToIgnoreCase(c2.getNom()))
                .collect(Collectors.toList());
    }
}
