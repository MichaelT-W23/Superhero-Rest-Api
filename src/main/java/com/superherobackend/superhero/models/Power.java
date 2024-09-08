package com.superherobackend.superhero.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Powers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Power implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "power_id")
    private Long powerId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "powers", fetch = FetchType.LAZY)
    private Set<Superhero> superheroes;
}
