package webAvance.example.App_Foyer_Universitaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webAvance.example.App_Foyer_Universitaire.repository.*;

@Service
public class StatistiqueService {

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    //@Autowired
   // private PaiementRepository paiementRepository;

    // Statistiques des chambres
    public long getTotalChambres() {
        return chambreRepository.count();
    }

    public long countChambresLibres() {
        return chambreRepository.countByStatut("Libre");
    }

    public long countChambresOccupees() {
        return chambreRepository.countByStatut("Occupée");
    }

    // Statistiques des étudiants
    public long getNombreEtudiantsInscrits() {
        return etudiantRepository.count();
    }

    // Statistiques des paiements
   /* public double getTotalFraisPayes() {
        return paiementRepository.sumByStatut("payé");
    }

    public double getTotalFraisImpayes() {
        return paiementRepository.sumByStatut("impayé");
    }*/
}
