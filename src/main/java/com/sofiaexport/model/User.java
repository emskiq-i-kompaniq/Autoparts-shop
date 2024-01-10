package com.sofiaexport.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Collections;
import java.util.Set;

@Setter
@Getter
@Table
@Entity
@NoArgsConstructor
public class User {

    @Id
    @UuidGenerator
    private String id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserOrder> orders;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.orders = Collections.emptySet();
    }
}
