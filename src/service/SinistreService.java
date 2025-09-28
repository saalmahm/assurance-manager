package service;

import dao.SinistreDAO;
import dao.ContratDAO;
import dao.ClientDAO;
import model.Sinistre;
import model.TypeSinistre;
import model.Contrat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SinistreService {
    private SinistreDAO sinistreDAO = new SinistreDAO();
    private ContratDAO contratDAO = new ContratDAO();
    private ClientDAO clientDAO = new ClientDAO();
    private AtomicInteger idGenerator = new AtomicInteger(1);

    public void ajouterSinistre(TypeSinistre type, LocalDateTime dateTime, double cout, String description, int contratId) {
        Sinistre sinistre = new Sinistre(idGenerator.getAndIncrement(), type, dateTime, cout, description, contratId);
        sinistreDAO.save(sinistre);
        System.out.println("Sinistre ajouté avec succès: " + sinistre);
    }

    public void supprimerSinistre(int id) {
        sinistreDAO.delete(id);
        System.out.println("Sinistre supprimé avec l'ID: " + id);
    }

    public double calculerCoutTotalSinistresClient(int clientId) {
        // JOIN logic here: get all contrats of client, then all sinistres of those contrats
        List<Integer> contratIds = contratDAO.findAll().stream()
                .filter(c -> c.getClientId() == clientId)
                .map(Contrat::getId)
                .collect(Collectors.toList());

        return sinistreDAO.findAll().stream()
                .filter(s -> contratIds.contains(s.getContratId()))
                .mapToDouble(Sinistre::getCout)
                .sum();
    }

    public Optional<Sinistre> rechercherSinistreParId(int id) {
        return Optional.ofNullable(sinistreDAO.findById(id));
    }

    public void afficherSinistresParContrat(int contratId) {
        List<Sinistre> sinistres = sinistreDAO.findAll().stream()
                .filter(s -> s.getContratId() == contratId)
                .collect(Collectors.toList());

        if (sinistres.isEmpty()) {
            System.out.println("Aucun sinistre trouvé pour le contrat ID: " + contratId);
        } else {
            sinistres.forEach(System.out::println);
        }
    }

    public void afficherSinistresTriesParMontant() {
        List<Sinistre> sinistres = sinistreDAO.findAll().stream()
                .sorted(Comparator.comparing(Sinistre::getCout).reversed())
                .collect(Collectors.toList());

        sinistres.forEach(System.out::println);
    }

    public void afficherSinistresParClient(int clientId) {
        List<Integer> contratIds = contratDAO.findAll().stream()
                .filter(c -> c.getClientId() == clientId)
                .map(Contrat::getId)
                .collect(Collectors.toList());

        List<Sinistre> sinistres = sinistreDAO.findAll().stream()
                .filter(s -> contratIds.contains(s.getContratId()))
                .collect(Collectors.toList());

        if (sinistres.isEmpty()) {
            System.out.println("Aucun sinistre trouvé pour le client ID: " + clientId);
        } else {
            sinistres.forEach(System.out::println);
        }
    }

    public void afficherSinistresAvantDate(LocalDate date) {
        List<Sinistre> sinistres = sinistreDAO.findAll().stream()
                .filter(s -> s.getDateTime().toLocalDate().isBefore(date))
                .collect(Collectors.toList());

        sinistres.forEach(System.out::println);
    }

    public void afficherSinistresCoutSuperieur(double montant) {
        List<Sinistre> sinistres = sinistreDAO.findAll().stream()
                .filter(s -> s.getCout() > montant)
                .collect(Collectors.toList());

        sinistres.forEach(System.out::println);
    }
}