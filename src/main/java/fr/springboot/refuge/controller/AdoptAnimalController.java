package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.*;
import fr.springboot.refuge.services.AdoptAnimalService;
import fr.springboot.refuge.services.AdoptiveFamilyService;
import fr.springboot.refuge.services.AnimalService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://refuge-aws.s3-website.eu-west-3.amazonaws.com")
//@CrossOrigin(origins = "http://localhost:4200")
public class AdoptAnimalController {

    @Autowired
    private AdoptAnimalService adoptAnimalService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private AdoptiveFamilyService adoptiveFamilyService;


    @GetMapping("/adoption")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public List<AdoptAnimal> getAll() {
        return adoptAnimalService.findAll();
    }

    @DeleteMapping("/adoption/{idAnimal}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteById(@PathVariable int idAnimal, HttpServletResponse response) {

        Map<String, String> result = new HashMap<String, String>();

        //Récupère l'adoption concerné
        AdoptAnimal adoption = adoptAnimalService.findByAnimalId(idAnimal);

        //Supprime l'adoption
        adoptAnimalService.deleteByAnimalId(idAnimal);
        result.put("animal message", "adoption has been deleted");
        result.put("animal id", String.valueOf(idAnimal));
        result.put("status", String.valueOf(response.getStatus()));

        //Récupère la liste des adoptions pour la famille d'adoption
        List<AdoptAnimal> adoptionForThisFamily = adoptAnimalService.findByAdoptiveFamilyId(adoption.getAdoptiveFamily().getId());

        //Si la famille n'a pas d'autre adoption, on l'a supprime
        if(adoptionForThisFamily.size() == 0){
            adoptiveFamilyService.deleteById(adoption.getAdoptiveFamily().getId());
            result.put("family_message", "family has been deleted");
            result.put("family id", String.valueOf(adoption.getAdoptiveFamily().getId()));
            result.put("status", String.valueOf(response.getStatus()));
        }
        return JSONObject.toJSONString(result);
    }

    @PostMapping("adoption")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public AdoptAnimal postAdoption(@RequestBody AdoptAnimal adoptAnimal){

        adoptAnimalService.saveOrUpdate(adoptAnimal);
        return adoptAnimal;
    }

}
