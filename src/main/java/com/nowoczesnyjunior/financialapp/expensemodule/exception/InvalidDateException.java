package com.nowoczesnyjunior.financialapp.expensemodule.exception;

public class InvalidDateException extends IllegalArgumentException{

    public InvalidDateException(String s) {
        super(String.format("Provided date (%s). Correct date format is YYYY-MM-DD.", s));
    }
}
