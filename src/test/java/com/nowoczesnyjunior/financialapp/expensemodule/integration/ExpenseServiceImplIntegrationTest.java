package com.nowoczesnyjunior.financialapp.expensemodule.integration;

import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.service.ExpenseService;
import com.nowoczesnyjunior.financialapp.openapi.model.CategoryDto;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import com.nowoczesnyjunior.financialapp.usermodule.repository.UserRepository;
import org.hibernate.ObjectNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void onRequestWithCategoryItShouldReturnExpensesForThatCategory() {
        // WHEN
        List<ExpenseDto> result = expenseService.getExpenses("2023-01-01", "2023-12-31", "Groceries", 10);

        // THEN
        assertEquals(2, result.size());
        result.forEach(expenseDto -> assertEquals("Groceries", expenseDto.getCategory().getName()));
    }

    @Test
    void onRequestWithoutCategoryItShouldReturnAllExpenses() {
        // GIVEN
        int allExpensesSize = expenseRepository.findAll().size();

        // WHEN
        List<ExpenseDto> result = expenseService.getExpenses("2023-01-01", "2023-12-31", null, 10);

        // THEN
        assertEquals(allExpensesSize, result.size());
    }

    @Test
    void onDeleteExistingExpenseByIdItShouldBeDeleted() {
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

        // THEN
        assertEquals(1, expensesQuantityBefore - expensesQuantityAfter);
        assertFalse(expenseRepository.findById(newExpenseId).isPresent());
    }

    @Test
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
        List<ExpenseDto> result = expenseService.getExpenses(null, null, null, null);

        // THEN
        assertEquals(8, result.size());
    }

    @Test
    void onAddNewExpenseWithoutCategoryItShouldThrowException() {
        // GIVEN
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(111L);
        categoryDto.setName("Transport");

        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setDate(LocalDateTime.now());
        expenseDto.setAmount(BigDecimal.valueOf(123.15));
        expenseDto.setDescription("Fuel");
        expenseDto.setCategory(categoryDto);

        // WHEN & THEN
        assertThrows(ObjectNotFoundException.class, () -> {
            expenseService.addExpense(expenseDto);
        });
    }

    @Test
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
        assertEquals(true, result.isPresent());
        assertEquals("Fuel", result.get().getDescription());
    }
}
