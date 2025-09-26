package model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class Conseiller extends Personne {
    private int id;
    private Map<Integer, Client> clients;

    public Conseiller() { this.clients = new HashMap<>(); }
    public Conseiller(String nom, String prenom, String email) {
        super(nom, prenom, email);
        this.clients = new HashMap<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Map<Integer, Client> getClients() { return clients; }
    public void setClients(Map<Integer, Client> clients) { this.clients = clients; }

    public void ajouterClient(Client client) { this.clients.put(client.getId(), client); }
    public void supprimerClient(int clientId) { this.clients.remove(clientId); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Conseiller : ").append(nom).append(" ").append(prenom).append("\n");
        sb.append("Email : ").append(email).append("\n");
        sb.append("Clients : ");
        if (clients.isEmpty()) {
            sb.append("aucun client");
        } else {
            sb.append(String.join(", ", clients.values().stream()
                    .map(c -> c.getNom() + " " + c.getPrenom())
                    .collect(Collectors.toList()))); // <-- ici, Java 8 compatible
        }
        return sb.toString();
    }
}
