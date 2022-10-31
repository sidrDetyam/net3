package ru.nsu.gemuev.net3.exceptions;

public class PropertyNotFoundException extends RuntimeException{
    public PropertyNotFoundException(String message){
        super(message);
    }
}
