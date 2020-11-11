package fr.springboot.refuge.services;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.HostFamily;

import java.util.List;

public interface HostFamilyService {
    List<HostFamily> findAll();
    List<HostFamily> findFreeFamilies();
    HostFamily findByPhoneNumber(String phoneNumber);
    List<Animal> findAnimalsByHostFamily(int id);
    HostFamily findById(int id);
    void saveOrUpdate(HostFamily hostFamily);
    void deleteById(int id);
}
