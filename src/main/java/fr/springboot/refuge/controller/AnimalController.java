package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.VeterinaryCare;
import fr.springboot.refuge.services.AnimalService;
import fr.springboot.refuge.services.VeterinaryCareService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://refuge-aws.s3-website.eu-west-3.amazonaws.com")
//@CrossOrigin(origins = "http://localhost:4200")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private VeterinaryCareService veterinaryCareService;

    @GetMapping("/animal")
    public List<Animal> getAll() {
        return animalService.findAll();
    }

    @GetMapping("/animal/host")
    public List<Animal> getWithHostFamily() {
        return animalService.findWithHostFamily();
    }

    @GetMapping("/animal/adoptive")
    public List<Animal> getAdopted() {
        return animalService.findAdopted();
    }

    @GetMapping("/animal/species/{species}")
    public List<Animal> getBySpecies(@PathVariable String species) {
        return animalService.findBySpecies(species);
    }

    @GetMapping("/animal/{id}")
    public Animal getById(@PathVariable int id) {
        return animalService.findById(id);
    }

    @PostMapping("/animal")
    public Animal save(@RequestBody Animal animal, HttpServletResponse response) {
        //Cherche un animal avec le nom de l'animal du formulaire.
        Animal animalDB = animalService.findByName(animal.getName());

        //Si on trouve un animal : le nom est déjà utilisé.
        if (animalDB != null) {
            response.setStatus(403);
            return new Animal();
        }

        animalService.saveOrUpdate(animal);
        return animal;
    }

    @PutMapping("/animal")
    public Animal update(@RequestBody Animal animal, HttpServletResponse response) {
        //Cherche un animal avec le nom de l'animal du formulaire.
        Animal animalDB = animalService.findByName(animal.getName());

        try {
            //Si on un trouve un animal et que son ID est différent de celui de l'animal du formulaire
            if (animalDB != null && animalDB.getId() != animal.getId()) {
                response.setStatus(403);
                return new Animal();
            }

            //Sinon on met à jour l'animal
            animalService.saveOrUpdate(animal);
            return animal;

            //Conflit entre l'animal de la base de donnée (animalDB) et l'animal du formulaire: même ID mais sont différents.
        } catch (Exception exception) {
            animalDB = null;
            animalService.saveOrUpdate(animal);
            return animal;
        }

    }

    @DeleteMapping("/animal/{id}")
    public String deleteById(@PathVariable int id, HttpServletResponse response) {
        boolean canDelete = true;
        Map<String, String> result = new HashMap<String, String>();
        Date now = new Date();
        List<VeterinaryCare> cares = veterinaryCareService.findAllByAnimalId(id);

        //Si l'animal a eu des soins ou va en avoir
        if (cares.size() > 0) {
            for (VeterinaryCare care : cares) {
                //On regarde si les soins ont été fait ou sont à prévoir.
                //Si le soin est à prévoir --> on peut pas supprimer
                if (care.getExamenDate().after(now)) {
                    canDelete = false;
                }
            }
        }

        //Si on peut supprimer
        if (canDelete) {

            //Si l'animal a eu des soins, on les supprime tous.
            if (cares.size() > 0) {
                veterinaryCareService.deleteAllCaresByAnimalId(id);
                result.put("cares", "cares has been deleted");
            }

            animalService.deleteById(id);
            result.put("message", "animal has been deleted");
            result.put("id", String.valueOf(id));
            result.put("status", String.valueOf(response.getStatus()));
            return JSONObject.toJSONString(result);
        } else {
            response.setStatus(403);
            result.put("message", "can't delete with care to do");
            result.put("status", String.valueOf(response.getStatus()));
            return JSONObject.toJSONString(result);
        }
    }

}
