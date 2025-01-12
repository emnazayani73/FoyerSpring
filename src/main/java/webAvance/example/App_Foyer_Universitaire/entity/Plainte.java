package webAvance.example.App_Foyer_Universitaire.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Plainte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id" , nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "agent_maintenance_id")
    private AgentMaintenance agentMaintenance;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String statut;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public AgentMaintenance getAgentMaintenance() {
        return agentMaintenance;
    }

    public void setAgentMaintenance(AgentMaintenance agentMaintenance) {
        this.agentMaintenance = agentMaintenance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
