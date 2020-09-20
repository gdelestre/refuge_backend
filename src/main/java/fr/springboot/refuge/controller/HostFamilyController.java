package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.HostFamily;
import fr.springboot.refuge.services.HostFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static fr.springboot.refuge.helper.HelperClass.distinctByKey;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class HostFamilyController {

    @Autowired
    private HostFamilyService hostFamilyService;

    @GetMapping("/host")
    public List<HostFamily> getAll() {
        return hostFamilyService.findAll();
    }

    @GetMapping("/host/{id}")
    public HostFamily getById(@PathVariable int id) {
        return hostFamilyService.findById(id);
    }

    @GetMapping("/host/{id}/animal")
    public List<Animal> getAllAnimalsByHostFamily(@PathVariable int id) {
        return hostFamilyService.findAnimalsByHostFamily(id);
    }

    @PostMapping("/host")
    public HostFamily post(@RequestBody HostFamily hostFamily, HttpServletResponse response) {
        // Récupère la liste de toutes les familles d'accueil
        List<HostFamily> hostFamilies = hostFamilyService.findAll();

        // Ajout de la famille d'accueil que l'on souhaite rajouter dans la base de données
        hostFamilies.add(hostFamily);

        // Récupère la liste des familles d'accueil qui ont des numéros de téléphone différents
        List<HostFamily> distinctHostFamily = hostFamilies.stream()
                .filter(distinctByKey(p -> p.getPhoneNumber()))
                .collect(Collectors.toList());

        // Compare la taille des liste de toutes les familles d'accueil (+ celle que l'on veut rajouter) avec celle qui contient toutes les familles qui ont un numéro de téléphone différent
        if (hostFamilies.size() != distinctHostFamily.size()) {
            response.setStatus(403);
            return new HostFamily();
        } else {
            hostFamilyService.saveOrUpdate(hostFamily);
            return hostFamily;
        }
    }

    @PutMapping("/host")
    public HostFamily update(@RequestBody HostFamily hostFamily) {
        hostFamilyService.saveOrUpdate(hostFamily);
        return hostFamily;
    }

    @DeleteMapping("/host/{id}")
    public String deleteById(@PathVariable int id) {
        hostFamilyService.deleteById(id);
        return "Host family has been deleted with id: " + id;
    }
}