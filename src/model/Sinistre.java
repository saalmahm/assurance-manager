package model;

import java.time.LocalDateTime;

public class Sinistre {
    // Fix: Remove 'package' keyword and use proper field declaration
    private String description;
    private LocalDateTime dateOccurrence;
    private String lieu;
    private double montantDommage;
    private String statut;
    private String numeroSinistre;

    // Constructors
    public Sinistre() {
    }

    public Sinistre(String numeroSinistre, String description, LocalDateTime dateOccurrence,
                    String lieu, double montantDommage, String statut) {
        this.numeroSinistre = numeroSinistre;
        this.description = description;
        this.dateOccurrence = dateOccurrence;
        this.lieu = lieu;
        this.montantDommage = montantDommage;
        this.statut = statut;
    }

    // Getters and Setters
    public String getNumeroSinistre() {
        return numeroSinistre;
    }

    public void setNumeroSinistre(String numeroSinistre) {
        this.numeroSinistre = numeroSinistre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateOccurrence() {
        return dateOccurrence;
    }

    public void setDateOccurrence(LocalDateTime dateOccurrence) {
        this.dateOccurrence = dateOccurrence;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public double getMontantDommage() {
        return montantDommage;
    }

    public void setMontantDommage(double montantDommage) {
        this.montantDommage = montantDommage;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Sinistre{" +
                "numeroSinistre='" + numeroSinistre + '\'' +
                ", description='" + description + '\'' +
                ", dateOccurrence=" + dateOccurrence +
                ", lieu='" + lieu + '\'' +
                ", montantDommage=" + montantDommage +
                ", statut='" + statut + '\'' +
                '}';
    }
}