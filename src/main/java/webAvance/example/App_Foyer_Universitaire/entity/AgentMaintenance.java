package webAvance.example.App_Foyer_Universitaire.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("AgentMaintenance")
public class AgentMaintenance extends Utilisateur {

    @OneToMany(mappedBy = "agentMaintenance")
    @JsonIgnore  // Ignorer la collection lors de la sérialisation en JSON
    private List<Plainte> plaintesGerees = new ArrayList<>();

    public List<Plainte> getPlaintesGerees() {
        return plaintesGerees;
    }

    public void setPlaintesGerees(List<Plainte> plaintesGerees) {
        this.plaintesGerees = plaintesGerees;
    }

}
