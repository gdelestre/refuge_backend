package fr.springboot.refuge.services;

import fr.springboot.refuge.entity.AdoptiveFamily;
import fr.springboot.refuge.entity.Animal;

import java.util.List;

public interface AdoptiveFamilyService {
    List<AdoptiveFamily> findAll();
    List<Animal> findAnimalsByAdoptiveFamily(int id);
    AdoptiveFamily findById(int id);
    void saveOrUpdate(AdoptiveFamily adoptiveFamily);
    void deleteById(int id);
}