package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.VeterinaryCare;
import fr.springboot.refuge.services.AnimalService;
import fr.springboot.refuge.services.VeterinaryCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private VeterinaryCareService veterinaryCareService;

    @GetMapping("/animal")
    public List<Animal> getAll(){
        return animalService.findAll();
    }


    @GetMapping("/animal/host")
    public List<Animal> getWithHostFamily(){
        return animalService.findWithHostFamily();
    }

    @GetMapping("/animal/adoptive")
    public List<Animal> getAdopted(){
        return animalService.findAdopted();
    }

    @GetMapping("/animal/species/{species}")
    public List<Animal> getBySpecies(@PathVariable String species){
        return animalService.findBySpecies(species);
    }

    @GetMapping("/animal/{id}")
    public Animal getById(@PathVariable int id){
        return animalService.findById(id);
    }

    @PostMapping("/animal")
    public Animal save(@RequestBody Animal animal, HttpServletResponse response){
        //Cherche un animal avec le nom de l'animal du formulaire.
        Animal animalDB = animalService.findByName(animal.getName());

        //Si on trouve un animal : le nom est déjà utilisé.
        if(animalDB != null){
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

        try{
            //Si on un trouve un animal et que son ID est différent de celui de l'animal du formulaire
            if(animalDB != null && animalDB.getId() != animal.getId()){
                response.setStatus(403);
                return new Animal();
            }

            //Sinon on met à jour l'animal
            animalService.saveOrUpdate(animal);
            return animal;

            //Conflit entre l'animal de la base de donnée (animalDB) et l'animal du formulaire: même ID mais sont différents.
        }catch(Exception exception){
            animalService.saveOrUpdate(animal);
            return animal;
        }

    }

    @DeleteMapping("/animal/{id}")
    public String deleteById(@PathVariable int id, HttpServletResponse response){
        List<VeterinaryCare> cares = veterinaryCareService.findAllByAnimalId(id);

        if(cares.size() == 0){
            animalService.deleteById(id);
            return "Animal has been deleted with id: "+id;
        }else{
            response.setStatus(403);
            return "You can't delete an animal with cares";
        }
    }

}
