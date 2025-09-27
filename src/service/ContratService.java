package service;

import dao.ContratDAO;
import model.Contrat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContratService {
    private ContratDAO contratDAO = new ContratDAO();

    public boolean ajouterContrat(Contrat contrat) {
        return contratDAO.addContrat(contrat);
    }

    public Optional<Contrat> rechercherContratParId(int id) {
        return contratDAO.getContratById(id);
    }

    public boolean supprimerContratParId(int id) {
        return contratDAO.deleteContratById(id);
    }

    public List<Contrat> getContratsParClientId(int clientId) {
        return contratDAO.getContratsByClientId(clientId)
                .stream()
                .sorted((c1, c2) -> c1.getDateDebut().compareTo(c2.getDateDebut()))
                .collect(Collectors.toList());
    }
}
