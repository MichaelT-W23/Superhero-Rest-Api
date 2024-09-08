package com.superherobackend.superhero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superherobackend.superhero.models.Superhero;
import com.superherobackend.superhero.repositories.SuperheroRepository;

@Service
public class SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    public Superhero addNewSuperhero(String name, String realName, String universe, int yearCreated) {
        Superhero existingSuperhero = superheroRepository.findByName(name);

        if (existingSuperhero != null) {
            throw new RuntimeException("Superhero with name '" + name + "' already exists.");
        }

        Superhero superhero = new Superhero();
        superhero.setName(name);
        superhero.setRealName(realName);
        superhero.setUniverse(universe);
        superhero.setYearCreated(yearCreated);

        return superheroRepository.save(superhero);
    }
}
