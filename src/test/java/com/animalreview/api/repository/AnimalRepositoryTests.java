package com.animalreview.api.repository;

import com.animalreview.api.entity.Animal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AnimalRepositoryTests {
    @Autowired
    private AnimalRepository animalRepository;

    @Test
    public void AnimalRepository_SaveAll_ReturnSavedAnimal(){
        Animal animal = Animal.builder()
                .name("lucas")
                .type("dog")
                .build();

        Animal savedAnimal = animalRepository.save(animal);

        Assertions.assertThat(savedAnimal).isNotNull();
        //Khẳng định rằng đối tượng đã lưu animal không phải là null, nghĩa là thao tác lưu đã thành công.
        Assertions.assertThat(savedAnimal.getId()).isGreaterThan(0);
        // Điều này đảm bảo đối tượng animal đã được cơ sở dữ liệu gán một ID dương, xác nhận rằng nó đã được tồn tại.
    }

    @Test
    public void AnimalRepository_GetAll_ReturnMoreThanOneAnimal(){
        Animal animal1 = Animal.builder()
                .name("lucas")
                .type("dog")
                .build();

        Animal animal2 = Animal.builder()
                .name("jerry")
                .type("cat")
                .build();

        animalRepository.save(animal1);
        animalRepository.save(animal2);

        List<Animal> animalList = animalRepository.findAll();
        Assertions.assertThat(animalList).isNotNull();
        Assertions.assertThat(animalList.size()).isEqualTo(2);
    }

    @Test
    public void AnimalRepository_FindById_ReturnAnimal(){
        Animal animal = Animal.builder().name("mery").type("dog").build();

        animalRepository.save(animal);

        Animal animal1 = animalRepository.findById(animal.getId()).get();
        Assertions.assertThat(animal1).isNotNull();
    }

    @Test
    public void AnimalRepository_FindByType_ReturnAnimalNotNull(){
        Animal animal = Animal.builder().name("mery").type("dog").build();

        animalRepository.save(animal);

        Animal animal1 = animalRepository.findByType(animal.getType()).get();
        Assertions.assertThat(animal1).isNotNull();
    }

    @Test
    public void AnimalRepository_UpdateAnimal_ReturnAnimalNotNull(){
        Animal animal = Animal.builder().name("mery").type("dog").build();

        animalRepository.save(animal);

        Animal animalSave = animalRepository.findById(animal.getId()).get();
        animalSave.setType("Snake");
        animalSave.setType("Lucy");

        Animal updateAnimal = animalRepository.save(animalSave);
        Assertions.assertThat(updateAnimal).isNotNull();
    }

    @Test
    public void AnimalRepository_DeleteAnimal_ReturnAnimalIsEmpty(){
        Animal animal = Animal.builder().name("mery").type("dog").build();

        animalRepository.save(animal);

        animalRepository.deleteById(animal.getId());
        Optional<Animal> animalReturn = animalRepository.findById(animal.getId());
        Assertions.assertThat(animalReturn).isEmpty();
    }
}
