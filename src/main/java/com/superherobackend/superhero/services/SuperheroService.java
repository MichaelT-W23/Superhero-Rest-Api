package com.superherobackend.superhero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.superherobackend.superhero.dto.SuperheroDTO;
import com.superherobackend.superhero.exceptions.DuplicateSuperheroException;
import com.superherobackend.superhero.models.Image;
import com.superherobackend.superhero.models.Power;
import com.superherobackend.superhero.models.Superhero;
import com.superherobackend.superhero.models.User;
import com.superherobackend.superhero.models.UserSuperhero;
import com.superherobackend.superhero.repositories.ImageRepository;
import com.superherobackend.superhero.repositories.PowerRepository;
import com.superherobackend.superhero.repositories.SuperheroRepository;
import com.superherobackend.superhero.repositories.UserRepository;
import com.superherobackend.superhero.repositories.UserSuperheroRepository;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

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

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private S3ImageService s3ImageService;
    
    public List<Superhero> getAllSuperheroes() {
        return superheroRepository.findAllSuperheroes();
    }
    
    @Transactional 
    public Superhero addNewSuperhero(SuperheroDTO superheroDTO, Long userId, MultipartFile image) throws Exception {

        Superhero existingSuperhero = superheroRepository.findByNameAndUserId(superheroDTO.getName(), userId);

        if (existingSuperhero != null) {
            throw new DuplicateSuperheroException("Superhero already exists for this user!");
        }

        // Create the new superhero if not found
        Superhero superhero = new Superhero();
        superhero.setName(superheroDTO.getName());
        superhero.setRealName(superheroDTO.getRealName());
        superhero.setUniverse(superheroDTO.getUniverse());
        superhero.setYearCreated(superheroDTO.getYearCreated());

        // Fetch and set powers
        Set<Power> powers = new HashSet<>();
        powerRepository.findAllById(superheroDTO.getPowerIds()).forEach(powers::add);
        superhero.setPowers(powers);

        // Save the new superhero 
        Superhero savedSuperhero = superheroRepository.save(superhero);

        // Upload Image
        String storedFilename = s3ImageService.uploadImage(savedSuperhero.getSuperId(), image); // Upload image to S3 and get stored filename

        // Check if an image already exists for the superhero
        Image existingImage = imageRepository.findBySuperhero(savedSuperhero);
        
        if (existingImage != null) {
            // Update the existing image
            existingImage.setOriginalFilename(image.getOriginalFilename());
            existingImage.setStoredFilename(storedFilename);
            imageRepository.save(existingImage);
        } else {
            // Save the new image metadata if no image exists
            Image newImage = new Image();
            newImage.setOriginalFilename(image.getOriginalFilename());
            newImage.setStoredFilename(storedFilename);
            newImage.setSuperhero(savedSuperhero);
            imageRepository.save(newImage);
            savedSuperhero.setImage(newImage);
        }

        // Associate the superhero with the user
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        UserSuperhero userSuperhero = new UserSuperhero();
        userSuperhero.setUser(user);
        userSuperhero.setSuperhero(savedSuperhero);
        userSuperheroRepository.save(userSuperhero);

        // Fetch and include the image in the superhero response
        Image imageToReturn = imageRepository.findBySuperhero(savedSuperhero);
        savedSuperhero.setImage(imageToReturn);

        return savedSuperhero;
    }


    @Transactional
    public void deleteSuperhero(Long superId) {

        // Delete the image from the S3 bucket
        String storedFilename = imageRepository.findStoredFilenameBySuperheroId(superId);

        if (storedFilename != null) {
            s3ImageService.deleteImage(storedFilename);
        }

        // Remove the image record from the Images table
        imageRepository.deleteBySuperheroSuperId(superId);

        // Remove all associations with the user (Many-to-Many relationship)
        userSuperheroRepository.deleteBySuperhero_SuperId(superId);

        // Remove the superhero and its powers (handled by ON DELETE CASCADE in DB)
        superheroRepository.deleteById(superId);
    }

    public Long getMaxSuperId() {
        return superheroRepository.findMaxSuperId();
    }
}
