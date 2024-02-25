package com.nowoczesnyjunior.financialapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:config/test-application.properties")
class FinancialAppApplicationTests {
	@Test
	void contextLoads() {
	}
}
