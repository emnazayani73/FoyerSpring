package webAvance.example.App_Foyer_Universitaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webAvance.example.App_Foyer_Universitaire.entity.Etudiant;

import java.util.List;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    @Query("SELECT e FROM Etudiant e WHERE e.isValid = false")
    List<Etudiant> findInvalidEtudiants();
    @Query("SELECT e FROM Etudiant e WHERE e.isValid = true")
    List<Etudiant> findValidEtudiants();
    @Query("SELECT e FROM Etudiant e WHERE e.chambre.id IS NULL")
    List<Etudiant> findEtudiantsSansChambre();

}
