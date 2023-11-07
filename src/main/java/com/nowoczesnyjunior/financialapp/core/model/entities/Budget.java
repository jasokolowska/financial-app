package com.nowoczesnyjunior.financialapp.core.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long budgetId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "budget_name", nullable = false)
    private String budgetName;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "budget")
    private List<BudgetCategoryAllocation> allocations;
}

