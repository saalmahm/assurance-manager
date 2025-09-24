package model;

import java.util.HashMap;
import java.util.Map;

public class Client extends Personne {
    private int id;
    private Map<Integer, Contrat> contrats;
    private Conseiller conseiller;

    public Client() {
        this.contrats = new HashMap<>();
    }

    public Client(String nom, String prenom, String email, Conseiller conseiller) {
        super(nom, prenom, email);
        this.conseiller = conseiller;
        this.contrats = new HashMap<>();
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Map<Integer, Contrat> getContrats() { return contrats; }
    public void setContrats(Map<Integer, Contrat> contrats) { this.contrats = contrats; }

    public Conseiller getConseiller() { return conseiller; }
    public void setConseiller(Conseiller conseiller) { this.conseiller = conseiller; }

    public void ajouterContrat(Contrat contrat) {
        this.contrats.put(contrat.getId(), contrat);
    }
}