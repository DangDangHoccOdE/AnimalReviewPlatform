package com.animalreview.api.controller;

import com.animalreview.api.dto.AnimalDto;
import com.animalreview.api.dto.AnimalResponse;
import com.animalreview.api.dto.ReviewDto;
import com.animalreview.api.entity.Animal;
import com.animalreview.api.entity.Review;
import com.animalreview.api.servies.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = AnimalController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AnimalControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void AnimalController_CreateAnimal_ReturnCreated() throws Exception{
        given(animalService.createAnimal(ArgumentMatchers.any())).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        ResultActions resultActions = mockMvc.perform(post("/api/animal/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(animalDto)));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(animal.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(animal.getType())));
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void AnimalController_GetAllAnimal_returnResponseDto() throws Exception{
        AnimalResponse responseDto = AnimalResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(animalDto)).build();
        when(animalService.getAllAnimal(1,10)).thenReturn(responseDto);

        ResultActions response =  mockMvc.perform(get("/api/animal")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize","10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()",CoreMatchers.is(responseDto.getContent().size())));

    }

    @Test
    public void AnimalController_AnimalDetail_ReturnAnimalDto() throws Exception{
        int animalId = 1;
        when(animalService.getAnimalById(animalId)).thenReturn(animalDto);

        ResultActions resultActions = mockMvc.perform(get("/api/animal/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(animalDto)));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(animal.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(animal.getType())));
    }

    @Test
    public void AnimalController_UpdateAnimal_ReturnAnimalDto() throws Exception{
        int animalId = 1;
        when(animalService.updateAnimal(animalDto,animalId)).thenReturn(animalDto);

        ResultActions resultActions = mockMvc.perform(put("/api/animal/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(animalDto)));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(animal.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(animal.getType())));
    }

    @Test
    public void AnimalController_DeleteAnimal_ReturnAnimalDto() throws Exception{
        int animalId = 1;
        doNothing().when(animalService).deleteAnimalId(animalId);

        ResultActions resultActions = mockMvc.perform(delete("/api/animal/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
