package com.nowoczesnyjunior.financialapp.expensemodule.service;

import com.nowoczesnyjunior.financialapp.expensemodule.exception.InvalidDateException;
import com.nowoczesnyjunior.financialapp.expensemodule.mapper.ExpenseMapper;
import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.model.ExpenseCategory;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.CategoryRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.utils.ExpenseDtoFixtures;
import com.nowoczesnyjunior.financialapp.expensemodule.utils.ExpenseFixtures;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ExpenseMapper expenseMapper;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    void shouldReturnAllExpensesFromGivenCategory() {
        // GIVEN
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        String categoryName = "Groceries";
        Integer topN = 10;

        List<Expense> expenseList = ExpenseFixtures.createSampleExpenseList();
        List<Expense> expensesForGroceriesCategory = expenseList.stream().filter(expense ->
            expense.getCategory().getCategoryName().contains("Groceries")).collect(Collectors.toList());

        List<ExpenseDto> expenseDtos = ExpenseDtoFixtures.createExpenseDtos(2);

        when(expenseRepository.findExpensesByExpenseDateBetweenAndCategory_CategoryName(any(), any(), any()))
                .thenReturn(expensesForGroceriesCategory);
        when(categoryRepository.findExpenseCategoryByCategoryName(any())).thenReturn(Optional.of(new ExpenseCategory()));
        when(expenseMapper.toDtoList(expensesForGroceriesCategory)).thenReturn(expenseDtos);

        // WHEN
        List<ExpenseDto> result = expenseService.getExpenses(startDate, endDate, categoryName, topN);

        // THEN
        assertNotNull(result);
        assertThat(result.size()).isEqualTo(expenseDtos.size());
    }

    @Test
    void shouldReturnAllExpenses() {
        // GIVEN
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";
        Integer topN = 10;

        List<Expense> expenseList = ExpenseFixtures.createExpenses(5);
        List<ExpenseDto> expenseDtos = ExpenseDtoFixtures.createExpenseDtos(5);

        when(expenseRepository.findExpenseByExpenseDateBetween(any(), any()))
                .thenReturn(expenseList);
        when(expenseMapper.toDtoList(expenseList)).thenReturn(expenseDtos);

        // WHEN
        List<ExpenseDto> result = expenseService.getExpenses(startDate, endDate, null, topN);

        // THEN
        assertNotNull(result);
        assertThat(result.size()).isEqualTo(expenseDtos.size());
    }

    @Test
    void shouldReturnAllExpensesWithDefaultDateRange() {
        // GIVEN
        LocalDateTime startLocalDate = LocalDate.parse("1900-01-01").atStartOfDay();
        LocalDateTime endLocalDate = LocalDate.now().atStartOfDay();

        List<Expense> expenseList = ExpenseFixtures.createExpenses(7);
        List<ExpenseDto> expenseDtoList = ExpenseDtoFixtures.createExpenseDtos(7);

        when(expenseRepository.findExpenseByExpenseDateBetween(any(), any()))
                .thenReturn(expenseList);
        when(expenseMapper.toDtoList(expenseList)).thenReturn(expenseDtoList);

        // WHEN
        List<ExpenseDto> result = expenseService.getExpenses(null, null, null, null);

        // THEN
        assertNotNull(result);
        assertThat(result.size()).isEqualTo(expenseDtoList.size());
    }

    @Test
    void shouldThrowExceptionWhenInvalidDates() {
        // GIVEN
        String startDate = "2023-01-01 12:00";
        String endDate = "2023-12-31";

        // THEN
        assertThrows(InvalidDateException.class, () -> {
            expenseService.getExpenses(startDate, endDate, null, null);
        });
    }
}