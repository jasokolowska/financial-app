package com.nowoczesnyjunior.financialapp.expensemodule.unit.service;

import com.nowoczesnyjunior.financialapp.expensemodule.exception.InvalidDateException;
import com.nowoczesnyjunior.financialapp.expensemodule.mapper.ExpenseMapper;
import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.CategoryRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.openapi.model.CategoryDto;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import com.nowoczesnyjunior.financialapp.usermodule.model.User;
import com.nowoczesnyjunior.financialapp.usermodule.repository.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;

    LocalDateTime defaultStartDate = LocalDateTime.of(1970, 01, 01, 00, 00);
    LocalDateTime defaultEndDate = LocalDate.now().atStartOfDay();

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, UserRepository userRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.expenseMapper = expenseMapper;
    }

    @Override
    public List<ExpenseDto> getExpenses(String startDate, String endDate, String categoryName, Integer topN) {
        LocalDateTime startLocalDate = startDate != null ? getLocalDateTime(startDate) : defaultStartDate;
        LocalDateTime endLocalDate = endDate != null ? getLocalDateTime(endDate) : defaultEndDate;

        List<Expense> list;
        if (categoryName != null) {
            list = expenseRepository.findExpenseByExpenseDateBetweenAndCategory(startLocalDate, endLocalDate, categoryName);
        } else {
            list = expenseRepository.findExpenseByExpenseDateBetween(startLocalDate, endLocalDate);
        }
        return expenseMapper.expensesToDtos(list);
    }

    @Override
    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        Expense expense = expenseMapper.dtoToModel(expenseDto);
        // TODO: get active user from usermodule
        User user = userRepository.findAll().get(0);
        expense.setUser(user);

        if (!isCategoryAvailable(expenseDto.getCategory())) {
            throw new ObjectNotFoundException("Category", expenseDto.getCategory().getId());
        }

        Expense savedExpense = expenseRepository.save(expense);
        return expenseMapper.expenseToExpenseDto(savedExpense);
    }

    @Override
    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public ExpenseDto editExpense(Long expenseId, ExpenseDto expenseDto) {
        Optional<Expense> oldExpence = expenseRepository.findById(expenseId);
        if (oldExpence.isPresent()) {
            Expense expense = expenseMapper.dtoToModel(expenseDto);
            expense.setExpenseId(expenseId);
            expense.setUser(oldExpence.get().getUser());
            Expense savedExpense = expenseRepository.save(expense);
            return expenseMapper.expenseToExpenseDto(savedExpense);
        } else {
            throw new ObjectNotFoundException( "Expense", expenseId);
        }
    }

    private LocalDateTime getLocalDateTime(String date){
        try {
            return LocalDate.parse(date).atStartOfDay();
        } catch (Exception e) {
            throw new InvalidDateException(date);
        }
    }

    private boolean isCategoryAvailable(CategoryDto category) {
        return this.categoryRepository.findById(category.getId()).isPresent();
    }
}
