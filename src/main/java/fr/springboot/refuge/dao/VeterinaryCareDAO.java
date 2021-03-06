package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.VeterinaryCare;

import java.util.List;

public interface VeterinaryCareDAO {
    List<VeterinaryCare> findAllByAnimalId(int id);
    List<VeterinaryCare> findAllByVeterinaryId(int id);
    List<VeterinaryCare> findAll();
    List<VeterinaryCare> findCaresToDo();
    VeterinaryCare findById(int id);
    void saveOrUpdate(VeterinaryCare veterinaryCare);
    void deleteById(int id);
    void deleteAllCaresByAnimalId(int id);
}
