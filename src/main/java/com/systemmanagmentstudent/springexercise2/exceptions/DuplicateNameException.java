package com.systemmanagmentstudent.springexercise2.exceptions;


public class DuplicateNameException extends RuntimeException {

    public DuplicateNameException(String message) {
        super(message);
    }

}
