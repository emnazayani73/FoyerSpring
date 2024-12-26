package webAvance.example.App_Foyer_Universitaire.controller;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import webAvance.example.App_Foyer_Universitaire.dto.LoginRequest;
import webAvance.example.App_Foyer_Universitaire.entity.Utilisateur;
import webAvance.example.App_Foyer_Universitaire.service.UtilisateurService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/register")
    public ResponseEntity<Utilisateur> register(@RequestBody Utilisateur utilisateur) {
        Utilisateur utilisateurEnregistre = utilisateurService.register(utilisateur);
        return ResponseEntity.ok(utilisateurEnregistre);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = utilisateurService.login(loginRequest.getEmail(), loginRequest.getMotDePasse());
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/registerEmployee")
    public ResponseEntity<?> registerEmployee(@RequestBody Utilisateur utilisateur) {
        try {
            // Appel de la méthode dans le service pour ajouter l'employé
            Utilisateur employeEnregistre = utilisateurService.addEmployee(utilisateur);
            return ResponseEntity.ok(employeEnregistre);
        } catch (IllegalArgumentException e) {
            // Retourner un message d'erreur directement sans la classe UtilisateurErrorResponse
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }







}
