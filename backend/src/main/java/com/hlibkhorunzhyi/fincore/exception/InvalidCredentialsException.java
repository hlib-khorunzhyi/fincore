package com.hlibkhorunzhyi.fincore.exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(){
        super("Invalid email or password");
    }
}
