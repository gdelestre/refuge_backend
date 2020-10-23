package fr.springboot.refuge.services;

import fr.springboot.refuge.entity.AdoptAnimal;
import java.util.List;

public interface AdoptAnimalService {
    List<AdoptAnimal> findByAdoptiveFamilyId(int id);
    List<AdoptAnimal> findAll();
    AdoptAnimal findByAnimalId(int id);
    void saveOrUpdate(AdoptAnimal adoptAnimal);
    void deleteByAnimalId(int id);
}
