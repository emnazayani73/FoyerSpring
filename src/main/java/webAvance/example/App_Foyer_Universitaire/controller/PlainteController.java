package webAvance.example.App_Foyer_Universitaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webAvance.example.App_Foyer_Universitaire.entity.Plainte;
import webAvance.example.App_Foyer_Universitaire.service.PlainteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plaintes")
public class PlainteController {

    @Autowired
    private PlainteService plainteService;

    // Create or update a plainte
    @PostMapping("/create")
    public ResponseEntity<Plainte> savePlainte(@RequestBody Plainte plainte) {
        Plainte savedPlainte = plainteService.savePlainte(plainte);
        return new ResponseEntity<>(savedPlainte, HttpStatus.CREATED);
    }

    // Get all plaintes
    @GetMapping
    public ResponseEntity<List<Plainte>> getAllPlaintes() {
        List<Plainte> plaintes = plainteService.getAllPlaintes();
        return new ResponseEntity<>(plaintes, HttpStatus.OK);
    }

    // Get a plainte by ID
    @GetMapping("/{id}")
    public ResponseEntity<Plainte> getPlainteById(@PathVariable Long id) {
        Optional<Plainte> plainte = plainteService.getPlainteById(id);
        if (plainte.isPresent()) {
            return new ResponseEntity<>(plainte.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a plainte by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlainte(@PathVariable Long id) {
        try {
            plainteService.deletePlainte(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
