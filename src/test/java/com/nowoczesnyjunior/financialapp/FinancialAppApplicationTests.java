package com.nowoczesnyjunior.financialapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class FinancialAppApplicationTests {

	@Test
	void contextLoads() {
	}

}
