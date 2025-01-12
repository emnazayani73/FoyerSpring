package webAvance.example.App_Foyer_Universitaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webAvance.example.App_Foyer_Universitaire.entity.Utilisateur;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur , Long> {
    Optional<Utilisateur> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM Utilisateur u WHERE u.isValid = false")
    List<Utilisateur> findByIsValidFalse();






}
