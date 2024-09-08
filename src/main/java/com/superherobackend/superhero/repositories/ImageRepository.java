// package com.superherobackend.superhero.repositories;

// import org.springframework.data.repository.CrudRepository;
// import org.springframework.stereotype.Repository;

// import com.superherobackend.superhero.models.Image;

// @Repository
// public interface ImageRepository extends CrudRepository<Image, Long> {
//     Image findBySuperId(Long superId);
// }

package com.superherobackend.superhero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.superherobackend.superhero.models.Image;
import com.superherobackend.superhero.models.Superhero;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    
    Image findBySuperhero(Superhero superhero); // Query by the Superhero object

}


