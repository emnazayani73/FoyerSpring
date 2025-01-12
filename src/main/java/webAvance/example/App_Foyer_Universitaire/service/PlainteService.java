package webAvance.example.App_Foyer_Universitaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webAvance.example.App_Foyer_Universitaire.entity.Plainte;
import webAvance.example.App_Foyer_Universitaire.repository.PlainteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlainteService {

    @Autowired
    private PlainteRepository plainteRepository;


    public Plainte savePlainte(Plainte plainte) {
        plainte.setStatut("Soumise");
        return plainteRepository.save(plainte);
    }


    public List<Plainte> getAllPlaintes() {
        return plainteRepository.findAll();
    }


    public Optional<Plainte> getPlainteById(Long id) {
        return plainteRepository.findById(id);
    }


    public void deletePlainte(Long id) {
        if (plainteRepository.existsById(id)) {
            plainteRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Plainte avec l'ID " + id + " n'existe pas.");
        }
    }
}
