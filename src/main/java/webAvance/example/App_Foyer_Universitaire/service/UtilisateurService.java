package webAvance.example.App_Foyer_Universitaire.service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webAvance.example.App_Foyer_Universitaire.dto.JwtResponse;
import webAvance.example.App_Foyer_Universitaire.dto.LoginRequest;
import webAvance.example.App_Foyer_Universitaire.dto.EtudiantDto;
import webAvance.example.App_Foyer_Universitaire.entity.*;
import webAvance.example.App_Foyer_Universitaire.repository.EtudiantRepository;
import webAvance.example.App_Foyer_Universitaire.repository.UtilisateurRepository;
import webAvance.example.App_Foyer_Universitaire.util.JwtUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private  final EtudiantRepository etudiantRepository;
    public UtilisateurService(UtilisateurRepository utilisateurRepository,
                              JwtUtil jwtUtil,
                              EtudiantRepository etudiantRepository,
                              AuthenticationManager authenticationManager,
                              PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.etudiantRepository = etudiantRepository;
    }

    //Inscription Etudiant
    public Etudiant registerEtudiant(Etudiant etudiant) {
        etudiant.setMotDePasse(passwordEncoder.encode(etudiant.getMotDePasse()));
        etudiant.setIsValid(false);
        return utilisateurRepository.save(etudiant);
    }


    //Inscription user
    public Utilisateur registerUtilisateur(Utilisateur utilisateur) {
        // Encoder le mot de passe
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        // Afficher l'utilisateur dans la console pour debug
        System.out.println("L'utilisateur : " + utilisateur);

        Utilisateur utilisateurType = null;

        // Vérification que le matricule n'est pas null ni vide
        if (utilisateur.getMatricule() == null || utilisateur.getMatricule().isEmpty()) {
            utilisateur.setMatricule("000");  // Définir la valeur par défaut
            System.out.println("Matricule manquant, valeur par défaut définie : " + utilisateur.getMatricule());
        }

        // Déterminer le type de l'utilisateur
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
                System.out.println("Type d'utilisateur inconnu");
                break;
        }

        // Vérifier que l'utilisateurType a bien été assigné
        if (utilisateurType != null) {
            // Mapper les propriétés communes de l'utilisateur
            utilisateurType.setEmail(utilisateur.getEmail());
            utilisateurType.setNom(utilisateur.getNom());
            utilisateurType.setPrenom(utilisateur.getPrenom());
            utilisateurType.setMotDePasse(utilisateur.getMotDePasse());
            utilisateurType.setTelephone(utilisateur.getTelephone());
            utilisateurType.setMatricule(utilisateur.getMatricule());  // Assigner le matricule
            utilisateurType.setTypeUser(utilisateur.getTypeUser());

            // Afficher l'utilisateurType dans la console pour debug
            System.out.println("L'utilisateurType : " + utilisateurType);

            // Sauvegarder l'utilisateur dans la base de données
            return utilisateurRepository.save(utilisateurType);
        } else {
            System.out.println("L'utilisateur n'a pas pu être créé car le type est invalide");
            return null;
        }
    }



    // valid Eudiant
    public Etudiant validateEtudiant(Long etudiantId) {
        Etudiant etudiant = (Etudiant) utilisateurRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        etudiant.setIsValid(true);
        return utilisateurRepository.save(etudiant);
    }


    // List Etudaint Invalid
    public List<EtudiantDto> getInvalidUtilisateurs() {
        System.out.println("Je suis dans la fonction service");
        return etudiantRepository.findInvalidEtudiants().stream()
                .map(etudiant -> new EtudiantDto(
                        etudiant.getId(),
                        etudiant.getNom(),
                        etudiant.getPrenom(),
                        etudiant.getEmail(),
                        etudiant.getIsValid(),
                        etudiant.getMatricule()

                ))
                .collect(Collectors.toList());
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

    // Récupération des users
    public List<Utilisateur> getAllUtilisateursAvecTypesSpecifiques() {
        Set<String> typesValid = new HashSet<>(Arrays.asList("ResponsableFinancier", "Admin", "AgentMaintenance"));
        return utilisateurRepository.findAll().stream()
                .filter(utilisateur -> typesValid.contains(utilisateur.getTypeUser()))
                .collect(Collectors.toList());
    }

    // update user
    public Utilisateur updateUtilisateur(Long id, Utilisateur updatedUtilisateur) {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        existingUtilisateur.setNom(updatedUtilisateur.getNom());
        existingUtilisateur.setPrenom(updatedUtilisateur.getPrenom());
        existingUtilisateur.setTelephone(updatedUtilisateur.getTelephone());
        existingUtilisateur.setEmail(updatedUtilisateur.getEmail());
        existingUtilisateur.setMatricule(updatedUtilisateur.getMatricule());
        existingUtilisateur.setTypeUser(updatedUtilisateur.getTypeUser());

        return utilisateurRepository.save(existingUtilisateur);
    }

    //delete user
    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        utilisateurRepository.deleteById(id);
    }

    // Récupérer tous les étudiants valides
    public List<EtudiantDto> getValidEtudiants() {
        return etudiantRepository.findValidEtudiants().stream()
                .map(etudiant -> new EtudiantDto(
                        etudiant.getId(),
                        etudiant.getNom(),
                        etudiant.getPrenom(),
                        etudiant.getEmail(),
                        etudiant.getIsValid(),
                        etudiant.getMatricule()
                ))
                .collect(Collectors.toList());
    }

    public List<EtudiantDto> getEtudiantsSansChambre() {
        List<Etudiant> etudiants = etudiantRepository.findEtudiantsSansChambre();

        // Convertir les entités en DTOs
        return etudiants.stream()
                .map(etudiant -> {
                    EtudiantDto dto = new EtudiantDto();
                    dto.setId(etudiant.getId());
                    dto.setNom(etudiant.getNom());
                    dto.setPrenom(etudiant.getPrenom());
                    dto.setEmail(etudiant.getEmail());
                    dto.setIsValid(etudiant.getIsValid());
                    dto.setMatricule(etudiant.getMatricule());
                    return dto;
                })
                .collect(Collectors.toList());
    }








}
