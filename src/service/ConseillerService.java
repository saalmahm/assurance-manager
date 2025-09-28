package service;

import dao.ConseillerDAO;
import model.Conseiller;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ConseillerService {
    private ConseillerDAO conseillerDAO = new ConseillerDAO();
    private ClientService clientService = new ClientService();
    private AtomicInteger idGenerator = new AtomicInteger(1);

    public void ajouterConseiller(String nom, String prenom, String email) {
        Conseiller conseiller = new Conseiller(idGenerator.getAndIncrement(), nom, prenom, email);
        conseillerDAO.save(conseiller);
        System.out.println("Conseiller ajouté avec succès: " + conseiller);
    }

    public void supprimerConseiller(int id) {
        conseillerDAO.delete(id);
        System.out.println("Conseiller supprimé avec l'ID: " + id);
    }

    public Optional<Conseiller> rechercherConseillerParId(int id) {
        return Optional.ofNullable(conseillerDAO.findById(id));
    }

    public void afficherClientsParConseiller(int conseillerId) {
        clientService.afficherClientsParConseiller(conseillerId);
    }
}