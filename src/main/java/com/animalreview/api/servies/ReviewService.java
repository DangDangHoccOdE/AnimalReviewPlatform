package com.animalreview.api.servies;

import com.animalreview.api.dto.ReviewDto;
import java.util.*;
public interface ReviewService {
    ReviewDto createReview(int animalId, ReviewDto reviewDto);
    List<ReviewDto> getReviewByAnimalId(int animalId);
    ReviewDto getReviewById(int reviewId,int animalId);
    ReviewDto updateReview(int animalId, int reviewId, ReviewDto reviewDto);
    void deleteReview(int animalId,int reviewId);
}
