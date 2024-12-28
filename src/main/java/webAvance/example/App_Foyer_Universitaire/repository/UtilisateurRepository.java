package webAvance.example.App_Foyer_Universitaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webAvance.example.App_Foyer_Universitaire.entity.Utilisateur;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur , Long> {
    Optional<Utilisateur> findByEmail(String email);
    boolean existsByEmail(String email);

}
