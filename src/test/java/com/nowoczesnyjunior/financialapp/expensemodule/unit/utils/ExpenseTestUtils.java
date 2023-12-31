package com.nowoczesnyjunior.financialapp.expensemodule.unit.utils;

import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.model.ExpenseCategory;
import com.nowoczesnyjunior.financialapp.usermodule.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTestUtils {

    public static List<Expense> createSampleExpenseList() {
        List<Expense> expenses = new ArrayList<>();

        expenses.add(createExpense(1L, createSampleUser(), createSampleCategory("Groceries"), BigDecimal.valueOf(50.0), LocalDateTime.of(2023, 1, 15, 12, 0), "Groceries"));
        expenses.add(createExpense(2L, createSampleUser(), createSampleCategory("Groceries"), BigDecimal.valueOf(30.5), LocalDateTime.of(2023, 2, 5, 15, 30), "Entertainment"));
        expenses.add(createExpense(3L, createSampleUser(), createSampleCategory("Utilities"), BigDecimal.valueOf(100.0), LocalDateTime.of(2023, 3, 20, 9, 45), "Utilities"));

        return expenses;
    }

    private static Expense createExpense(Long expenseId, User user, ExpenseCategory category, BigDecimal amount, LocalDateTime expenseDate, String description) {
        Expense expense = new Expense();
        expense.setExpenseId(expenseId);
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

    private static ExpenseCategory createSampleCategory(String categoryName) {
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setCategoryName(categoryName);
        return expenseCategory;
    }
}
