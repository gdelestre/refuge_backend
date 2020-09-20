package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.AdoptiveFamily;
import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.services.AdoptiveFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static fr.springboot.refuge.helper.HelperClass.distinctByKey;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AdoptiveFamilyControler {

    @Autowired
    private AdoptiveFamilyService adoptiveFamilyService;

    @GetMapping("/adoptive")
    public List<AdoptiveFamily> getAll() {
        return adoptiveFamilyService.findAll();
    }

    @GetMapping("/adoptive/{id}")
    public AdoptiveFamily getById(@PathVariable int id) {
        return adoptiveFamilyService.findById(id);
    }

    @GetMapping("/adoptive/{id}/animal")
    public List<Animal> getAllAnimalsByHostFamily(@PathVariable int id) {
        return adoptiveFamilyService.findAnimalsByAdoptiveFamily(id);
    }

    @PostMapping("/adoptive")
    public AdoptiveFamily post(@RequestBody AdoptiveFamily adoptiveFamily, HttpServletResponse response) {
        // Récupère la liste des familles adoptantes
        List<AdoptiveFamily> adoptiveFamilies = adoptiveFamilyService.findAll();

        // Ajout de la famille adoptante que l'on souhaite rajouter dans la base de données
        adoptiveFamilies.add(adoptiveFamily);

        // Récupère la liste des  famille adoptantes qui ont des numéros de téléphone différents
        List<AdoptiveFamily> distinctAdoptiveFamily = adoptiveFamilies.stream()
                .filter(distinctByKey(p -> p.getPhoneNumber()))
                .collect(Collectors.toList());

        // Compare la taille des liste de toutes les familles adoptantes (+ celle que l'on veut rajouter) avec celle qui contient toutes les familles qui ont un numéro de téléphone différent
        if (adoptiveFamilies.size() != distinctAdoptiveFamily.size()) {
            response.setStatus(403);
            return new AdoptiveFamily();
        } else {
            adoptiveFamilyService.saveOrUpdate(adoptiveFamily);
            return adoptiveFamily;
        }
    }

    @PutMapping("/adoptive")
    public AdoptiveFamily update(@RequestBody AdoptiveFamily adoptiveFamily) {
        adoptiveFamilyService.saveOrUpdate(adoptiveFamily);
        return adoptiveFamily;
    }

    @DeleteMapping("/adoptive/{id}")
    public String deleteById(@PathVariable int id) {
        adoptiveFamilyService.deleteById(id);
        return "Adoptive family has been deleted with id: " + id;
    }
}