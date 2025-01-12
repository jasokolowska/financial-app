package com.nowoczesnyjunior.financialapp.usermodule.repository;

import com.nowoczesnyjunior.financialapp.usermodule.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsername(String username);
    AppUser findByUsername(String username);
}
