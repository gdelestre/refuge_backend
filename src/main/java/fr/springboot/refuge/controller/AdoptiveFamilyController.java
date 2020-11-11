package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.AdoptiveFamily;
import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.services.AdoptiveFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AdoptiveFamilyController {

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

    @GetMapping("/adoptive/phone/{phoneNumber}")
    public AdoptiveFamily getByPhoneNumber(@PathVariable String phoneNumber) {
        return adoptiveFamilyService.findByPhoneNumber(phoneNumber);
    }

    /*
    @GetMapping("/adoptive/{id}/animal")
    public List<Animal> getAllAnimalsByHostFamily(@PathVariable int id) {
        return adoptiveFamilyService.findAnimalsByAdoptiveFamily(id);
    }
     */

    @PostMapping("/adoptive")
    public AdoptiveFamily post(@RequestBody AdoptiveFamily adoptiveFamily, HttpServletResponse response) {
        //Cherche une famille dans la BD avec le numéro de téléphone saisi
        AdoptiveFamily familyDB = adoptiveFamilyService.findByPhoneNumber(adoptiveFamily.getPhoneNumber());

        //Si on trouve une famille : le numéro de téléphone est déjà utilisé
        if (familyDB != null) {
            response.setStatus(403);
            return new AdoptiveFamily();
        }
        adoptiveFamilyService.saveOrUpdate(adoptiveFamily);
        return adoptiveFamily;
    }

    @PutMapping("/adoptive")
    public AdoptiveFamily update(@RequestBody AdoptiveFamily adoptiveFamily, HttpServletResponse response) {
        //Cherche une famille dans la BD avec le numéro de téléphone saisi
        AdoptiveFamily familyDB = adoptiveFamilyService.findByPhoneNumber(adoptiveFamily.getPhoneNumber());

        try {
            //Si on un trouve une famille et que son ID est différent de celui de la famille du formulaire
            if (familyDB != null && familyDB.getId() != adoptiveFamily.getId()) {
                response.setStatus(403);
                return new AdoptiveFamily();
            }

            //Sinon on met à jour la famille
            adoptiveFamilyService.saveOrUpdate(adoptiveFamily);
            return adoptiveFamily;

            //Conflit entre la famille de la base de donnée (familyDB) et la famille du formulaire: même ID mais sont différents.
        } catch (Exception exception) {
            familyDB = null;
            adoptiveFamilyService.saveOrUpdate(adoptiveFamily);
            return adoptiveFamily;
        }
    }
}