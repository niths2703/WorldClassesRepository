package com.example.WorldClassesBooking.Exceptions;

public class ConflictException extends RuntimeException{
    public ConflictException(){

    }
    public ConflictException(String message){
        super(message);
    }
}
