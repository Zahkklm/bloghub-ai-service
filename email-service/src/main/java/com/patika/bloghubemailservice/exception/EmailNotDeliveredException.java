package com.patika.bloghubemailservice.exception;

public class EmailNotDeliveredException extends RuntimeException{
    public EmailNotDeliveredException(String message) {
        super(message);
    }


}
