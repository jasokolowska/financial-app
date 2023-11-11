package com.nowoczesnyjunior.financialapp.usermodule.model;

import com.nowoczesnyjunior.financialapp.expensemodule.model.Budget;
import com.nowoczesnyjunior.financialapp.expensemodule.model.Expense;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Expense> expenses;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Budget> budgets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(this.userId, user.userId) &&
                Objects.equals(this.lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.lastName);
    }
}

