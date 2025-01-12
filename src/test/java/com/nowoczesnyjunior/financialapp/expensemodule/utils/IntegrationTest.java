package com.nowoczesnyjunior.financialapp.expensemodule.utils;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public abstract class IntegrationTest {
}
