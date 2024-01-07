package com.sofiaexport.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private String engine;

    @ManyToMany
    @JoinTable(
            name = "car_auto_parts",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "auto_part_id")
    )
    private Set<AutoPart> compatibleParts;

    public Car(String brand, String model, String engine) {
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.compatibleParts = Collections.emptySet();
    }

    public Car() {

    }
}
