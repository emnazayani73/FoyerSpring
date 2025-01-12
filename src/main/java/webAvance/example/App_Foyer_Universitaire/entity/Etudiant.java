package webAvance.example.App_Foyer_Universitaire.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Etudiant")
public class Etudiant extends Utilisateur {

    @ManyToOne
    @JoinColumn(name = "chambre_id")
    private Chambre chambre;

    private Boolean isValid;



    // la parent est l'étudiant, tous les opérations appliquées sur l'étudiant seront aussi appliquées sur leurs plaintes
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plainte> plaintes = new ArrayList<>();

    public List<Plainte> getPlaintes() {
        return plaintes;
    }

    public void setPlaintes(List<Plainte> plaintes) {
        this.plaintes = plaintes;
    }

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


}
