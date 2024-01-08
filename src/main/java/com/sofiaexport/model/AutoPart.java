package com.sofiaexport.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class AutoPart {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String brand;

    @Column
    private PartType partType;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private String serialNumber;

    @Column
    private Integer countInStockItems;

    @ManyToMany(mappedBy = "autoParts")
    private Set<User> users;

    @ManyToMany(mappedBy = "compatibleParts")
    private Set<Car> compatibleCars;

    @ManyToMany(mappedBy = "autoPartsInOrder")
    private Set<UserOrder> ordersWithPart;

    public AutoPart(String brand, PartType partType, String description, Double price, String serialNumber, Integer countInStockItems) {
        this.brand = brand;
        this.partType = partType;
        this.description = description;
        this.price = price;
        this.serialNumber = serialNumber;
        this.countInStockItems = countInStockItems;
        this.users = Collections.emptySet();
        this.compatibleCars = Collections.emptySet();
        this.ordersWithPart = Collections.emptySet();
    }

    public void decreaseStockQuantity() {
        this.countInStockItems--;
    }
}