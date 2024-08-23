package com.patika.bloghubservice.exception;

public class UserStatusNotChangedException extends RuntimeException{

    public UserStatusNotChangedException(String email) {
        super("User durumu değiştirilemedi: " + email);
    }
}
