package webAvance.example.App_Foyer_Universitaire.controller;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import webAvance.example.App_Foyer_Universitaire.dto.JwtResponse;
import webAvance.example.App_Foyer_Universitaire.dto.LoginRequest;
import webAvance.example.App_Foyer_Universitaire.entity.Etudiant;
import webAvance.example.App_Foyer_Universitaire.entity.Utilisateur;
import webAvance.example.App_Foyer_Universitaire.repository.UtilisateurRepository;
import webAvance.example.App_Foyer_Universitaire.service.UtilisateurService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @PostMapping("/register/etudiant")
    public ResponseEntity<Etudiant> registerEtudiant(@RequestBody Etudiant etudiant) {
        Etudiant nouveauEtudiant = utilisateurService.registerEtudiant(etudiant);
        return ResponseEntity.ok(nouveauEtudiant);
    }


    @PostMapping("/register/utilisateur")
    public ResponseEntity<Utilisateur> registerUtilisateur(@RequestBody Utilisateur utilisateur) {

        Utilisateur nouvelUtilisateur = utilisateurService.registerUtilisateur(utilisateur);
        return ResponseEntity.ok(nouvelUtilisateur);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = utilisateurService.login(loginRequest);

            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification : " + e.getMessage());
        }
    }

    @PutMapping("/{id}/validate")
    public ResponseEntity<Etudiant> validateEtudiant(@PathVariable Long id) {
        Etudiant etudiant = utilisateurService.validateEtudiant(id);
        return ResponseEntity.ok(etudiant);
    }




}
