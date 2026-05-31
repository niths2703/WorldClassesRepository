package com.example.WorldClassesBooking.Exceptions;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(){

    }
    public InvalidUserException(String message){
       super(message);
    }
}
