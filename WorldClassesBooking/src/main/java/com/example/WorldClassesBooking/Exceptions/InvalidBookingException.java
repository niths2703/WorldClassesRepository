package com.example.WorldClassesBooking.Exceptions;

public class InvalidBookingException extends RuntimeException {
    public InvalidBookingException(){
    }

    public InvalidBookingException(String message){
        super(message);
    }
}
