package model;

import java.time.LocalDateTime;

public class Sinistre {
    private int id;
    private TypeSinistre typeSinistre;
    private LocalDateTime dateTime;
    private double cout;
    private String description;
    private int contratId;

    public Sinistre(int id, TypeSinistre typeSinistre, LocalDateTime dateTime, double cout, String description, int contratId) {
        this.id = id;
        this.typeSinistre = typeSinistre;
        this.dateTime = dateTime;
        this.cout = cout;
        this.description = description;
        this.contratId = contratId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public TypeSinistre getTypeSinistre() { return typeSinistre; }
    public void setTypeSinistre(TypeSinistre typeSinistre) { this.typeSinistre = typeSinistre; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public double getCout() { return cout; }
    public void setCout(double cout) { this.cout = cout; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getContratId() { return contratId; }
    public void setContratId(int contratId) { this.contratId = contratId; }

    @Override
    public String toString() {
        return "Sinistre{id=" + id + ", type=" + typeSinistre + ", date=" + dateTime + ", cout=" + cout + ", contratId=" + contratId + "}";
    }
}