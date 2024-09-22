package com.animalreview.api.exception;

public class AnimalNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1;

    public AnimalNotFoundException(String messgae){
        super(messgae);
    }
}
