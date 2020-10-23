package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.*;
import fr.springboot.refuge.services.AdoptAnimalService;
import fr.springboot.refuge.services.AdoptiveFamilyService;
import fr.springboot.refuge.services.AnimalService;
import fr.springboot.refuge.services.VeterinaryCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AdoptAnimalController {

    @Autowired
    private AdoptAnimalService adoptAnimalService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private AdoptiveFamilyService adoptiveFamilyService;

    @Autowired
    private VeterinaryCareService veterinaryCareService;

    @GetMapping("/adoption")
    public List<AdoptAnimal> getAll() {
        return adoptAnimalService.findAll();
    }


    @DeleteMapping("/adoption/{idAnimal}")
    public String deleteById(@PathVariable int idAnimal) {

        //Supprime tous les soins pour l'animal
        veterinaryCareService.deleteAllCaresByAnimalId(idAnimal);

        //Récupère l'adoption concerné
        AdoptAnimal adoption = adoptAnimalService.findByAnimalId(idAnimal);

        //Supprime l'adoption
        adoptAnimalService.deleteByAnimalId(idAnimal);

        //Supprime l'animal concerné par l'adoption
        animalService.deleteById(idAnimal);

        //Récupère la liste des adoptions pour la famille d'adoption
        List<AdoptAnimal> adoptionForThisFamily = adoptAnimalService.findByAdoptiveFamilyId(adoption.getAdoptiveFamily().getId());

        //Si la famille n'a pas d'autre adoption, on l'a supprime
        if(adoptionForThisFamily.size() == 0)
            adoptiveFamilyService.deleteById(adoption.getAdoptiveFamily().getId());


        return "Adoption for Animal with id: " + idAnimal+ " has been deleted.";
    }


    @PostMapping("adoption")
    public AdoptAnimal postAdoption(@RequestBody AdoptAnimal adoptAnimal){

        adoptAnimalService.saveOrUpdate(adoptAnimal);
        return adoptAnimal;
    }



}
