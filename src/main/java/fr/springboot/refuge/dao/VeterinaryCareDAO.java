package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.VeterinaryCare;

import java.util.List;

public interface VeterinaryCareDAO {
    List<VeterinaryCare> findAllByAnimalId(int id);
    List<VeterinaryCare> findAll();
    void saveOrUpdate(VeterinaryCare veterinaryCare);
    void deleteById(int id);
    void deleteAllCaresByAnimalId(int id);
}
