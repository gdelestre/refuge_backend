package fr.springboot.refuge.entity;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="adopt_animal")
public class AdoptAnimal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne()
    @JoinColumn(name = "id_animal")
    private Animal adoptedAnimal;


    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_adoptiveFamily")
    private  AdoptiveFamily adoptiveFamily;

    @Column(name = "adoption_date")
    private LocalDate adoptionDate;

    public AdoptAnimal(Animal adoptedAnimal, AdoptiveFamily adoptiveFamily, LocalDate adoptionDate) {
        this.adoptedAnimal = adoptedAnimal;
        this.adoptiveFamily = adoptiveFamily;
        this.adoptionDate = adoptionDate;
    }

    public AdoptAnimal() {
    }

    public int getId() {
        return id;
    }

    public void setAdoptedAnimal(Animal adoptedAnimal) {
        this.adoptedAnimal = adoptedAnimal;
    }

    public AdoptiveFamily getAdoptiveFamily() {
        return adoptiveFamily;
    }

    public void setAdoptiveFamily(AdoptiveFamily adoptiveFamily) {
        this.adoptiveFamily = adoptiveFamily;
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }

    public void setAdoptionDate(LocalDate adoptionDate) {
        this.adoptionDate = adoptionDate;
    }
}
