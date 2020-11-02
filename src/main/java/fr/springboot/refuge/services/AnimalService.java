package fr.springboot.refuge.services;

import fr.springboot.refuge.entity.Animal;

import java.util.List;

public interface AnimalService {
    List<Animal> findAll();
    List<Animal> findWithHostFamily();
    List<Animal> findAdopted();
    List<Animal> findBySpecies(String species);
    Animal findById(int id);
    Animal findByName(String name);
    void saveOrUpdate(Animal animal);
    void deleteById(int id);
}
