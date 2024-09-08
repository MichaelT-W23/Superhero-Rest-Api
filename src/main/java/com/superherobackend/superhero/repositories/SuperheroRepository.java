package com.superherobackend.superhero.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.superherobackend.superhero.models.Superhero;

@Repository
public interface SuperheroRepository extends CrudRepository<Superhero, Long> {
    Superhero findByName(String name);
}
