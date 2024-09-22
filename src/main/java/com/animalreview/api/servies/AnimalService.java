package com.animalreview.api.servies;

import com.animalreview.api.dto.AnimalDto;
import com.animalreview.api.dto.AnimalResponse;

public interface AnimalService {
    AnimalDto createAnimal(AnimalDto animalDto);
    AnimalResponse getAllAnimal(int pageNo, int pageSize);
    AnimalDto getAnimalById(int id);
    AnimalDto updateAnimal(AnimalDto animalDto, int id);
    void deleteAnimalId(int id);
}
