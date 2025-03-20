package com.archisacademy.parking.exception.user;

public class UserExistByEmailException extends RuntimeException {
    public UserExistByEmailException(String message) {
        super(message);
    }
}
