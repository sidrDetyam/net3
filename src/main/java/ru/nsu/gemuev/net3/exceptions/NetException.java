package ru.nsu.gemuev.net3.exceptions;

import java.io.IOException;

public class NetException extends IOException {
    public NetException(String message){
        super(message);
    }
}
