package com.nowoczesnyjunior.financialapp.expensemodule.controller;

import com.nowoczesnyjunior.financialapp.expensemodule.service.ExpenseService;
import com.nowoczesnyjunior.financialapp.openapi.api.ExpensesApi;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v3/api")
public class ExpenseController implements ExpensesApi {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    public ResponseEntity<ExpenseDto> addExpense(ExpenseDto expense) {
        return ResponseEntity.ok(expenseService.addExpense(expense));
    }

    @Override
    public ResponseEntity<Void> deleteExpense(Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<ExpenseDto> editExpense(Long expenseId, ExpenseDto expense) {
        return ResponseEntity.ok(expenseService.editExpense(expenseId, expense));
    }

    @Override
    public ResponseEntity<List<ExpenseDto>> getExpenses(String startDate, String endDate, String categoryName, Integer topN) {
        List<ExpenseDto> expenses = expenseService.getExpenses(startDate, endDate, categoryName,topN);
        return ResponseEntity.ok(expenses);
    }
}
