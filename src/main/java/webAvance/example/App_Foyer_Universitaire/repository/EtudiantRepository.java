package webAvance.example.App_Foyer_Universitaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webAvance.example.App_Foyer_Universitaire.entity.Etudiant;

import java.util.List;


@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    List<Etudiant> findByNomContainingIgnoreCase(String nom);
    List<Etudiant> findByCin(String cin); // Ajout de la recherche par CIN
}


