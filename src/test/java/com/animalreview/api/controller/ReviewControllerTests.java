package com.animalreview.api.controller;

import com.animalreview.api.dto.AnimalDto;
import com.animalreview.api.entity.Animal;
import com.animalreview.api.entity.Review;
import com.animalreview.api.entity.User;
import com.animalreview.api.servies.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.animalreview.api.dto.ReviewDto;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;
    private Animal animal;
    private Review review;
    private ReviewDto reviewDto;
    private AnimalDto animalDto;

    @BeforeEach
    public void init(){
        user = User.builder().username("username").password("password").build();
        animal = Animal.builder().name("mery").type("dog").build();
        animalDto = AnimalDto.builder().name("mery").type("dog").build();
        review = Review.builder()
                .title("title")
                .content("content")
                .stars(5).build();
        reviewDto = ReviewDto.builder().title("review title").content("test content").stars(5).build();
    }

    @Test
    public void ReviewController_GetReviewsByAnimalId_ReturnReviewDto() throws Exception {
        int animalId = 1;
        when(reviewService.getReviewByAnimalId(animalId)).thenReturn(Arrays.asList(reviewDto));

        ResultActions response = mockMvc.perform(get("/api/animal/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(animalDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(reviewDto).size())));
    }

    @Test
    public void ReviewController_UpdateReview_ReturnReviewDto() throws Exception {
        int animalId = 1;
        int reviewId = 1;
        int userId = 1;
        when(reviewService.updateReview(userId,animalId, reviewId, reviewDto)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(put("/api/users/1/animal/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }


    @Test
    public void ReviewController_CreateReview_ReturnReviewDto() throws Exception {
        int animalId = 1;
        int userId = 1;
        when(reviewService.createReview(userId,animalId, reviewDto)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(post("/api/users/1/animal/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    public void ReviewController_GetReviewId_ReturnReviewDto() throws Exception {
        int animalId = 1;
        int reviewId = 1;
        when(reviewService.getReviewById(reviewId, animalId)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(get("/api/animal/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    public void ReviewController_DeleteReview_ReturnOk() throws Exception {
        int animalId = 1;
        int reviewId = 1;
        int userId = 1 ;

        doNothing().when(reviewService).deleteReview(userId,animalId, reviewId);

        ResultActions response = mockMvc.perform(delete("/api/users/1/animal/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }




}