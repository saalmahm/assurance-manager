package model;

import java.util.HashMap;
import java.util.Map;

public class Conseiller extends Personne {
    private int id;
    private Map<Integer, Client> clients;

    public Conseiller() {
        this.clients = new HashMap<>();
    }

    public Conseiller(String nom, String prenom, String email) {
        super(nom, prenom, email);
        this.clients = new HashMap<>();
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Map<Integer, Client> getClients() { return clients; }
    public void setClients(Map<Integer, Client> clients) { this.clients = clients; }

    public void ajouterClient(Client client) {
        this.clients.put(client.getId(), client);
    }

    public void supprimerClient(int clientId) {
        this.clients.remove(clientId);
    }
}