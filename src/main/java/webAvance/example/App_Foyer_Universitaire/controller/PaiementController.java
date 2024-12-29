package webAvance.example.App_Foyer_Universitaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webAvance.example.App_Foyer_Universitaire.entity.Etudiant;
import webAvance.example.App_Foyer_Universitaire.entity.Paiement;
import webAvance.example.App_Foyer_Universitaire.repository.EtudiantRepository;
import webAvance.example.App_Foyer_Universitaire.service.PaiementService;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")  // Updated the URL to be consistent
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @Autowired
    private EtudiantRepository etudiantRepository;

    // Add a new paiement
    @PostMapping("/add")
    public ResponseEntity<Paiement> addPaiement(@RequestBody Paiement paiement) {
        Long etudiantId = paiement.getEtudiant().getId();

        // Check if Etudiant exists
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        if (etudiant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Etudiant not found
        }

        Paiement createdPaiement = paiementService.createPaiement(paiement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaiement);
    }


    // Get all paiements for a student
    @GetMapping("/student/{cin}")
    public ResponseEntity<List<Paiement>> getPaiementsByStudent(@PathVariable("cin") String cin) {

        List<Etudiant> etudiants = etudiantRepository.findByCin(cin); // Get students by CIN

        if (etudiants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // If no student found
        }

        Etudiant etudiant = etudiants.get(0); // Assuming CIN is unique, get the first student
        List<Paiement> paiements = paiementService.getPaiementsByStudent(etudiant); // Correctly call the non-static method
        return ResponseEntity.ok(paiements);
    }

    // Get all paiements by status (e.g., completed, pending)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Paiement>> getPaiementsByStatus(@PathVariable("status") String status) {
        List<Paiement> paiements = paiementService.getPaiementsByStatus(status);
        return ResponseEntity.ok(paiements);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable("id") Long id) {
        boolean isDeleted = paiementService.deletePaiement(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Return 204 if deleted
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 if not found
        }
    }
}
