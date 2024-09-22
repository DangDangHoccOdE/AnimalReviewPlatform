package com.animalreview.api.service;

import com.animalreview.api.dto.AnimalDto;
import com.animalreview.api.dto.AnimalResponse;
import com.animalreview.api.entity.Animal;
import com.animalreview.api.repository.AnimalRepository;
import com.animalreview.api.servies.impl.AnimalServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTests {
    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalServiceImpl animalService;

    @Test
    public void AnimalService_CreateAnimal_ReturnAnimalDto(){
        // Tạo object Animal và AnimalDto để sử dụng trong test
        Animal animal = Animal.builder().name("mery").type("dog").build();

        AnimalDto animalDto = AnimalDto.builder().name("Lucas").type("dog").build();

        // Cấu hình Mockito để giả lập hành vi của phương thức save
        // Vì giả lập database nên cái animal mà trả về sẽ không tồn tại nếu không có câu lệnh when duới. Data khi save sẽ luôn trả về animal trên
        when(animalRepository.save(Mockito.any(Animal.class))).thenReturn(animal);

        // Gọi phương thức createAnimal từ service và kiểm tra kết quả
        AnimalDto saveAnimal = animalService.createAnimal(animalDto); // => saveAnimal = animal

        // Kiểm tra xem kết quả có khác null không
        Assertions.assertThat(saveAnimal).isNotNull();
    }

    @Test
    public void AnimalService_GetAllAnimal_ReturnResponseDto(){
        Page<Animal> animals = Mockito.mock(Page.class);

        when(animalRepository.findAll(Mockito.any(Pageable.class))).thenReturn(animals);

        AnimalResponse saveAnimal = animalService.getAllAnimal(1,10);

        Assertions.assertThat(saveAnimal).isNotNull();
    }
    @Test
    public void AnimalService_GetAnimalById_ReturnAnimalDto(){
        Animal animal = Animal.builder().name("mery").type("dog").build();

        when(animalRepository.findById(1)).thenReturn(Optional.ofNullable(animal));

        AnimalDto saveAnimal = animalService.getAnimalById(1);

        Assertions.assertThat(saveAnimal).isNotNull();
    }

    @Test
    public void AnimalService_UpdateAnimal_ReturnAnimalDto(){
        Animal animal = Animal.builder().name("mery").type("dog").build();

        AnimalDto animalDto = AnimalDto.builder().name("Lucas").type("dog").build();
        when(animalRepository.findById(1)).thenReturn(Optional.ofNullable(animal));
        when(animalRepository.save(Mockito.any(Animal.class))).thenReturn(animal);

        AnimalDto savedAnimal = animalService.updateAnimal(animalDto,1);

        Assertions.assertThat(savedAnimal).isNotNull();
    }

    @Test
    public void AnimalService_DeleteAnimalById_ReturnAnimalDto(){
        Animal animal = Animal.builder().name("mery").type("dog").build();

        when(animalRepository.findById(1)).thenReturn(Optional.ofNullable(animal));

        assertAll(()->animalService.deleteAnimalId(1));
    }
}
