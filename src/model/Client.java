package model;

import java.util.HashMap;
import java.util.Map;

public class Client extends Personne {
    private int id;
    private Conseiller conseiller;        // lien avec le conseiller
    private Map<Integer, Contrat> contrats; // contrats du client

    public Client() { this.contrats = new HashMap<>(); }
    public Client(String nom, String prenom, String email, Conseiller conseiller) {
        super(nom, prenom, email);
        this.conseiller = conseiller;
        this.contrats = new HashMap<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Conseiller getConseiller() { return conseiller; }
    public void setConseiller(Conseiller conseiller) { this.conseiller = conseiller; }

    public Map<Integer, Contrat> getContrats() { return contrats; }
    public void setContrats(Map<Integer, Contrat> contrats) { this.contrats = contrats; }

    public void ajouterContrat(Contrat contrat) {
        if (contrat != null) this.contrats.put(contrat.getId(), contrat);
    }

    public Integer getConseillerId() { return conseiller != null ? conseiller.getId() : null; }

    public void setConseillerId(Integer id) {
        if (id != null && id > 0) {
            if (this.conseiller == null) this.conseiller = new Conseiller();
            this.conseiller.setId(id);
        } else {
            this.conseiller = null;
        }
    }


    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' + ", email='" + email + '\'' +
                ", conseillerId=" + getConseillerId() + '}';
    }
}
