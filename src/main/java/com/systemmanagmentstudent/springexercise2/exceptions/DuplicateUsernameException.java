package com.systemmanagmentstudent.springexercise2.exceptions;


public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException(String message) {
        super(message);
    }

}
