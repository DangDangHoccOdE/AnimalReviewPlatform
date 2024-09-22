package com.animalreview.api.service;

import com.animalreview.api.dto.AnimalDto;
import com.animalreview.api.dto.ReviewDto;
import com.animalreview.api.entity.Animal;
import com.animalreview.api.entity.Review;
import com.animalreview.api.repository.AnimalRepository;
import com.animalreview.api.repository.ReviewRepository;
import com.animalreview.api.servies.impl.ReviewServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;

    private Animal animal;
    private Review review;
    private ReviewDto reviewDto;
    private AnimalDto animalDto;

    @BeforeEach
    public void init(){
        animal = Animal.builder().name("mery").type("dog").build();
        animalDto = AnimalDto.builder().name("mery").type("dog").build();
        review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();
        reviewDto = ReviewDto.builder().title("review title").content("test content").stars(5).build();
    }

    @Test
    public void ReviewService_CreateReview_ReturnReviewDto(){
        when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto savedReview = reviewServiceImpl.createReview(animal.getId(),reviewDto);

        Assertions.assertThat(savedReview).isNotNull();
    }

    @Test
    public void ReviewService_FindByAnimalId_ReturnReviewDto(){
        int animalId = 1;
        when(reviewRepository.findByAnimalId(animalId)).thenReturn(Arrays.asList(review));

        List<ReviewDto> reviewList = reviewServiceImpl.getReviewByAnimalId(animalId);
        Assertions.assertThat(reviewList).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewById_ReturnReviewDto(){
        int animalId = 1;
        int reviewId = 1;

        review.setAnimal(animal);
        when(animalRepository.findById(animalId)).thenReturn(Optional.of(animal));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        ReviewDto getReviewDto = reviewServiceImpl.getReviewById(reviewId,animalId);

        Assertions.assertThat(getReviewDto).isNotNull();
    }

    @Test
    public void ReviewService_UpdateReview_ReturnReviewDto(){
        int animalId = 1;
        int reviewId = 1;

        animal.setReviews(Arrays.asList(review));
        review.setAnimal(animal);
        when(animalRepository.findById(animalId)).thenReturn(Optional.of(animal));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(review)).thenReturn(review);

        ReviewDto updateReview = reviewServiceImpl.updateReview(animalId,reviewId,reviewDto);
        Assertions.assertThat(updateReview).isNotNull();
    }

    @Test
    public void ReviewService_DeleteReview_ReturnReviewDto(){
        int animalId = 1;
        int reviewId = 1;

        animal.setReviews(Arrays.asList(review));
        review.setAnimal(animal);
        when(animalRepository.findById(animalId)).thenReturn(Optional.of(animal));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        assertAll(()->reviewServiceImpl.deleteReview(animalId,reviewId));
    }
}
