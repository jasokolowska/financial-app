package com.nowoczesnyjunior.financialapp.expensemodule.service;

import com.nowoczesnyjunior.financialapp.expensemodule.mapper.ExpenseMapper;
import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import com.nowoczesnyjunior.financialapp.usermodule.model.User;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    LocalDateTime defaultStartDate = LocalDateTime.of(1900, 01, 01, 00, 00);
    LocalDateTime defaultEndDate = LocalDate.now().atStartOfDay();

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
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
        User user = new User();
        user.setUserId(1L);
        expense.setUser(user);
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
        return LocalDate.parse(date).atStartOfDay();
    }
}
