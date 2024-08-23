package com.patika.bloghubservice.exception;

public class EmailNotDeliveredException extends RuntimeException{
    public EmailNotDeliveredException(String message) {
        super(message);
    }


}
