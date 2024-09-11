package com.superherobackend.superhero.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.superherobackend.superhero.models.Superhero;
import com.superherobackend.superhero.repositories.UserSuperheroRepository;

@RestController
public class UserSuperheroController {

    @Autowired
    private UserSuperheroRepository userSuperheroRepository;


    @GetMapping("/users/{userId}/superheroes")
    public ResponseEntity<List<Superhero>> getUserSuperheroes(@PathVariable Long userId) {
        List<Superhero> superheroes = userSuperheroRepository.findAllSuperheroesByUserId(userId);
        return ResponseEntity.ok(superheroes);
    }

    @GetMapping("/superheroes/{superId}")
    public ResponseEntity<Superhero> getSuperheroById(@PathVariable Long superId) {
        Optional<Superhero> superhero = userSuperheroRepository.findSuperheroById(superId);
        return superhero.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{userId}/superheroes/universe")
    public ResponseEntity<List<Superhero>> getUserSuperheroesByUniverse(@PathVariable Long userId, @RequestParam String universe) {
        List<Superhero> superheroes = userSuperheroRepository.findSuperheroesByUserIdAndUniverse(userId, universe);
        return ResponseEntity.ok(superheroes);
    }

    
    @GetMapping("/users/{userId}/superheroes/power")
    public ResponseEntity<List<Superhero>> getUserSuperheroesByPower(@PathVariable Long userId, @RequestParam Long powerId) {
        List<Superhero> superheroes = userSuperheroRepository.findSuperheroesByUserIdAndPower(userId, powerId);
        return ResponseEntity.ok(superheroes);
    }
}
