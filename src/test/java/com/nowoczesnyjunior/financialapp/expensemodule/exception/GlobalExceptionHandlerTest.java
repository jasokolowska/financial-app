package com.nowoczesnyjunior.financialapp.expensemodule.exception;

import com.nowoczesnyjunior.financialapp.common.GlobalExceptionHandler;
import com.nowoczesnyjunior.financialapp.openapi.model.ApiResponseDto;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private Exception exception;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void shouldReturnInternalServerError() {
        when(exception.getMessage()).thenReturn("Internal server error");

        ResponseEntity<ApiResponseDto> responseEntity = globalExceptionHandler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal error", responseEntity.getBody().getType());
        assertEquals("Internal server error", responseEntity.getBody().getMessage());
        assertEquals(500, responseEntity.getBody().getCode());
    }

    @Test
    void  shouldReturnNotFound() {
        ObjectNotFoundException objectNotFoundException = mock(ObjectNotFoundException.class);
        when(objectNotFoundException.getEntityName()).thenReturn("Entity");
        when(objectNotFoundException.getIdentifier()).thenReturn(123);

        ResponseEntity<ApiResponseDto> responseEntity = globalExceptionHandler.handleEntityNotFoundException(objectNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Not Found", responseEntity.getBody().getType());
        assertEquals("Entity with the given Id (123) not found.", responseEntity.getBody().getMessage());
        assertEquals(404, responseEntity.getBody().getCode());
    }
}
