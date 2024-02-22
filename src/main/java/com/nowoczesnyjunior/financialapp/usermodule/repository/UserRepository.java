package com.nowoczesnyjunior.financialapp.usermodule.repository;

import com.nowoczesnyjunior.financialapp.usermodule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
