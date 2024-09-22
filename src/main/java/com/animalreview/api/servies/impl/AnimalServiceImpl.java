package com.animalreview.api.servies.impl;

import com.animalreview.api.servies.AnimalService;
import com.animalreview.api.dto.AnimalDto;
import com.animalreview.api.dto.AnimalResponse;
import com.animalreview.api.entity.Animal;
import com.animalreview.api.exception.AnimalNotFoundException;
import com.animalreview.api.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public AnimalDto createAnimal(AnimalDto animalDto) {
        Animal animal = new Animal();
        animal.setName(animalDto.getName());
        animal.setType(animal.getType());
        Animal newAnimal = animalRepository.save(animal);

        AnimalDto animalResponse = new AnimalDto();
        animalResponse.setId(newAnimal.getId());
        animalResponse.setName(newAnimal.getName());
        animalResponse.setType(newAnimal.getType());
        return animalResponse;
    }

    @Override
    public AnimalResponse getAllAnimal(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Animal> animals = animalRepository.findAll(pageable);
        List<Animal> listOfAnimal = animals.getContent();
        List<AnimalDto> content = listOfAnimal.stream().map(
                animal -> mapToDto(animal)).collect(Collectors.toList());

        AnimalResponse animalResponse = new AnimalResponse();
        animalResponse.setContent(content);
        animalResponse.setPageNo(animals.getNumber());
        animalResponse.setPageSize(animals.getSize());
        animalResponse.setTotalElements(animals.getTotalElements());
        animalResponse.setTotalPages(animals.getTotalPages());
        animalResponse.setLast(animals.isLast());

        return animalResponse;
    }

    @Override
    public AnimalDto getAnimalById(int id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(()->new AnimalNotFoundException("Animal could not be found"));
        return mapToDto(animal);
    }

    @Override
    public AnimalDto updateAnimal(AnimalDto animalDto, int id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(()->new AnimalNotFoundException("Animal could not be update!"));
        animal.setName(animalDto.getName());
        animal.setType(animalDto.getType());

        Animal updateAnimal = animalRepository.save(animal);
        return mapToDto(updateAnimal);
    }

    @Override
    public void deleteAnimalId(int id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException("Animal could not be delete"));
        animalRepository.delete(animal);
    }

    private AnimalDto mapToDto(Animal animal){
        AnimalDto animalDto = new AnimalDto();
        animalDto.setId(animal.getId());
        animalDto.setName(animal.getName());
        animalDto.setType(animal.getType());

        return animalDto;
    }

    private Animal mapToEntity(AnimalDto animalDto){
        Animal animal = new Animal();
        animal.setName(animal.getName());
        animal.setType(animal.getType());

        return animal;
    }
}
