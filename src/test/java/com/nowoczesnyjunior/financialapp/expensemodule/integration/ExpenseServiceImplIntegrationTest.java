package com.nowoczesnyjunior.financialapp.expensemodule.integration;

import com.nowoczesnyjunior.financialapp.expensemodule.mapper.ExpenseMapper;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.service.ExpenseService;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class ExpenseServiceImplIntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseMapper expenseMapper;

    @Autowired
    private ExpenseService expenseService;

    @Test
    void getExpenses_withCategory_shouldReturnDtos() {
        // Perform integration test by calling the actual database
        List<ExpenseDto> result = expenseService.getExpenses("2023-01-01", "2023-12-31", "Groceries", 10);

        assertEquals(2, result.size());
        assertEquals("Groceries for the week", result.get(0).getDescription());
    }

    @Test
    void getExpenses_withoutCategory_shouldReturnDtos() {
        // Perform integration test by calling the actual database
        List<ExpenseDto> result = expenseService.getExpenses("2023-01-01", "2023-12-31", null, 10);

        assertEquals(7, result.size());
    }
}
