package com.nowoczesnyjunior.financialapp.expensemodule.integration;

import com.nowoczesnyjunior.financialapp.expensemodule.mapper.ExpenseMapper;
import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.model.ExpenseCategory;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.service.ExpenseService;
import com.nowoczesnyjunior.financialapp.openapi.model.CategoryDto;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import com.nowoczesnyjunior.financialapp.usermodule.model.User;
import com.nowoczesnyjunior.financialapp.usermodule.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@TestPropertySource(locations = "classpath:config/test-application.properties")
@Sql(scripts = "classpath:test-data/insert-dummy-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class ExpenseServiceImplIntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseService expenseService;

    @Test
    void getExpenses_withCategory_shouldReturnDtos() {
        List<ExpenseDto> result = expenseService.getExpenses("2023-01-01", "2023-12-31", "Groceries", 10);

        assertEquals(2, result.size());
        assertEquals("Groceries for the week", result.get(0).getDescription());
    }

    @Test
    void getExpenses_withoutCategory_shouldReturnDtos() {
        List<ExpenseDto> result = expenseService.getExpenses("2023-01-01", "2023-12-31", null, 10);

        assertEquals(7, result.size());
    }

    // delete
    @Test
    void deleteExpense_withoutCategory_shouldReturnDtos() {
        // GIVEN
        Expense expense = new Expense();
        expense.setExpenseDate(LocalDateTime.now());
        expense.setDescription("New expense");
        expense.setAmount(BigDecimal.valueOf(41.77));
        expense.setUser(userRepository.findAll().get(0));

        // WHEN
        Long newExpenseId = expenseRepository.save(expense).getExpenseId();
        int expensesQuantityBefore = expenseRepository.findAll().size();
        expenseService.deleteExpense(newExpenseId);
        int expensesQuantityAfter = expenseRepository.findAll().size();

        assertEquals(1, expensesQuantityBefore - expensesQuantityAfter);
        assertFalse( expenseRepository.findById(newExpenseId).isPresent());
    }
    // add
    @Test
    void addNewExpense_withoutCategory_shouldReturnDtos() {
        // GIVEN
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Groceries");

        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setDate(LocalDateTime.now());
        expenseDto.setAmount(BigDecimal.valueOf(123.15));
        expenseDto.setDescription("Fuel");
        expenseDto.setCategory(categoryDto);

        // WHEN
        ExpenseDto newExpense = expenseService.addExpense(expenseDto);

        List<ExpenseDto> result = expenseService.getExpenses(null, null, null, null);

        assertEquals(8, result.size());
    }
    // edit

    @Test
    void editExpense_withoutCategory_shouldReturnDtos() {
        // GIVEN
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setDate(LocalDateTime.now());
        expenseDto.setAmount(BigDecimal.valueOf(123.15));
        expenseDto.setDescription("Fuel");

        // WHEN
        expenseService.editExpense(1L, expenseDto);
        Optional<Expense> result = expenseRepository.findById(1L);

        assertEquals(true, result.isPresent());
        assertEquals("Fuel", result.get().getDescription());
    }
}
