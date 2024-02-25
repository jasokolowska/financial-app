package com.nowoczesnyjunior.financialapp.expensemodule.exception;

import org.hibernate.ObjectNotFoundException;

public class CategoryNotFoundException extends IllegalArgumentException {

    public CategoryNotFoundException() {
        super(String.format("Provided category id doesn't exist."));
    }
}
