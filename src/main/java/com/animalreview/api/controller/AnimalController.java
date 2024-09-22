package com.animalreview.api.controller;

import com.animalreview.api.servies.AnimalService;
import com.animalreview.api.dto.AnimalDto;
import com.animalreview.api.dto.AnimalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnimalController {
    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/animal")
    public ResponseEntity<AnimalResponse> getAnimals(
            @RequestParam(value = "pageNo",defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize
    ){
        return new ResponseEntity<>(animalService.getAllAnimal(pageNo,pageSize), HttpStatus.OK);
    }

    @GetMapping("/animal/{id}")
    public ResponseEntity<AnimalDto> animalDetail(@PathVariable int id){
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @PostMapping("/animal/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AnimalDto> createAnimal(@RequestBody AnimalDto animalDto){
        return new ResponseEntity<>(animalService.createAnimal(animalDto),HttpStatus.CREATED);
    }


    @PutMapping("/animal/{id}/update")
    public ResponseEntity<AnimalDto> updateAnimal(@RequestBody AnimalDto animalDto, @PathVariable("id") int animalId) {
        AnimalDto response = animalService.updateAnimal(animalDto, animalId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/animal/{id}/delete")
    public ResponseEntity<String> deleteAnimal(@PathVariable("id") int animalId) {
        animalService.deleteAnimalId(animalId);
        return new ResponseEntity<>("Animal delete", HttpStatus.OK);
    }
}
