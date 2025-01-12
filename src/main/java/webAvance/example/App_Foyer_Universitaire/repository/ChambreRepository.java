package webAvance.example.App_Foyer_Universitaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webAvance.example.App_Foyer_Universitaire.entity.Chambre;

import java.util.List;

public interface ChambreRepository extends JpaRepository <Chambre , Long> {
    List<Chambre> findByStatut(String statut);
    long countByStatut(String statut);
}
