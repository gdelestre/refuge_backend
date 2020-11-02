package fr.springboot.refuge.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "veterinary", uniqueConstraints={@UniqueConstraint(columnNames={"phone_number"})})
public class Veterinary extends Person{

    @OneToMany(mappedBy = "veterinary",
            cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    List<VeterinaryCare> veterinaryCares;

    public Veterinary(String firstName, String lastName, int zipCode, String city, String streetName, int streetNumber, String phoneNumber) {
        super(firstName, lastName, zipCode, city, streetName, streetNumber, phoneNumber);
    }

    public Veterinary() {
    }

    /*public List<VeterinaryCare> getVeterinaryCares() {
        return veterinaryCares;
    }

     */

    public void setVeterinaryCares(List<VeterinaryCare> veterinaryCares) {
        this.veterinaryCares = veterinaryCares;
    }
}
