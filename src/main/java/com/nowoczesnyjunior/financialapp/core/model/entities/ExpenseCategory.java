package com.nowoczesnyjunior.financialapp.core.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "expense_categories")
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Expense> expenses;

    @OneToMany(mappedBy = "category")
    private List<BudgetCategoryAllocation> allocations;

    // Getters and setters
}

