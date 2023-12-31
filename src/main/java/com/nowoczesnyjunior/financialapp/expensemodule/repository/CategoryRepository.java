package com.nowoczesnyjunior.financialapp.expensemodule.repository;

import com.nowoczesnyjunior.financialapp.expensemodule.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<ExpenseCategory, Long> {
}
