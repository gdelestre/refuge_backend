package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.HostFamily;
import java.util.List;

public interface HostFamilyDAO {
    List<HostFamily> findFreeFamilies();
    List<HostFamily> findFullFamilies();
    HostFamily findByPhoneNumber(String phoneNumber);
    List<Animal> findAnimalsByHostFamily(int id);
    HostFamily findById(int id);
    void saveOrUpdate(HostFamily hostFamily);
    void deleteById(int id);
}
