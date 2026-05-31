package com.example.WorldClassesBooking.Exceptions;

public class InvalidUserRoleException extends RuntimeException{
    public InvalidUserRoleException(){
    }

    public InvalidUserRoleException(String message){
        super(message);
    }

    public InvalidUserRoleException(String message, Throwable cause) {
        super(message, cause);
    }

}
