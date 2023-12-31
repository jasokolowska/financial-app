package com.nowoczesnyjunior.financialapp.expensemodule.unit.utils;

import com.nowoczesnyjunior.financialapp.openapi.model.CategoryDto;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDtoTestUtils {

    public static List<ExpenseDto> createSampleExpenseDtoList() {
        List<ExpenseDto> expenseDtos = new ArrayList<>();

        expenseDtos.add(createExpenseDto(BigDecimal.valueOf(50.0), LocalDateTime.of(2023, 1, 15, 12, 0), createSampleCategoryDto(), "Groceries"));
        expenseDtos.add(createExpenseDto(BigDecimal.valueOf(30.5), LocalDateTime.of(2023, 2, 5, 15, 30), createSampleCategoryDto(), "Entertainment"));
        expenseDtos.add(createExpenseDto(BigDecimal.valueOf(100.0), LocalDateTime.of(2023, 3, 20, 9, 45), createSampleCategoryDto(), "Utilities"));

        return expenseDtos;
    }

    private static ExpenseDto createExpenseDto(BigDecimal amount, LocalDateTime date, CategoryDto category, String description) {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAmount(amount);
        expenseDto.setDate(date);
        expenseDto.setCategory(category);
        expenseDto.setDescription(description);
        return expenseDto;
    }

    private static CategoryDto createSampleCategoryDto() {
        return new CategoryDto();
    }
}
