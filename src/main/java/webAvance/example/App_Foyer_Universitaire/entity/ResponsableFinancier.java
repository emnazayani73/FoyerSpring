package webAvance.example.App_Foyer_Universitaire.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ResponsableFinancier")
public class ResponsableFinancier extends Utilisateur {
}
