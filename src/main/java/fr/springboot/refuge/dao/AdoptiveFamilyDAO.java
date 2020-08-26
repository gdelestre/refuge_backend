package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.AdoptiveFamily;

import java.util.List;

public interface AdoptiveFamilyDAO {
    List<AdoptiveFamily> findAll();
    AdoptiveFamily findById(int id);
    void saveOrUpdate(AdoptiveFamily adoptiveFamily);
    void deleteById(int id);
}
