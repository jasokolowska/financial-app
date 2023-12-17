package com.nowoczesnyjunior.financialapp.expensemodule.exception;

import com.nowoczesnyjunior.financialapp.openapi.model.ApiResponseDto;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        e.printStackTrace();
        String errorMessage = "An unexpected error occurred.";
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleEntityNotFoundException(ObjectNotFoundException e) {
        e.printStackTrace();

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setCode(404);
        apiResponseDto.setMessage(e.getEntityName() + " with the given Id (" + e.getIdentifier().toString() + ") not found.");
        apiResponseDto.setType("Not Found");

        return new ResponseEntity<>(apiResponseDto, HttpStatus.NOT_FOUND);
    }
}
