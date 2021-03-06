package fr.springboot.refuge.services;
import fr.springboot.refuge.entity.VeterinaryCare;
import java.util.List;

public interface VeterinaryCareService {
    List<VeterinaryCare> findAllByAnimalId(int id);
    List<VeterinaryCare> findAllByVeterinaryId(int id);
    List<VeterinaryCare> findAll();
    List<VeterinaryCare> findCaresToDo();
    VeterinaryCare findById(int id);
    void saveOrUpdate(VeterinaryCare veterinaryCare);
    void deleteById(int id);
    void deleteAllCaresByAnimalId(int id);

}
