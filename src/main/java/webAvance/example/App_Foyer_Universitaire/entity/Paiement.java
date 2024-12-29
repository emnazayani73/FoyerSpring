package webAvance.example.App_Foyer_Universitaire.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", referencedColumnName = "id")
    private Etudiant etudiant;



    private Double amount;
    private Date paimentDate;
    private String paiementStatus;
    private String paiementMethod;

    // Constructor
    public Paiement() {}

    // Getters and setters
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getPaimentDate() {
        return paimentDate;
    }

    public void setPaimentDate(Date paimentDate) {
        this.paimentDate = paimentDate;
    }

    public String getPaiementStatus() {
        return paiementStatus;
    }

    public void setPaiementStatus(String paiementStatus) {
        this.paiementStatus = paiementStatus;
    }

    public String getPaiementMethod() {
        return paiementMethod;
    }

    public void setPaiementMethod(String paiementMethod) {
        this.paiementMethod = paiementMethod;
    }
}


