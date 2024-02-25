package com.nowoczesnyjunior.financialapp.expensemodule.service;

import com.nowoczesnyjunior.financialapp.expensemodule.exception.CategoryNotFoundException;
import com.nowoczesnyjunior.financialapp.expensemodule.exception.InvalidDateException;
import com.nowoczesnyjunior.financialapp.expensemodule.mapper.ExpenseMapper;
import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.CategoryRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.openapi.model.CategoryDto;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import com.nowoczesnyjunior.financialapp.usermodule.model.User;
import com.nowoczesnyjunior.financialapp.usermodule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;

    LocalDateTime defaultStartDate = LocalDateTime.of(1970, 01, 01, 00, 00);
    LocalDateTime defaultEndDate = LocalDate.now().atStartOfDay();

    @Override
    public List<ExpenseDto> getExpenses(String startDate, String endDate, String categoryName, Integer topN) {
        LocalDateTime startLocalDate = startDate != null ? getLocalDateTime(startDate) : defaultStartDate;
        LocalDateTime endLocalDate = endDate != null ? getLocalDateTime(endDate) : defaultEndDate;
        List<Expense> expenses;

        if (categoryName != null) {
            validateCategory(categoryName);
            expenses = expenseRepository.findExpensesByExpenseDateBetweenAndCategory_CategoryName(startLocalDate,
                    endLocalDate, categoryName);
        } else {
            expenses = expenseRepository.findExpenseByExpenseDateBetween(startLocalDate, endLocalDate);
        }
        return expenseMapper.toDtoList(expenses);
    }

    @Override
    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        if (expenseDto.getCategory().getId() == null || !categoryExists(expenseDto.getCategory())) {
            throw new CategoryNotFoundException();
        }

        Expense expense = expenseMapper.toModel(expenseDto);
        // TODO: get active user from usermodule
        User user = userRepository.findAll().get(0);
        expense.setUser(user);

        Expense savedExpense = expenseRepository.save(expense);
        return expenseMapper.toDto(savedExpense);
    }

    @Override
    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public ExpenseDto editExpense(Long expenseId, ExpenseDto expenseDto) {
        Optional<Expense> oldExpenceOptional = expenseRepository.findById(expenseId);
        if (oldExpenceOptional.isPresent()) {
            Expense expense = expenseMapper.toModel(expenseDto);
            expense.setExpenseId(expenseId);
            expense.setUser(oldExpenceOptional.get().getUser());
            Expense savedExpense = expenseRepository.save(expense);
            return expenseMapper.toDto(savedExpense);
        } else {
            throw new ObjectNotFoundException("Expense", expenseId);
        }
    }

    private LocalDateTime getLocalDateTime(String date) {
        try {
            return LocalDate.parse(date).atStartOfDay();
        } catch (Exception e) {
            throw new InvalidDateException(date);
        }
    }

    private void validateCategory(String categoryName) {
        // TODO: Add UNIQUE constraint to category name
        if (!categoryExists(categoryName)) {
            throw new CategoryNotFoundException();
        }
    }


    private boolean categoryExists(CategoryDto category) {
        return this.categoryRepository.findById(category.getId()).isPresent();
    }

    private boolean categoryExists(String categoryName) {
        return this.categoryRepository.findExpenseCategoryByCategoryName(categoryName).isPresent();
    }
}
