package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.VeterinaryCare;
import fr.springboot.refuge.services.AnimalService;
import fr.springboot.refuge.services.VeterinaryCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static fr.springboot.refuge.helper.HelperClass.distinctByKey;

@RestController
@RequestMapping("/api")
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

    @GetMapping("/animal/{id}")
    public Animal getById(@PathVariable int id){
        return animalService.findById(id);
    }

    @PostMapping("/animal")
    public Animal save(@RequestBody Animal animal, HttpServletResponse response){
        // Récupère la liste de tous les animaux
        List<Animal> animals = animalService.findAll();

        // Ajout de l'animal que l'on souhaite rajouter de la base données
        animals.add(animal);

        // Récupère la liste des animaux qui ont des noms différents
        List<Animal> distinctAnimals = animals.stream()
                .filter( distinctByKey(p -> p.getName()) )
                .collect( Collectors.toList() );

        // Compare la taille des liste de tous les animaux (+ celui que l'on veut rajouter) avec celle qui contient tous les animaux qui ont un nom différent
        if(animals.size() != distinctAnimals.size()){
            response.setStatus(403);
            return new Animal();
        }else{
            animalService.saveOrUpdate(animal);
            return animal;
        }
    }

    @PutMapping("/animal")
    public Animal update(@RequestBody Animal animal){
        animalService.saveOrUpdate(animal);
        return animal;
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
