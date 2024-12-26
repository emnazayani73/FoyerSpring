package webAvance.example.App_Foyer_Universitaire.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Clé secrète générée pour signer le JWT
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Méthode pour générer un token avec un rôle formaté
    public String generateToken(String email, String role) {
        // Ajouter le préfixe ROLE_ au rôle
//        String formattedRole = "ROLE_" + role;

        return Jwts.builder()
                .setSubject(email)  // Ajoute l'email comme sujet du token
                .claim("role", role)  // Ajoute le rôle avec le préfixe ROLE_
                .setIssuedAt(new Date())  // Date d'émission du token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Expiration après 10 heures
                .signWith(SECRET_KEY)  // Utilisation de la clé secrète pour signer le JWT
                .compact();  // Création du JWT compacté
    }

    // Méthode pour extraire l'email du token
    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)  // Utilisation de la clé pour valider le token
                .parseClaimsJws(token)
                .getBody()
                .getSubject();  // Retourne l'email du token
    }
}
