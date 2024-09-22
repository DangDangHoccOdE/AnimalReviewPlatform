package com.animalreview.api.repository;

import com.animalreview.api.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByAnimalId(int animalId);
}
