package model;

import java.util.HashMap;
import java.util.Map;

public class Client extends Personne {
    private int id;                       // id du client
    private Conseiller conseiller;        // lien avec le conseiller
    private Map<Integer, Contrat> contrats; // contrats rattachés au client

    // Constructeur vide
    public Client() {
        this.contrats = new HashMap<>();
    }

    // Constructeur avec infos de base
    public Client(String nom, String prenom, String email, Conseiller conseiller) {
        super(nom, prenom, email);
        this.conseiller = conseiller;
        this.contrats = new HashMap<>();
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Conseiller getConseiller() {
        return conseiller;
    }

    public void setConseiller(Conseiller conseiller) {
        this.conseiller = conseiller;
    }

    public Map<Integer, Contrat> getContrats() {
        return contrats;
    }

    public void setContrats(Map<Integer, Contrat> contrats) {
        this.contrats = contrats;
    }

    // Ajouter un contrat au client
    public void ajouterContrat(Contrat contrat) {
        if (contrat != null) {
            this.contrats.put(contrat.getId(), contrat);
        }
    }

    // Méthode utilitaire pour récupérer l'id du conseiller (pour DAO)
    public int getConseillerId() {
        return conseiller != null ? conseiller.getId() : 0;
    }

    // Méthode utilitaire pour lier un conseiller via son ID (pratique pour DAO)
    public void setConseillerId(int id) {
        if (this.conseiller == null) {
            this.conseiller = new Conseiller();
        }
        this.conseiller.setId(id);
    }
}
