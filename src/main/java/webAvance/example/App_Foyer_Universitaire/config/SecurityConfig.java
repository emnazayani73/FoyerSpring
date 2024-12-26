package webAvance.example.App_Foyer_Universitaire.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/register", "/api/auth/login" , "/api/auth/registerEmployee").permitAll()
                        //.requestMatchers("/api/auth/registerEmployee").hasRole("ADMIN")
                        .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                )
                .csrf(csrf -> csrf.disable()); // Désactive la protection CSRF si nécessaire

        return http.build();
    }
}
