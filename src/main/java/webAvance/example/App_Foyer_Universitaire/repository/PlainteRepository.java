package webAvance.example.App_Foyer_Universitaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webAvance.example.App_Foyer_Universitaire.entity.Plainte;

public interface PlainteRepository extends JpaRepository<Plainte, Long> {
}
