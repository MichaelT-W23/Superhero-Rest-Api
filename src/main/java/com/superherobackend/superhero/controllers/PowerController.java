package com.superherobackend.superhero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.superherobackend.superhero.models.Power;
import com.superherobackend.superhero.repositories.PowerRepository;

import java.util.List;

public class PowerController {

    @Autowired
    private PowerRepository powerRepository;
    
    @GetMapping("/powers")
    public ResponseEntity<List<Power>> getAllPowers() {
        List<Power> powers = powerRepository.findAll();
        return ResponseEntity.ok(powers);
    }
}
