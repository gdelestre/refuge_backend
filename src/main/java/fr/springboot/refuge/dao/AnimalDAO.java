package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.Animal;

import java.util.List;

public interface AnimalDAO {
    List<Animal> findAll();
    List<Animal> findWithHostFamily();
    List<Animal> findAdopted();
    List<Animal> findBySpecies(String species);
    Animal findById(int id);
    void saveOrUpdate(Animal animal);
    void deleteById(int id);
}
