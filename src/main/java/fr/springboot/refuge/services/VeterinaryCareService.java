package fr.springboot.refuge.services;
import fr.springboot.refuge.entity.VeterinaryCare;
import java.util.List;

public interface VeterinaryCareService {
    List<VeterinaryCare> findAllByAnimalId(int id);
    List<VeterinaryCare> findAll();
    void saveOrUpdate(VeterinaryCare veterinaryCare);
    void deleteById(int id);
    void deleteAllCaresByAnimalId(int id);

}
