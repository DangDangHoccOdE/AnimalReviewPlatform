package com.animalreview.api.repository;

import com.animalreview.api.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AnimalRepository extends JpaRepository<Animal,Integer> {
    Optional<Animal> findByType(String type);
}
