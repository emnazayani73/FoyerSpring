package webAvance.example.App_Foyer_Universitaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webAvance.example.App_Foyer_Universitaire.service.StatistiqueService;

import java.util.Map;

@RestController
@RequestMapping("/api/statistiques")
public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;

    @GetMapping("/chambres")
    public Map<String, Long> getStatistiquesChambres() {
        return Map.of(
                "total", statistiqueService.getTotalChambres(),
                "libres", statistiqueService.countChambresLibres(),
                "occupees", statistiqueService.countChambresOccupees()
        );
    }

    @GetMapping("/etudiants")
    public Map<String, Long> getNombreEtudiants() {
        return Map.of("nombreEtudiants", statistiqueService.getNombreEtudiantsInscrits());
    }

   /* @GetMapping("/paiements")
    public Map<String, Double> getStatistiquesPaiements() {
        return Map.of(
                "totalFraisPayes", statistiqueService.getTotalFraisPayes(),
                "totalFraisImpayes", statistiqueService.getTotalFraisImpayes()
        );
    }*/
}
