package com.superherobackend.superhero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.superherobackend.superhero.dto.SuperheroDTO;
import com.superherobackend.superhero.exceptions.DuplicateSuperheroException;
import com.superherobackend.superhero.models.Superhero;
import com.superherobackend.superhero.services.SuperheroService;

import java.util.List;

@RestController
@RequestMapping("/superheroes")
public class SuperheroController {

    @Autowired
    private SuperheroService superheroService;

    @PostMapping("/users/{userId}/superheroes/add")
    public ResponseEntity<?> addNewSuperhero(@PathVariable Long userId,
                                                    @RequestParam String name,
                                                    @RequestParam String realName,
                                                    @RequestParam String universe,
                                                    @RequestParam int yearCreated,
                                                    @RequestParam List<Long> powerIds,
                                                    @RequestParam MultipartFile image) {

        SuperheroDTO superheroDTO = new SuperheroDTO(name, realName, universe, yearCreated, powerIds, image);

        Superhero superhero = null;

        try {
            superhero = superheroService.addNewSuperhero(superheroDTO, userId);
            return ResponseEntity.ok(superhero);
        } catch (DuplicateSuperheroException e) {
            return ResponseEntity.status(409).body("Superhero already exists for this user.");
        }

    }
    
}

