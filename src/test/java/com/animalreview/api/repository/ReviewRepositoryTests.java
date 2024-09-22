package com.animalreview.api.repository;

import com.animalreview.api.entity.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnSavedReview(){
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        Review savedReview = reviewRepository.save(review);

        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_GetAllAnimal_ReturnMoreThanOneReview(){
        Review review1 = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        Review review2 = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> reviewList = reviewRepository.findAll();
        Assertions.assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    public void ReviewRepository_FindById_ReturnReview(){
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        reviewRepository.save(review);

        Review reviewReturn = reviewRepository.findById(review.getId()).get();
        Assertions.assertThat(reviewReturn).isNotNull();
    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnReview(){
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        reviewRepository.save(review);

        Review reviewReturn = reviewRepository.findById(review.getId()).get();
        reviewReturn.setTitle("title");
        reviewReturn.setContent("content");
        reviewReturn.setStars(5);

        Review updateReview = reviewRepository.save(reviewReturn);
        Assertions.assertThat(updateReview.getTitle()).isNotNull();
        Assertions.assertThat(updateReview.getContent()).isNotNull();
    }

    @Test
    public void AnimalRepository_DeleteReview_ReturnReviewIsEmpty(){
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();

        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewReturn = reviewRepository.findById(review.getId());
        Assertions.assertThat(reviewReturn).isEmpty();
    }
}
