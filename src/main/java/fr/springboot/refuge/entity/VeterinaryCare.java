package fr.springboot.refuge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="veterinary_care")
public class VeterinaryCare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String examen;

    @Column(name = "examen_date")
    @Temporal(TemporalType.DATE)
    private Date examenDate;

    @Column(name = "examen_time")
    private String examenTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "id_animal", nullable=false)
    private Animal animal;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "id_veterinary", nullable=false)
    @JsonIgnore
    private Veterinary veterinary;


    public VeterinaryCare() {
    }

    public VeterinaryCare(String examen, Date examenDate, String examenTime, Veterinary veterinary) {
        this.examen = examen;
        this.examenDate = examenDate;
        this.examenTime = examenTime;
        this.veterinary = veterinary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExamen() {
        return examen;
    }

    public void setExamen(String examen) {
        this.examen = examen;
    }

    public Date getExamenDate() {
        return examenDate;
    }

    public void setExamenDate(Date examenDate) {
        this.examenDate = examenDate;
    }

    public String getExamenTime() {
        return examenTime;
    }

    public void setExamenTime(String examenTime) {
        this.examenTime = examenTime;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Veterinary getVeterinary() {
        return veterinary;
    }

    public void setVeterinary(Veterinary veterinary) {
        this.veterinary = veterinary;
    }

    @Override
    public String toString() {
        return "VeterinaryCare{" +
                "examen='" + examen + '\'' +
                ", examenDate=" + examenDate +
                ", examenTime=" + examenTime +
                '}';
    }
}
