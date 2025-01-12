package com.nowoczesnyjunior.financialapp.usermodule.exception;

public class UserCreationException extends RuntimeException{

    public UserCreationException() {
        super("New user not created - failure ocured during saving new user in db");
    }
}
