package com.superherobackend.superhero.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superherobackend.superhero.models.Superhero;
import com.superherobackend.superhero.repositories.UserSuperheroRepository;

@Service
public class UserSuperheroService {

    @Autowired
    private UserSuperheroRepository userSuperheroRepository;

    public List<Superhero> getAllSuperheroesByUserId(Long userId) {
        return userSuperheroRepository.findAllSuperheroesByUserId(userId);
    }
}