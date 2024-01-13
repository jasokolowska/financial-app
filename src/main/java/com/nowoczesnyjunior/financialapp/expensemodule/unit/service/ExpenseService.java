package com.nowoczesnyjunior.financialapp.expensemodule.unit.service;

import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;

import java.util.List;

public interface ExpenseService {

    List<ExpenseDto> getExpenses(String startDate, String endDate, String categoryName, Integer topN);

    ExpenseDto addExpense(ExpenseDto expense);

    void deleteExpense(Long expenseId);

    ExpenseDto editExpense(Long expenseId, ExpenseDto expense);
}
