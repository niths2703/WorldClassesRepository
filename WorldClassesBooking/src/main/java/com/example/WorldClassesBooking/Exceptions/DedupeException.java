package com.example.WorldClassesBooking.Exceptions;

public class DedupeException extends RuntimeException{

    public DedupeException(){

    }
    public DedupeException(String message){
        super(message);
    }
}
