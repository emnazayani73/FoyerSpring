package webAvance.example.App_Foyer_Universitaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webAvance.example.App_Foyer_Universitaire.entity.Etudiant;
import webAvance.example.App_Foyer_Universitaire.repository.EtudiantRepository;
import webAvance.example.App_Foyer_Universitaire.service.EtudiantService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;
    @Autowired
    private EtudiantRepository etudiantRepository;

    // Ajouter un étudiant
    @PostMapping("/add")
    public Etudiant addEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.addEtudiant(etudiant);
    }

    // Modifier un étudiant
    @PutMapping("/{cin}")
    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable("cin") String cin, @RequestBody Etudiant updatedEtudiant) {
        // Retrieve list of Etudiants by cin
        List<Etudiant> etudiants = etudiantRepository.findByCin(cin);

        // Check if any Etudiant exists with the given cin
        if (!etudiants.isEmpty()) {
            // Loop through the list of Etudiants (even though you may want to ensure only one result for cin)
            Etudiant etudiant = etudiants.get(0); // Assuming the CIN is unique, get the first match
            etudiant.setIsValid(updatedEtudiant.getIsValid());
            etudiant.setChambre(updatedEtudiant.getChambre());
            // Update other fields as necessary
            etudiant.setEmail(updatedEtudiant.getEmail()); // If you want to update email
            etudiant.setNom(updatedEtudiant.getNom()); // If you want to update name
            etudiant.setPrenom(updatedEtudiant.getPrenom()); // If you want to update surname

            // Save the updated Etudiant
            Etudiant savedEtudiant = etudiantRepository.save(etudiant);

            // Return the updated Etudiant in the response
            return ResponseEntity.ok(savedEtudiant);
        } else {
            // If no Etudiant is found with the given cin
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }





    @DeleteMapping("/{cin}")
    public ResponseEntity<String> deleteEtudiant(@PathVariable("cin") String cin) {
        List<Etudiant> etudiants = etudiantRepository.findByCin(cin);

        if (!etudiants.isEmpty()) {
            Etudiant etudiant = etudiants.get(0);  // Assuming CIN is unique
            etudiantRepository.delete(etudiant);
            return ResponseEntity.ok("Etudiant with CIN " + cin + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Etudiant with CIN " + cin + " not found.");
        }
    }


    // Rechercher des étudiants par nom ou CIN
    @GetMapping("/search")
    public ResponseEntity<List<Etudiant>> searchEtudiants(@RequestParam(required = false) String nom,
                                                          @RequestParam(required = false) String cin) {
        return ResponseEntity.ok(etudiantService.searchEtudiants(nom, cin));
    }

    // Afficher tous les étudiants
    @GetMapping("/all")
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        return ResponseEntity.ok(etudiantService.getAllEtudiants());
    }

    @GetMapping("/{cin}")
    public ResponseEntity<List<Etudiant>> getEtudiantsByCin(@PathVariable("cin") String cin) {
        List<Etudiant> etudiants = etudiantService.getEtudiantsByCin(cin);
        if (!etudiants.isEmpty()) {
            return ResponseEntity.ok(etudiants);
        } else {
            return ResponseEntity.status(404).body(null);  // 404 if no students found
        }
    }
}
