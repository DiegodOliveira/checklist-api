package com.learning.springboot.cheklistapi.exceptions;

public class CategoryServiceException extends RuntimeException{
    public CategoryServiceException(String message){
        super(message);
    }
}
