package webAvance.example.App_Foyer_Universitaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webAvance.example.App_Foyer_Universitaire.entity.Etudiant;
import webAvance.example.App_Foyer_Universitaire.entity.Paiement;

import java.util.Date;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByEtudiant(Etudiant etudiant);
    List<Paiement> findByPaiementStatus(String status);
    List<Paiement> findByPaimentDateBetween(Date startDate, Date endDate);
}

