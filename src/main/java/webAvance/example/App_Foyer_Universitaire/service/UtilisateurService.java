package webAvance.example.App_Foyer_Universitaire.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webAvance.example.App_Foyer_Universitaire.entity.Utilisateur;
import webAvance.example.App_Foyer_Universitaire.repository.UtilisateurRepository;
import webAvance.example.App_Foyer_Universitaire.util.JwtUtil;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    // Inscription d'un Etudaint
    public Utilisateur register(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé !");
        }

        String encodedPassword = passwordEncoder.encode(utilisateur.getMotDePasse());
        utilisateur.setMotDePasse(encodedPassword);

        utilisateur.setRole("ETUDIANT");

        return utilisateurRepository.save(utilisateur);
    }

    public String login(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
            throw new BadCredentialsException("Email ou mot de passe incorrect");
        }

        // Génération du token JWT
        System.out.println("le role" + utilisateur.getRole());
        return jwtUtil.generateToken(utilisateur.getEmail() , utilisateur.getRole());
    }

    public Utilisateur addEmployee(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé !");
        }

        // Encoder le mot de passe
        String encodedPassword = passwordEncoder.encode(utilisateur.getMotDePasse());
        utilisateur.setMotDePasse(encodedPassword);

        // Si le rôle n'est pas fourni, tu peux ajouter une vérification ou le définir par défaut
        if (utilisateur.getRole() == null || utilisateur.getRole().isEmpty()) {
            throw new IllegalArgumentException("Le rôle est requis !");
        }

        return utilisateurRepository.save(utilisateur);
    }





}
