package com.superherobackend.superhero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.superherobackend.superhero.dto.SuperheroDTO;
import com.superherobackend.superhero.models.Power;
import com.superherobackend.superhero.models.Superhero;
import com.superherobackend.superhero.models.User;
import com.superherobackend.superhero.models.UserSuperhero;
import com.superherobackend.superhero.repositories.PowerRepository;
import com.superherobackend.superhero.repositories.SuperheroRepository;
import com.superherobackend.superhero.repositories.UserRepository;
import com.superherobackend.superhero.repositories.UserSuperheroRepository;
import com.superherobackend.superhero.exceptions.DuplicateSuperheroException;

import java.util.HashSet;
import java.util.Set;

@Service
public class SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    @Autowired
    private PowerRepository powerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSuperheroRepository userSuperheroRepository;

    public Superhero addNewSuperhero(SuperheroDTO superheroDTO, Long userId) throws DuplicateSuperheroException {
        Superhero existingSuperhero = superheroRepository.findByName(superheroDTO.getName());

        // Check if the superhero already exists for the user
        if (existingSuperhero != null && userSuperheroRepository.existsByUserIdAndSuperheroId(userId, existingSuperhero.getSuperId())) {
            throw new DuplicateSuperheroException("Superhero already exists for this user!");
        }

        // Create the new superhero if not found
        Superhero superhero = existingSuperhero != null ? existingSuperhero : new Superhero();
        superhero.setName(superheroDTO.getName());
        superhero.setRealName(superheroDTO.getRealName());
        superhero.setUniverse(superheroDTO.getUniverse());
        superhero.setYearCreated(superheroDTO.getYearCreated());

        // Fetch and assign powers
        Set<Power> powers = new HashSet<>();
        powerRepository.findAllById(superheroDTO.getPowerIds()).forEach(powers::add);
        superhero.setPowers(powers);

        Superhero savedSuperhero = superheroRepository.save(superhero);

        // Associate the superhero with the user
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        UserSuperhero userSuperhero = new UserSuperhero();
        userSuperhero.setUser(user);
        userSuperhero.setSuperhero(savedSuperhero);
        userSuperheroRepository.save(userSuperhero);

        return savedSuperhero;
    }
}
