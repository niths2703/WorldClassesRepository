package com.example.WorldClassesBooking.Exceptions;

public class InvalidOfferingException extends RuntimeException{
    public InvalidOfferingException(){

    }

    public InvalidOfferingException(String message){
        super(message);
    }
}
