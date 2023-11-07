package com.nowoczesnyjunior.financialapp.core.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
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
}

