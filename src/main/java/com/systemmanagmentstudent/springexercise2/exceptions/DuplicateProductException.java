package com.systemmanagmentstudent.springexercise2.exceptions;

public class DuplicateProductException extends RuntimeException{

    public DuplicateProductException(String message) {
        super(message);
    }

}
