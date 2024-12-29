package webAvance.example.App_Foyer_Universitaire.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Etudiant")
public class Etudiant extends Utilisateur {

    @ManyToOne
    @JoinColumn(name = "chambre_id")
    private Chambre chambre;

    private Boolean isValid;

    @Column(unique = true)
    private String cin;


    // Getters et Setters

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }
}
