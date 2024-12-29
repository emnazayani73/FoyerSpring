package webAvance.example.App_Foyer_Universitaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webAvance.example.App_Foyer_Universitaire.entity.Etudiant;
import webAvance.example.App_Foyer_Universitaire.entity.Paiement;
import webAvance.example.App_Foyer_Universitaire.repository.PaiementRepository;

import java.util.Date;
import java.util.List;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    public Paiement createPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    public List<Paiement> getPaiementsByStudent(Etudiant etudiant) {
        return paiementRepository.findByEtudiant(etudiant);
    }

    public List<Paiement> getPaiementsByStatus(String status) {
        return paiementRepository.findByPaiementStatus(status);
    }
    public List<Paiement> getPaiementsByDateRange(Date startDate, Date endDate) {
        return paiementRepository.findByPaimentDateBetween(startDate, endDate);
    }

    public boolean deletePaiement(Long id) {
        if (paiementRepository.existsById(id)) {
            paiementRepository.deleteById(id);
            return true;
        }
        return false;
    }
    // Additional methods as needed
}
