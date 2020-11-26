package fr.springboot.refuge.controller;

import fr.springboot.refuge.entity.Veterinary;
import fr.springboot.refuge.entity.VeterinaryCare;
import fr.springboot.refuge.services.VeterinaryCareService;
import fr.springboot.refuge.services.VeterinaryService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class VeterinaryController {

    @Autowired
    private VeterinaryService veterinaryService;

    @Autowired
    private VeterinaryCareService careService;

    @GetMapping("/veterinary")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public List<Veterinary> getAll() {
        return veterinaryService.findAll();
    }

    @GetMapping("/veterinary/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public Veterinary getById(@PathVariable int id) {
        return veterinaryService.findById(id);
    }

    @PostMapping("/veterinary")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Veterinary post(@RequestBody Veterinary veterinary, HttpServletResponse response) {
        veterinaryService.saveOrUpdate(veterinary);
        return veterinary;
    }

    @PutMapping("/veterinary")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Veterinary update(@RequestBody Veterinary veterinary) {
        veterinaryService.saveOrUpdate(veterinary);
        return veterinary;
    }

    @DeleteMapping("/veterinary/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteById(@PathVariable int id, HttpServletResponse response) {
        Map<String, String> result = new HashMap<String, String>();

        List<VeterinaryCare> cares = careService.findAllByVeterinaryId(id);

        Date now = new Date();

        if(cares.size() >0){
            if(cares.get(0).getExamenDate().after(now)) {
                response.setStatus(403);
                result.put("message", "You can't delete veterinary with care(s) to do");
                result.put("status", String.valueOf(response.getStatus()));
                return JSONObject.toJSONString(result);
            }

            for(VeterinaryCare care: cares){
                careService.deleteById(care.getId());
            }
        }

        veterinaryService.deleteById(id);
        result.put("message", "veterinary has been deleted");
        result.put("id", String.valueOf(id));
        result.put("status", String.valueOf(response.getStatus()));

        return JSONObject.toJSONString(result);

    }
}
