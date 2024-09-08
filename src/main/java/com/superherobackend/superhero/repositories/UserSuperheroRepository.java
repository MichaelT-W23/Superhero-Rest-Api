package com.superherobackend.superhero.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.superherobackend.superhero.models.Superhero;

@Repository
public interface UserSuperheroRepository extends CrudRepository<Superhero, Long> {

    @Query("SELECT s FROM Superhero s JOIN UserSuperheroes us ON s.superId = us.superId WHERE us.userId = :userId")
    List<Superhero> findAllSuperheroesByUserId(Long userId);

    @Query("SELECT s FROM Superhero s JOIN UserSuperheroes us ON s.superId = us.superId WHERE us.userId = :userId AND s.universe = :universe")
    List<Superhero> findSuperheroesByUserIdAndUniverse(Long userId, String universe);

}