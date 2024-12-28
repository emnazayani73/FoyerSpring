package webAvance.example.App_Foyer_Universitaire.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webAvance.example.App_Foyer_Universitaire.dto.JwtResponse;
import webAvance.example.App_Foyer_Universitaire.dto.LoginRequest;
import webAvance.example.App_Foyer_Universitaire.entity.*;
import webAvance.example.App_Foyer_Universitaire.repository.UtilisateurRepository;
import webAvance.example.App_Foyer_Universitaire.util.JwtUtil;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                              JwtUtil jwtUtil,
                              AuthenticationManager authenticationManager,
                              PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    //Inscription Etudiant
    public Etudiant registerEtudiant(Etudiant etudiant) {
        etudiant.setMotDePasse(passwordEncoder.encode(etudiant.getMotDePasse()));
        etudiant.setIsValid(false);
        return utilisateurRepository.save(etudiant);
    }


    //Inscription user
     public Utilisateur registerUtilisateur(Utilisateur utilisateur) {

        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        System.out.println("l'utilisateur" + " " + utilisateur);
        Utilisateur utilisateurType = null;
        switch (utilisateur.getTypeUser()) {
            case "Admin":
                utilisateurType = new Admin();
                break;
            case "ResponsableFinancier":
                utilisateurType = new ResponsableFinancier();
                break;
            case "AgentMaintenance":
                utilisateurType = new AgentMaintenance();
                break;
            default:
                System.out.println("Something went wrong");
                break;
        }


        if (utilisateurType != null) {
            utilisateurType.setEmail(utilisateur.getEmail());
            utilisateurType.setNom(utilisateur.getNom());
            utilisateurType.setPrenom(utilisateur.getPrenom());
            utilisateurType.setMotDePasse(utilisateur.getMotDePasse());
            utilisateurType.setTelephone(utilisateur.getTelephone());


            System.out.println("l'utilisateurType" + " " + utilisateurType);
            return utilisateurRepository.save(utilisateurType);
        }

        return null;
    }

    // valid Eudiant
    public Etudiant validateEtudiant(Long etudiantId) {
        Etudiant etudiant = (Etudiant) utilisateurRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        etudiant.setIsValid(true);
        return utilisateurRepository.save(etudiant);
    }


   //login user
    public JwtResponse login(LoginRequest loginRequest) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(loginRequest.getMotDePasse(), utilisateur.getMotDePasse())) {
            throw new BadCredentialsException("Email ou mot de passe incorrect");
        }

        if (utilisateur instanceof Etudiant) {
            Etudiant etudiant = (Etudiant) utilisateur;
            if (!Boolean.TRUE.equals(etudiant.getIsValid())) {
                throw new BadCredentialsException("L'étudiant n'est pas encore validé.");
            }
        }

        String role = utilisateur.getClass().getSimpleName();
        System.out.println("Le rôle: " + role);
        String token = jwtUtil.generateToken(utilisateur.getEmail(), role);
        return new JwtResponse(token, utilisateur.getId(), utilisateur.getEmail(), role);
    }












}
