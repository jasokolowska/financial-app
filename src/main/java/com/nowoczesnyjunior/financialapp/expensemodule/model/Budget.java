package com.nowoczesnyjunior.financialapp.expensemodule.model;


import com.nowoczesnyjunior.financialapp.usermodule.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
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
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BudgetCategoryAllocation> allocations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Budget budget = (Budget) o;
        return Objects.equals(this.budgetId, budget.budgetId) &&
                Objects.equals(this.totalAmount, budget.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.budgetId, this.totalAmount);
    }
}

