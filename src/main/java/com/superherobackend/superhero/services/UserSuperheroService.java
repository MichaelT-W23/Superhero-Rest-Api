package com.superherobackend.superhero.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superherobackend.superhero.models.Superhero;
import com.superherobackend.superhero.models.User;
import com.superherobackend.superhero.models.UserSuperhero;
import com.superherobackend.superhero.repositories.SuperheroRepository;
import com.superherobackend.superhero.repositories.UserRepository;
import com.superherobackend.superhero.repositories.UserSuperheroRepository;

@Service
public class UserSuperheroService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SuperheroRepository superheroRepository;

    @Autowired
    private UserSuperheroRepository userSuperheroRepository;

    
    public List<Superhero> getAllSuperheroesByUserId(Long userId) {
        return userSuperheroRepository.findAllSuperheroesByUserId(userId);
    }

    public Optional<Superhero> getSuperheroById(Long superId) {
        return userSuperheroRepository.findSuperheroById(superId);
    }

    public void addSuperheroToUser(Long userId, Long superheroId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Superhero superhero = superheroRepository.findById(superheroId)
            .orElseThrow(() -> new RuntimeException("Superhero not found with id: " + superheroId));

        if (userSuperheroRepository.existsByUserUserIdAndSuperheroSuperId(userId, superheroId)) {
            throw new RuntimeException("Superhero already associated with that user");
        }

        UserSuperhero userSuperhero = new UserSuperhero(user, superhero);
        userSuperheroRepository.save(userSuperhero);
    }

    @Transactional
    public void deleteSuperheroFromUser(Long userId, Long superheroId) {
        boolean exists = userSuperheroRepository.existsByUserUserIdAndSuperheroSuperId(userId, superheroId);

        if (!exists) {
            throw new RuntimeException("Association between user and superhero not found");
        }

        userSuperheroRepository.deleteByUserUserIdAndSuperheroSuperId(userId, superheroId);
    }

    public boolean isUserSuperhero(Long userId, Long superheroId) {
        return userSuperheroRepository.existsByUserUserIdAndSuperheroSuperId(userId, superheroId);
    }
}