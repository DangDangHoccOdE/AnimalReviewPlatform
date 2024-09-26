package com.animalreview.api.servies.impl;

import com.animalreview.api.dto.ReviewDto;
import com.animalreview.api.entity.Animal;
import com.animalreview.api.entity.Review;
import com.animalreview.api.entity.User;
import com.animalreview.api.exception.AnimalNotFoundException;
import com.animalreview.api.exception.ReviewNotFoundException;
import com.animalreview.api.exception.UserNotFoundException;
import com.animalreview.api.repository.AnimalRepository;
import com.animalreview.api.repository.ReviewRepository;
import com.animalreview.api.repository.UserRepository;
import com.animalreview.api.servies.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, AnimalRepository animalRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewDto createReview(int userId,int animalId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);

        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new AnimalNotFoundException("Animal with associated review not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated review not found"));

        review.setUser(user);
        review.setAnimal(animal);

        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewByAnimalId(int id) {
        List<Review> reviews = reviewRepository.findByAnimalId(id);

        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int animalId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new AnimalNotFoundException("Animal with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate animal not found"));

        if(review.getAnimal().getId() != animal.getId()) {
            throw new ReviewNotFoundException("This review does not belond to a animal");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int userId,int animalId, int reviewId, ReviewDto reviewDto) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new AnimalNotFoundException("Animal with associated review not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate animal not found"));

        if(review.getAnimal().getId() != animal.getId() || review.getUser().getId() != user.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a animal");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updateReview = reviewRepository.save(review);

        return mapToDto(updateReview);
    }

    @Override
    public void deleteReview(int userId,int animalId, int reviewId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new AnimalNotFoundException("Animal with associated review not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate animal not found"));

        if(review.getAnimal().getId() != animal.getId() || review.getUser().getId() != user.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a animal");
        }

        reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
