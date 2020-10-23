package fr.springboot.refuge.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "adoptive_family")
public class AdoptiveFamily extends Person {


    @OneToMany(mappedBy = "adoptiveFamily",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<AdoptAnimal> adoptions;

    public AdoptiveFamily(String firstName, String lastName, int zipCode, String city, String streetName, int streetNumber, String phoneNumber, LocalDate adoptionDate) {
        super(firstName, lastName, zipCode, city, streetName, streetNumber, phoneNumber);
    }

    public AdoptiveFamily() {
    }

    public void setAdoptions(List<AdoptAnimal> adoptions) {
        this.adoptions = adoptions;
    }
}
