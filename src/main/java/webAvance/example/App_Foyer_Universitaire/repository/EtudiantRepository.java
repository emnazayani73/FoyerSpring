package webAvance.example.App_Foyer_Universitaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webAvance.example.App_Foyer_Universitaire.entity.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

}
