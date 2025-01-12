package webAvance.example.App_Foyer_Universitaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webAvance.example.App_Foyer_Universitaire.entity.Chambre;
import webAvance.example.App_Foyer_Universitaire.entity.Etudiant;
import webAvance.example.App_Foyer_Universitaire.repository.ChambreRepository;
import webAvance.example.App_Foyer_Universitaire.repository.EtudiantRepository;
import webAvance.example.App_Foyer_Universitaire.repository.UtilisateurRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ChambreService {

    @Autowired
    private ChambreRepository chambreRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    EtudiantRepository etudiantRepository;


    public Chambre createChambre(Chambre chambre) {
        chambre.setStatut("Libre");
        return chambreRepository.save(chambre);
    }


    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }


    public Optional<Chambre> getChambreById(Long id) {
        return chambreRepository.findById(id);
    }

    // Mettre à jour une chambre
    public Chambre updateChambre(Long id, Chambre updatedChambre) {
        if (chambreRepository.existsById(id)) {
            updatedChambre.setId(id);
            return chambreRepository.save(updatedChambre);
        } else {
            return null;
        }
    }


    public boolean deleteChambre(Long id) {
        if (chambreRepository.existsById(id)) {
            chambreRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public List<Chambre> getChambresOccupées() {
        return chambreRepository.findByStatut("Occupée");
    }


    public List<Chambre> getChambresLibres() {
        return chambreRepository.findByStatut("Libre");
    }


    public String assignChambreToEtudiant(Long etudiantId, Long chambreId) {
        Optional<Chambre> chambreOptional = chambreRepository.findById(chambreId);
        if (chambreOptional.isEmpty()) {
            return "La chambre avec l'ID " + chambreId + " n'existe pas.";
        }

        Chambre chambre = chambreOptional.get();

        if (chambre.getPlaceDejaOccupe() >= chambre.getCapacite()) {
            return "La chambre avec l'ID " + chambreId + " est déjà pleine.";
        }

        Optional<Etudiant> etudiantOptional = etudiantRepository.findById(etudiantId);
        if (etudiantOptional.isEmpty()) {
            return "L'étudiant avec l'ID " + etudiantId + " n'existe pas.";
        }

        Etudiant etudiant = etudiantOptional.get();
        etudiant.setChambre(chambre);
        etudiantRepository.save(etudiant);

        chambre.setPlaceDejaOccupe(chambre.getPlaceDejaOccupe() + 1);
        if (chambre.getPlaceDejaOccupe() >= chambre.getCapacite()) {
            chambre.setStatut("Occupée");
        }

        chambreRepository.save(chambre);

        return "La chambre avec l'ID " + chambreId + " a été assignée à l'étudiant avec l'ID " + etudiantId + ".";
    }





}
