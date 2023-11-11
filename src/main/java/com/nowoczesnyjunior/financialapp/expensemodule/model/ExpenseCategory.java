package com.nowoczesnyjunior.financialapp.expensemodule.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
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
    @ToString.Exclude
    private List<Expense> expenses;

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<BudgetCategoryAllocation> allocations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpenseCategory expenseCategory = (ExpenseCategory) o;
        return Objects.equals(this.categoryId, expenseCategory.categoryId) &&
                Objects.equals(this.categoryName, expenseCategory.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.categoryId, this.categoryName);
    }
}

