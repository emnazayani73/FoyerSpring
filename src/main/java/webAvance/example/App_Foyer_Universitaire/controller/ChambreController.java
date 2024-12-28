package webAvance.example.App_Foyer_Universitaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webAvance.example.App_Foyer_Universitaire.entity.Chambre;
import webAvance.example.App_Foyer_Universitaire.service.ChambreService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chambres")
public class ChambreController {

    @Autowired
    private ChambreService chambreService;


    @PostMapping("/create")
    public ResponseEntity<Chambre> createChambre(@RequestBody Chambre chambre) {
        Chambre createdChambre = chambreService.createChambre(chambre);
        return new ResponseEntity<>(createdChambre, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Chambre>> getAllChambres() {
        List<Chambre> chambres = chambreService.getAllChambres();
        return new ResponseEntity<>(chambres, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Chambre> getChambreById(@PathVariable Long id) {
        Optional<Chambre> chambre = chambreService.getChambreById(id);
        return chambre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Chambre> updateChambre(@PathVariable Long id, @RequestBody Chambre updatedChambre) {
        Chambre updated = chambreService.updateChambre(id, updatedChambre);
        return updated != null ? new ResponseEntity<>(updated, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteChambre(@PathVariable Long id) {
        boolean isDeleted = chambreService.deleteChambre(id);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("La chambre avec l'ID " + id + " a été supprimée avec succès.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("La chambre avec l'ID " + id + " n'existe pas.");
        }
    }




    @GetMapping("/status/occupée")
    public ResponseEntity<List<Chambre>> getChambresOccupées() {
        List<Chambre> chambres = chambreService.getChambresOccupées();
        return new ResponseEntity<>(chambres, HttpStatus.OK);
    }


    @GetMapping("/status/libre")
    public ResponseEntity<List<Chambre>> getChambresLibres() {
        List<Chambre> chambres = chambreService.getChambresLibres();
        return new ResponseEntity<>(chambres, HttpStatus.OK);
    }

    @PostMapping("/{etudiantId}/assign-chambre/{chambreId}")
    public ResponseEntity<String> assignChambre(@PathVariable Long etudiantId, @PathVariable Long chambreId) {
        String result = chambreService.assignChambreToEtudiant(etudiantId, chambreId);
        return ResponseEntity.ok(result);
    }
}
