package webAvance.example.App_Foyer_Universitaire.dto;

public class EtudiantDto {

        private Long id;
        private String nom;
        private String prenom;
        private String email;
        private Boolean isValid;
        private String matricule;

        // Constructeur sans arguments
        public EtudiantDto() {
        }

        // Constructeur avec arguments
        public EtudiantDto(Long id, String nom, String prenom, String email, Boolean isValid , String matricule) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.isValid = isValid;
            this.matricule=matricule;
        }

        // Getters et setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Boolean getIsValid() {
            return isValid;
        }

        public void setIsValid(Boolean isValid) {
            this.isValid = isValid;
        }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

        @Override
        public String toString() {
            return "UtilisateurDTO{" +
                    "id=" + id +
                    ", nom='" + nom + '\'' +
                    ", prenom='" + prenom + '\'' +
                    ", email='" + email + '\'' +
                    ", isValid=" + isValid +
                    '}';
        }
    }


