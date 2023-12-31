package com.nowoczesnyjunior.financialapp.expensemodule.service;

import com.nowoczesnyjunior.financialapp.expensemodule.mapper.ExpenseMapper;
import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.unit.utils.ExpenseDtoTestUtils;
import com.nowoczesnyjunior.financialapp.expensemodule.unit.utils.ExpenseTestUtils;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpenseMapper expenseMapper;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    void getExpenses_withCategory() {
        // GIVEN
        String startDate = "2023-01-01";
        LocalDateTime startLocalDate = LocalDate.parse(startDate).atStartOfDay();
        String endDate = "2023-12-31";
        LocalDateTime endLocalDate = LocalDate.parse(endDate).atStartOfDay();
        String categoryName = "Groceries";
        Integer topN = 10;

        List<Expense> expenseList = ExpenseTestUtils.createSampleExpenseList();
        List<ExpenseDto> sampleExpenseDtoList = ExpenseDtoTestUtils.createSampleExpenseDtoList();

        Mockito.when(expenseRepository.findExpenseByExpenseDateBetweenAndCategory(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(expenseList);
        Mockito.when(expenseMapper.expensesToDtos(expenseList)).thenReturn(sampleExpenseDtoList);

        // WHEN
        List<ExpenseDto> result = expenseService.getExpenses(startDate, endDate, categoryName, topN);

        // THEN
        verify(expenseRepository).findExpenseByExpenseDateBetweenAndCategory(
                eq(startLocalDate), eq(endLocalDate), eq(categoryName));
    }

    @Test
    void getExpenses_withoutCategory() {
        // GIVEN
        String startDate = "2023-01-01";
        LocalDateTime startLocalDate = LocalDate.parse(startDate).atStartOfDay();
        String endDate = "2023-12-31";
        LocalDateTime endLocalDate = LocalDate.parse(endDate).atStartOfDay();
        Integer topN = 10;

        List<Expense> expenseList = ExpenseTestUtils.createSampleExpenseList();
        List<ExpenseDto> sampleExpenseDtoList = ExpenseDtoTestUtils.createSampleExpenseDtoList();

        Mockito.when(expenseRepository.findExpenseByExpenseDateBetween(Mockito.any(), Mockito.any()))
                .thenReturn(expenseList);
        Mockito.when(expenseMapper.expensesToDtos(expenseList)).thenReturn(sampleExpenseDtoList);

        // WHEN
        List<ExpenseDto> result = expenseService.getExpenses(startDate, endDate, null, topN);

        // THEN
        verify(expenseRepository).findExpenseByExpenseDateBetween(
                eq(startLocalDate), eq(endLocalDate));
    }

    @Test
    void getExpenses_withoutCategoryAndDates() {
        // GIVEN
        LocalDateTime startLocalDate = LocalDate.parse("1900-01-01").atStartOfDay();
        LocalDateTime endLocalDate = LocalDate.now().atStartOfDay();

        List<Expense> expenseList = ExpenseTestUtils.createSampleExpenseList();
        List<ExpenseDto> sampleExpenseDtoList = ExpenseDtoTestUtils.createSampleExpenseDtoList();

        Mockito.when(expenseRepository.findExpenseByExpenseDateBetween(Mockito.any(), Mockito.any()))
                .thenReturn(expenseList);
        Mockito.when(expenseMapper.expensesToDtos(expenseList)).thenReturn(sampleExpenseDtoList);

        // WHEN
        List<ExpenseDto> result = expenseService.getExpenses(null, null, null, null);

        // THEN
        verify(expenseRepository).findExpenseByExpenseDateBetween(
                eq(startLocalDate), eq(endLocalDate));
    }

    @Test
    void getExpenses_withoutCategoryAndInvalidDates() {
        // GIVEN
        String startDate = "2023-01-01 12:00";
        String endDate = "2023-12-31";

        // THEN
        assertThrows(DateTimeParseException.class, () -> {
            expenseService.getExpenses(startDate, endDate, null, null);
        });
    }
}