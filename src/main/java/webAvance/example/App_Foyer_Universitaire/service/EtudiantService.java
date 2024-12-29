package webAvance.example.App_Foyer_Universitaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webAvance.example.App_Foyer_Universitaire.entity.Etudiant;
import webAvance.example.App_Foyer_Universitaire.repository.EtudiantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Ajouter un étudiant
    public Etudiant addEtudiant(Etudiant etudiant) {
        etudiant.setIsValid(false); // Par défaut, l'étudiant n'est pas validé
        String encryptedPassword = passwordEncoder.encode(etudiant.getMotDePasse());
        etudiant.setMotDePasse(encryptedPassword);
        return etudiantRepository.save(etudiant);
    }

    // Mettre à jour un étudiant
    public Etudiant updateEtudiant(String cin, Etudiant updatedEtudiant) {
        List<Etudiant> etudiants = etudiantRepository.findByCin(cin);

        if (!etudiants.isEmpty()) {
            // Assuming cin is unique and taking the first result
            Etudiant etudiant = etudiants.get(0);
            etudiant.setEmail(updatedEtudiant.getEmail());
            etudiant.setNom(updatedEtudiant.getNom());
            etudiant.setPrenom(updatedEtudiant.getPrenom());
            etudiant.setIsValid(updatedEtudiant.getIsValid());
            etudiant.setChambre(updatedEtudiant.getChambre());
            // Add more fields to update as needed

            return etudiantRepository.save(etudiant);
        }
        return null; // Return null if no Etudiant found with the provided cin
    }

    // Supprimer un étudiant
    public void deleteEtudiant(String cin) {
        List<Etudiant> etudiants = etudiantRepository.findByCin(cin);
        if (!etudiants.isEmpty()) {
            etudiantRepository.delete(etudiants.get(0));  // Assuming CIN is unique
        }
    }


    // Rechercher des étudiants par nom
    public List<Etudiant> searchEtudiants(String nom, String cin) {
        if (cin != null && !cin.isEmpty()) {
            return etudiantRepository.findByCin(cin); // Filtrage par CIN
        } else if (nom != null && !nom.isEmpty()) {
            return etudiantRepository.findByNomContainingIgnoreCase(nom); // Filtrage par nom
        } else {
            return etudiantRepository.findAll(); // Retourner tous les étudiants
        }
    }

    // Afficher tous les étudiants
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public List<Etudiant> getEtudiantsByCin(String cin) {
        return etudiantRepository.findByCin(cin);  // This will return a List<Etudiant>
    }
}
