package com.nowoczesnyjunior.financialapp.expensemodule.utils;

import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.model.ExpenseCategory;
import com.nowoczesnyjunior.financialapp.usermodule.model.User;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ExpenseFixtures {

    private static final Random random = new Random();

    public static List<Expense> createExpenses(int quantity) {
        List<Expense> expenses = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            expenses.add(createExpense(createSampleUser(), createSampleCategory(), getRandomAmount(), getRandomLocalDate(), getRandomDescription()));
        }
        return expenses;
    }

    public static List<Expense> createSampleExpenseList() {
        List<Expense> expenses = new ArrayList<>();

        expenses.add(createExpense(createSampleUser(), createSampleCategory(), BigDecimal.valueOf(50.0), LocalDateTime.of(2023, 1, 15, 12, 0), "Groceries"));
        expenses.add(createExpense(createSampleUser(), createSampleCategory(), BigDecimal.valueOf(30.5), LocalDateTime.of(2023, 2, 5, 15, 30), "Entertainment"));
        expenses.add(createExpense(createSampleUser(), createSampleCategory(), BigDecimal.valueOf(100.0), LocalDateTime.of(2023, 3, 20, 9, 45), "Utilities"));

        return expenses;
    }

    private static Expense createExpense(User user, ExpenseCategory category, BigDecimal amount, LocalDateTime expenseDate, String description) {
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setExpenseDate(expenseDate);
        expense.setDescription(description);
        return expense;
    }

    private static User createSampleUser() {
        // Create and return a sample User object
        // You can set user properties as needed
        return new User();
    }

    private static ExpenseCategory createSampleCategory() {
        List<String> categories = Arrays.asList("Groceries", "Transport", "Tax");
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setCategoryName(categories.get(random.nextInt(categories.size())));
        return expenseCategory;
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
