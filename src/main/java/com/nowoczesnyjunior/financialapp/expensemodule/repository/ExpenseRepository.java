package com.nowoczesnyjunior.financialapp.expensemodule.repository;

import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import com.nowoczesnyjunior.financialapp.expensemodule.model.ExpenseCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findExpenseByExpenseDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT e FROM Expense e INNER JOIN e.category ec " +
            "WHERE ec.categoryName = :categoryName " +
            "AND e.expenseDate BETWEEN :startDate AND :endDate")
    List<Expense> findExpenseByExpenseDateBetweenAndCategory(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("categoryName") String categoryName);

}
