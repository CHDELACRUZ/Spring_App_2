package com.systemmanagmentstudent.springexercise2.exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message) {
        super(message);
    }

}
