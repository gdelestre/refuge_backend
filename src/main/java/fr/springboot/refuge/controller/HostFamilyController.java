package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.AdoptiveFamily;
import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.HostFamily;
import fr.springboot.refuge.services.HostFamilyService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://refuge-aws.s3-website.eu-west-3.amazonaws.com")
//@CrossOrigin(origins = "http://localhost:4200")
public class HostFamilyController {

    @Autowired
    private HostFamilyService hostFamilyService;

    @GetMapping("/host/free")
    public List<HostFamily> getFreeFamilies() {
        return hostFamilyService.findFreeFamilies();
    }

    @GetMapping("/host/full")
    public List<HostFamily> getFullFamilies() {
        return hostFamilyService.findFullFamilies();
    }

    @GetMapping("/host/{id}")
    public HostFamily getById(@PathVariable int id) {
        return hostFamilyService.findById(id);
    }

    @GetMapping("/host/phone/{phoneNumber}")
    public HostFamily getByPhoneNumber(@PathVariable String phoneNumber) {
        return hostFamilyService.findByPhoneNumber(phoneNumber);
    }

    @PostMapping("/host")
    public HostFamily post(@RequestBody HostFamily hostFamily, HttpServletResponse response) {
        //Cherche une famille dans la BD avec le numéro de téléphone saisi
        HostFamily familyDB = hostFamilyService.findByPhoneNumber(hostFamily.getPhoneNumber());

        //Si on trouve une famille : le numéro de téléphone est déjà utilisé
        if (familyDB != null) {
            response.setStatus(403);
            return new HostFamily();
        }

        hostFamilyService.saveOrUpdate(hostFamily);
        return hostFamily;
    }

    @PutMapping("/host")
    public HostFamily update(@RequestBody HostFamily hostFamily, HttpServletResponse response) {
        //Cherche une famille dans la BD avec le numéro de téléphone saisi
        HostFamily familyDB = hostFamilyService.findByPhoneNumber(hostFamily.getPhoneNumber());

        try {
            //Si on un trouve une famille et que son ID est différent de celui de la famille du formulaire
            if (familyDB != null && familyDB.getId() != hostFamily.getId()) {
                response.setStatus(403);
                return new HostFamily();
            }

            //Sinon on met à jour la famille
            hostFamilyService.saveOrUpdate(hostFamily);
            return hostFamily;

            //Conflit entre la famille de la base de donnée (familyDB) et la famille du formulaire: même ID mais sont différents.
        } catch (Exception exception) {
            familyDB = null;
            hostFamilyService.saveOrUpdate(hostFamily);
            return hostFamily;
        }
    }

    @DeleteMapping("/host/{id}")
    public String deleteById(@PathVariable int id, HttpServletResponse response) {
        //Récupère la liste de tous les animaux accueillis par cette famille
        List<Animal> animals = hostFamilyService.findAnimalsByHostFamily(id);

        Map<String, String> result = new HashMap<String, String>();

        if(animals.size() == 0){
            hostFamilyService.deleteById(id);
            result.put("message", "family has been deleted");
            result.put("id", String.valueOf(id));
        }else {
            response.setStatus(403);
            result.put("message", "Can't delete family with animals");
        }
        result.put("status", String.valueOf(response.getStatus()));
        return JSONObject.toJSONString(result);
    }
}