package com.example.WorldClassesBooking.Exceptions;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidSessionException extends RuntimeException{

    public InvalidSessionException() {

    }
    public InvalidSessionException(String message){
        super(message);
    }
}
