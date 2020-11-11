package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.Veterinary;
import fr.springboot.refuge.entity.VeterinaryCare;
import fr.springboot.refuge.services.AnimalService;
import fr.springboot.refuge.services.VeterinaryCareService;
import fr.springboot.refuge.services.VeterinaryService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class VeterinaryCareController {

    @Autowired
    private VeterinaryCareService veterinaryCareService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private VeterinaryService veterinaryService;

    @GetMapping("/care/{id}")
    public VeterinaryCare getCareById(@PathVariable int id){
        return veterinaryCareService.findById(id);
    }

    @GetMapping("/care")
    public List<VeterinaryCare> getCaresToDo(){
        return veterinaryCareService.findCaresToDo();
    }
    
    @GetMapping("/{id}/care")
    public List<VeterinaryCare> getAllByAnimalId(@PathVariable int id){
        return veterinaryCareService.findAllByAnimalId(id);
    }

    @PostMapping("animal/{animalId}/veterinary/{veterinaryId}/care")
    public VeterinaryCare postCare(@PathVariable int animalId, @PathVariable int veterinaryId, @RequestBody VeterinaryCare veterinaryCare){
        //On vérifie que les deux Id ne sont pas null
        if(animalId != 0 && veterinaryId != 0){
            // Récupération de l'animal concerné par le soin via son Id
            Animal animal = animalService.findById(animalId);

            // Récupération du vétérinaire qui va faire le soin via son Id
            Veterinary veterinary = veterinaryService.findById(veterinaryId);

            // Association de l'animal et du vétérinaire au soin crée
            veterinaryCare.setAnimal(animal);
            veterinaryCare.setVeterinary(veterinary);
        }
        veterinaryCareService.saveOrUpdate(veterinaryCare);
        return veterinaryCare;
    }

    @PutMapping("animal/{animalId}/veterinary/{veterinaryId}/care")
    public VeterinaryCare putCare(@PathVariable int animalId, @PathVariable int veterinaryId, @RequestBody VeterinaryCare veterinaryCare){
        //On vérifie que les deux Id ne sont pas null
        if(animalId != 0 && veterinaryId != 0){
            // Récupération de l'animal concerné par le soin via son Id
            Animal animal = animalService.findById(animalId);

            // Récupération du vétérinaire qui va faire le soin via son Id
            Veterinary veterinary = veterinaryService.findById(veterinaryId);

            // Association de l'animal et du vétérinaire au soin crée
            veterinaryCare.setAnimal(animal);
            veterinaryCare.setVeterinary(veterinary);
        }
        veterinaryCareService.saveOrUpdate(veterinaryCare);
        return veterinaryCare;
    }

    @DeleteMapping("/care/{id}")
    public String deleteCare(@PathVariable int id, HttpServletResponse response){
        Map<String, String> result = new HashMap<String, String>();

        veterinaryCareService.deleteById(id);
        result.put("message", "care has been deleted");
        result.put("id care", String.valueOf(id));
        result.put("status", String.valueOf(response.getStatus()));

        return JSONObject.toJSONString(result);
    }

}
