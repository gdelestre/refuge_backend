package fr.springboot.refuge.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "adoptive_family")
public class AdoptiveFamily extends Person {

    @Column(name = "adoption_date")
    private LocalDate adoptionDate;

    @OneToMany(mappedBy = "adoptiveFamily",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})

    List<Animal> animals;

    public AdoptiveFamily(String firstName, String lastName, int zipCode, String city, String streetName, int streetNumber, String phoneNumber, LocalDate adoptionDate) {
        super(firstName, lastName, zipCode, city, streetName, streetNumber, phoneNumber);
        this.adoptionDate = adoptionDate;
    }

    public AdoptiveFamily() {
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }

    public void setAdoptionDate(LocalDate adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
}
