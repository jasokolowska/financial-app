package com.nowoczesnyjunior.financialapp.expensemodule.model;

import com.nowoczesnyjunior.financialapp.usermodule.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long expenseId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "expense_date", nullable = false)
    private Date expenseDate;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;
        return Objects.equals(this.expenseId, expense.expenseId) &&
                Objects.equals(this.amount, expense.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.expenseId, this.amount);
    }
}
