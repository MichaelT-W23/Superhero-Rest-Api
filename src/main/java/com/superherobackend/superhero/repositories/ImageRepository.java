package com.superherobackend.superhero.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.superherobackend.superhero.models.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    Image findBySuperId(Long superId);
}

