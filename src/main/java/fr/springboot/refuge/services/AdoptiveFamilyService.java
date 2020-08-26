package fr.springboot.refuge.services;

import fr.springboot.refuge.entity.AdoptiveFamily;

import java.util.List;

public interface AdoptiveFamilyService {
    List<AdoptiveFamily> findAll();
    AdoptiveFamily findById(int id);
    void saveOrUpdate(AdoptiveFamily adoptiveFamily);
    void deleteById(int id);
}