package com.francinjr.rentalbusiness.components.users;

public class UsernameOrPasswordInvalidException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UsernameOrPasswordInvalidException(String message) {
        super(message);
    }
}
