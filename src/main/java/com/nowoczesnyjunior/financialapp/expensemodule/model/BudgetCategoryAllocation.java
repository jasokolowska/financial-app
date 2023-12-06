package com.nowoczesnyjunior.financialapp.expensemodule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "budget_category_allocation")
public class BudgetCategoryAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allocation_id")
    private Long allocationId;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;

    @Column(name = "allocated_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal allocatedAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetCategoryAllocation categoryAllocation = (BudgetCategoryAllocation) o;
        return Objects.equals(this.allocationId, categoryAllocation.allocationId) &&
                Objects.equals(this.allocatedAmount, categoryAllocation.allocatedAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.allocationId, this.allocatedAmount);
    }
}

