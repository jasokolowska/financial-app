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
    public ResponseEntity<ApiResponseDto> handleException(Exception e) {
        e.printStackTrace();

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setCode(500);
        apiResponseDto.setMessage(e.getMessage());
        apiResponseDto.setType("Internal error");

        return new ResponseEntity<>(apiResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
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