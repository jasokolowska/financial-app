package com.nowoczesnyjunior.financialapp.expensemodule.unit.utils;

import com.nowoczesnyjunior.financialapp.openapi.model.CategoryDto;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExpenseDtoFixtures {

    private static final Random random = new Random();

    public static List<ExpenseDto> createExpenseDtos(int quantity) {
        List<ExpenseDto> expenseDtos = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            expenseDtos.add(createExpenseDto(getRandomAmount(), getRandomLocalDate(), createSampleCategoryDto(), getRandomDescription()));
        }
        return expenseDtos;
    }


    public static List<ExpenseDto> createSampleExpenseDtoList() {
        List<ExpenseDto> expenseDtos = new ArrayList<>();

        expenseDtos.add(createExpenseDto(getRandomAmount(), LocalDateTime.of(2023, 1, 15, 12, 0), createSampleCategoryDto(), "Groceries"));
        expenseDtos.add(createExpenseDto(getRandomAmount(), LocalDateTime.of(2023, 2, 5, 15, 30), createSampleCategoryDto(), "Entertainment"));
        expenseDtos.add(createExpenseDto(getRandomAmount(), LocalDateTime.of(2023, 3, 20, 9, 45), createSampleCategoryDto(), "Utilities"));

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

    private static BigDecimal getRandomAmount() {
        return BigDecimal.valueOf(random.nextDouble());
    }

    private static LocalDateTime getRandomLocalDate() {
        return LocalDateTime.now().minusDays(random.nextInt(5000));
    }

    private static String getRandomDescription() {
        return RandomStringUtils.randomAlphanumeric(100);
    }
}
