package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.AdoptAnimal;

import java.util.List;

public interface AdoptAnimalDAO {

    AdoptAnimal findByAnimalId(int id);
    List<AdoptAnimal> findByAdoptiveFamilyId(int id);
    List<AdoptAnimal> findAll();
    void saveOrUpdate(AdoptAnimal adoptAnimal);
    void deleteByAnimalId(int id);
}
