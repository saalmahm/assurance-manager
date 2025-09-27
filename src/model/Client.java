package model;

public class Client extends Person {
    private int id;
    private int conseillerId;

    public Client(int id, String nom, String prenom, String email, int conseillerId) {
        super(nom, prenom, email);
        this.id = id;
        this.conseillerId = conseillerId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getConseillerId() { return conseillerId; }
    public void setConseillerId(int conseillerId) { this.conseillerId = conseillerId; }

    @Override
    public String toString() {
        return "Client{id=" + id + ", nom='" + getNom() + "', prenom='" + getPrenom() + "', conseillerId=" + conseillerId + "}";
    }
}