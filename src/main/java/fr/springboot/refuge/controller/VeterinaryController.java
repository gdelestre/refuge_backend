package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.Veterinary;
import fr.springboot.refuge.services.VeterinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static fr.springboot.refuge.helper.HelperClass.distinctByKey;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class VeterinaryController {

    @Autowired
    private VeterinaryService veterinaryService;

    @GetMapping("/veterinary")
    public List<Veterinary> getAll() {
        return veterinaryService.findAll();
    }

    @GetMapping("/veterinary/{id}")
    public Veterinary getById(@PathVariable int id) {
        return veterinaryService.findById(id);
    }

    @PostMapping("/veterinary")
    public Veterinary post(@RequestBody Veterinary veterinary, HttpServletResponse response) {
        // Récupère la liste de tous les vétérinaires
        List<Veterinary> veterinaries = veterinaryService.findAll();

        // Ajout du vétérinaire que l'on souhaite rajouter dans la base de données
        veterinaries.add(veterinary);

        // Récupère la liste des vétérinaires qui ont des numéros de téléphone différents
        List<Veterinary> distinctVeterinaries = veterinaries.stream()
                .filter(distinctByKey(p -> p.getPhoneNumber()))
                .collect(Collectors.toList());

        // Compare la taille des liste de tous les vétérinaires (+ celui que l'on veut rajouter) avec celle qui contient tous les vétérinaires qui ont un numéro de téléphone différent
        if (veterinaries.size() != distinctVeterinaries.size()) {
            response.setStatus(403);
            return new Veterinary();
        } else {
            veterinaryService.saveOrUpdate(veterinary);
            return veterinary;
        }
    }

    @PutMapping("/veterinary")
    public Veterinary update(@RequestBody Veterinary veterinary) {
        veterinaryService.saveOrUpdate(veterinary);
        return veterinary;
    }

    @DeleteMapping("/veterinary/{id}")
    public String deleteById(@PathVariable int id) {
        veterinaryService.deleteById(id);
        return "Veterinary has been deleted with id: " + id;
    }
}
