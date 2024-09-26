package com.animalreview.api.exception;

    public class UserNotFoundException extends RuntimeException{
        private static final long serialVersionUID = 1;

        public UserNotFoundException(String messgae){
            super(messgae);
        }
    }

