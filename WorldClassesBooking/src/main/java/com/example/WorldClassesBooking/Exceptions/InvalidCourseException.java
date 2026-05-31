package com.example.WorldClassesBooking.Exceptions;

public class InvalidCourseException extends RuntimeException{
    public InvalidCourseException(){

    }

    public InvalidCourseException(String message){
        super(message);
    }
}
