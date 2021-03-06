package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.AdoptiveFamily;
import fr.springboot.refuge.entity.Animal;

import java.util.List;

public interface AdoptiveFamilyDAO {
    List<AdoptiveFamily> findAll();
    AdoptiveFamily findByPhoneNumber(String phoneNumber);
    List<Animal> findAnimalsByAdoptiveFamily (int id);
    AdoptiveFamily findById(int id);
    void saveOrUpdate(AdoptiveFamily adoptiveFamily);
    void deleteById(int id);
}
