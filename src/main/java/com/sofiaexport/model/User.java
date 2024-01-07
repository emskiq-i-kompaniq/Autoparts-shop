package com.sofiaexport.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

@Setter
@Getter
@Table
@Entity
public class User {

    @Id
    @GeneratedValue()
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_auto_parts",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "auto_part_id")
    )
    private Set<AutoPart> autoParts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserOrder> orders;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.autoParts = Collections.emptySet();
        this.orders = Collections.emptySet();
    }

    public User() {
        this.name = "";
        this.email = "";
        this.password = "";
        this.autoParts = Collections.emptySet();
        this.orders = Collections.emptySet();
    }
}
