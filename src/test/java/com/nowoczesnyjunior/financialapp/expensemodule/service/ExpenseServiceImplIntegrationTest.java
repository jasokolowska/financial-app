package com.nowoczesnyjunior.financialapp.expensemodule.service;

import com.nowoczesnyjunior.financialapp.expensemodule.exception.CategoryNotFoundException;
import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.utils.IntegrationTest;
import com.nowoczesnyjunior.financialapp.openapi.model.CategoryDto;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import com.nowoczesnyjunior.financialapp.usermodule.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseServiceImplIntegrationTest extends IntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPrintAllExpenses() {
        // Retrieve all expenses from the repository
        List<Expense> expenses = expenseRepository.findAll();

        // Print each expense
        System.out.println("All Expenses:");
        for (Expense expense : expenses) {
            System.out.println(expense.getDescription());
        }
    }

    @Test
    @WithMockUser(username = "john_doe")
    void onRequestWithCategoryItShouldReturnExpensesForThatCategory() {
        // WHEN
        List<ExpenseDto> result = expenseService.getExpenses("2023-01-01", "2023-12-31", "Groceries", 10);

        // THEN
        assertEquals(2, result.size());
        result.forEach(expenseDto -> assertEquals("Groceries", expenseDto.getCategory().getName()));
    }

    @Test
    @WithMockUser(username = "john_doe")
    void onRequestWithoutCategoryItShouldReturnAllExpenses() {
        // GIVEN
        int allExpensesSize = expenseRepository.findAll().size();

        // WHEN
        List<ExpenseDto> result = expenseService.getExpenses("2023-01-01", "2023-12-31", null, 10);

        // THEN
        assertEquals(allExpensesSize, result.size());
    }

    @Test
    @WithMockUser(username = "john_doe")
    void onDeleteExistingExpenseByIdItShouldBeDeleted() {
        // GIVEN
        Expense expense = new Expense();
        expense.setExpenseDate(LocalDateTime.now());
        expense.setDescription("New expense");
        expense.setAmount(BigDecimal.valueOf(41.77));
        expense.setUser(userRepository.findAll().get(0));

        // WHEN
        Long newExpenseId = expenseRepository.save(expense).getId();
        int expensesQuantityBefore = expenseRepository.findAll().size();
        expenseService.deleteExpense(newExpenseId);
        int expensesQuantityAfter = expenseRepository.findAll().size();

        // THEN
        assertEquals(1, expensesQuantityBefore - expensesQuantityAfter);
        assertFalse(expenseRepository.findById(newExpenseId).isPresent());
    }

    @Test
    @WithMockUser(username = "john_doe@gmail.com")
    void onAddNewExpenseWithoutCategoryItShouldReturnExpense() {
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

        // THEN
        assertNotNull(newExpense.getId());
    }

    @Test
    @WithMockUser(username = "john_doe")
    void onAddNewExpenseWithoutCategoryItShouldThrowException() {
        // GIVEN
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setDate(LocalDateTime.now());
        expenseDto.setAmount(BigDecimal.valueOf(123.15));
        expenseDto.setDescription("Fuel");

        // WHEN & THEN
        assertThrows(CategoryNotFoundException.class, () -> {
            expenseService.addExpense(expenseDto);
        });
    }

    @Test
    @WithMockUser(username = "john_doe")
    void onEditExpenseByIdShouldReturnChangedExpense() {
        // GIVEN
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setDate(LocalDateTime.now());
        expenseDto.setAmount(BigDecimal.valueOf(123.15));
        expenseDto.setDescription("Fuel");

        // WHEN
        expenseService.editExpense(1L, expenseDto);
        Optional<Expense> result = expenseRepository.findById(1L);

        // THEN
        assertTrue(result.isPresent());
        assertEquals("Fuel", result.get().getDescription());
    }
}
