package com.nowoczesnyjunior.financialapp.expensemodule.repository;

import com.nowoczesnyjunior.financialapp.expensemodule.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<ExpenseCategory, Long> {

    Optional<ExpenseCategory> findExpenseCategoryByCategoryName(String categoryName);
}
