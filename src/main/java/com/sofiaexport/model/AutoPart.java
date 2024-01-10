package com.sofiaexport.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Collections;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AutoPart {

    @Id
    @UuidGenerator
    private String id;

    @Column
    private String brand;

    @Column
    @Enumerated(EnumType.STRING)
    private PartType partType;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private String serialNumber;

    @Column
    private Integer countInStockItems;

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
        this.compatibleCars = Collections.emptySet();
        this.ordersWithPart = Collections.emptySet();
    }

    public void setCountInStockItems(Integer countInStockItems) {
        if (countInStockItems < 0) {
            throw new IllegalArgumentException("countInStockItems should be greater than or equal to 0");
        }
        this.countInStockItems = countInStockItems;
    }

    public void decreaseStockQuantity() {
        this.countInStockItems--;
    }
}