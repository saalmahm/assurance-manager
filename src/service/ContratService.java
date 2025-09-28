package service;

import dao.ContratDAO;
import dao.ClientDAO;
import model.Contrat;
import model.TypeContrat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ContratService {
    private ContratDAO contratDAO = new ContratDAO();
    private ClientDAO clientDAO = new ClientDAO();
    private AtomicInteger idGenerator = new AtomicInteger(1);

    public void ajouterContrat(TypeContrat type, LocalDate dateDebut, LocalDate dateFin, int clientId) {
        Contrat contrat = new Contrat(idGenerator.getAndIncrement(), type, dateDebut, dateFin, clientId);
        contratDAO.save(contrat);
        System.out.println("Contrat ajouté avec succès: " + contrat);
    }

    public Optional<Contrat> afficherContratParId(int id) {
        return Optional.ofNullable(contratDAO.findById(id));
    }

    public void supprimerContrat(int id) {
        contratDAO.delete(id);
        System.out.println("Contrat supprimé avec l'ID: " + id);
    }

    public void afficherContratsParClient(int clientId) {
        List<Contrat> contrats = contratDAO.findAll().stream()
                .filter(c -> c.getClientId() == clientId)
                .collect(Collectors.toList());

        if (contrats.isEmpty()) {
            System.out.println("Aucun contrat trouvé pour le client ID: " + clientId);
        } else {
            System.out.println("Contrats du client " + clientId + ":");
            contrats.forEach(System.out::println);
        }
    }
}