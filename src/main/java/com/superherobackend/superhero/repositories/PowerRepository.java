package com.superherobackend.superhero.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.superherobackend.superhero.models.Power;

import java.util.List;

@Repository
public interface PowerRepository extends CrudRepository<Power, Long> {
    List<Power> findAll();
}
