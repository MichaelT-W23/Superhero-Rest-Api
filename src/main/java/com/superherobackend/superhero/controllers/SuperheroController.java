package com.superherobackend.superhero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.superherobackend.superhero.models.Superhero;
import com.superherobackend.superhero.services.SuperheroService;

@RestController
@RequestMapping("/superheroes")
public class SuperheroController {

    @Autowired
    private SuperheroService superheroService;

    @PostMapping
    public ResponseEntity<Superhero> addNewSuperhero(@RequestParam String name, @RequestParam String realName,
                                                     @RequestParam String universe, @RequestParam int yearCreated) {

        Superhero superhero = superheroService.addNewSuperhero(name, realName, universe, yearCreated);
        return ResponseEntity.ok(superhero);
    }



}
