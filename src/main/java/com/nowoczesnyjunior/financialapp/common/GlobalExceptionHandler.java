package com.nowoczesnyjunior.financialapp.common;

import com.nowoczesnyjunior.financialapp.expensemodule.exception.CategoryNotFoundException;
import com.nowoczesnyjunior.financialapp.expensemodule.exception.InvalidDateException;
import com.nowoczesnyjunior.financialapp.openapi.model.ApiResponseDto;
import com.nowoczesnyjunior.financialapp.usermodule.exception.UserCreationException;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleException(Exception e) {
        logger.error(e.getLocalizedMessage());
        ApiResponseDto apiResponseDto = getApiResponseDto(500, e.getMessage(), "Internal error");
        return new ResponseEntity<>(apiResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleEntityNotFoundException(ObjectNotFoundException e) {
        logger.error(e.getLocalizedMessage());
        ApiResponseDto apiResponseDto = getApiResponseDto(400, e.getMessage(), "Bad request");
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleCategoryNotFoundException(CategoryNotFoundException e) {
        logger.error(e.getLocalizedMessage());
        ApiResponseDto apiResponseDto = getApiResponseDto(400, e.getMessage(), "Bad request");
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ApiResponseDto> handleInvalidDateException(InvalidDateException e) {
        logger.error(e.getLocalizedMessage());
        ApiResponseDto apiResponseDto = getApiResponseDto(400, e.getMessage(), "Bad request");
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ApiResponseDto>> handleInvalidInputData(MethodArgumentNotValidException e) {
        logger.error(e.getLocalizedMessage());
        List<ApiResponseDto> validationErrors = new ArrayList<>();

        e.getBindingResult().getAllErrors().stream()
                .filter(error -> error instanceof FieldError)
                .forEach(error -> {
                    String message = (((FieldError) error).getField() + " " + error.getDefaultMessage());
                    ApiResponseDto apiResponseDto = getApiResponseDto(400, message, error.getCode());
                    validationErrors.add(apiResponseDto);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ApiResponseDto> handleInvalidInputData(UserCreationException e) {
        logger.error(e.getLocalizedMessage());
        ApiResponseDto apiResponseDto = getApiResponseDto(400, e.getMessage(), "Bad request");
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    private static ApiResponseDto getApiResponseDto(int code, String message, String type) {
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setCode(code);
        apiResponseDto.setMessage(message);
        apiResponseDto.setType(type);
        return apiResponseDto;
    }
}
