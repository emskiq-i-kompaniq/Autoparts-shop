package com.sofiaexport.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserOrder {

    @Id
    @UuidGenerator
    private String id;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column
    private Double sum;

    @Column
    private Long timestamp;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_auto_parts",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "auto_part_id")
    )
    private Set<AutoPart> autoPartsInOrder;

    public UserOrder(OrderStatus status, Double sum, Long timestamp) {
        this.status = status;
        this.sum = sum;
        this.timestamp = timestamp;
        this.user = null;
        this.autoPartsInOrder = Collections.emptySet();
    }

    public void addAutoPart(AutoPart autoPart) {
        setSum(sum + autoPart.getPrice());
        autoPartsInOrder.add(autoPart);
    }

    public void removeAutoPart(AutoPart autoPartToRemove) {
        if (autoPartsInOrder.contains(autoPartToRemove)) {
            setSum(getSum() - autoPartToRemove.getPrice());
            autoPartsInOrder.remove(autoPartToRemove);
            // Optionally, update any other fields related to the order as needed
        } else {
            // Handle case where the AutoPart is not in the order
            return;
        }
    }
}
