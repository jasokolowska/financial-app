package com.nowoczesnyjunior.financialapp.expensemodule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.utils.ExpenseDtoFixtures;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import org.checkerframework.checker.regex.qual.Regex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:config/test-application.properties")
@Sql(scripts = "classpath:test-data/insert-dummy-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@WebAppConfiguration
class ExpenseControllerTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void shouldReturnExpenseDtoWhenExpenseSuccessfullySaved() throws Exception {
        // GIVEN
        ExpenseDto expenseDto = ExpenseDtoFixtures.createExpenseDtos(1).get(0);

        // WHEN & THEN
        mockMvc.perform(post("/v1/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(expenseDto.getAmount()));
    }

    @Test
    void shouldReturnBadRequestWhenCategoryNotExists() throws Exception {
        // GIVEN
        ExpenseDto expenseDto = ExpenseDtoFixtures.createExpenseDtos(1).get(0);
        Long categoryId = null;
        expenseDto.getCategory().setId(categoryId);

        // WHEN & THEN
        mockMvc.perform(post("/v1/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Provided category id doesn't exist."));
    }


    @Test
    void shouldReturnAcceptedWhenExpenseSuccessfulyDeleted() throws Exception {
        // GIVEN
        Long expenseId = 1L;

        // WHEN & THEN
        mockMvc.perform(delete("/v1/api/expenses/{expenseId}", expenseId))
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldReturnOkWhenExpenseSuccessfullyUpdated() throws Exception {
        // GIVEN
        Long expenseId = expenseRepository.findAll().get(0).getExpenseId();
        ExpenseDto expenseDto = ExpenseDtoFixtures.createExpenseDtos(1).get(0);

        // WHEN & THEN
        mockMvc.perform(put("/v1/api/expenses/{expenseId}", expenseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(expenseDto.getAmount()));
    }

    @Test
    void shouldReturnAllExpensesWhenExpensesSearchCriteriaAreEmpty() throws Exception {
        // GIVEN
        int allExpensenQuantity = expenseRepository.findAll().size();

        // WHEN & THEN
        mockMvc.perform(get("/v1/api/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(allExpensenQuantity));
    }

    @Test
    void shouldReturnFilteredExpensesWhenForGivenDates() throws Exception {
        // WHEN & THEN
        mockMvc.perform(get("/v1/api/expenses")
                        .param("start_date", "2022-01-01")
                        .param("end_date", "2022-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnFilteredExpensesWhenForGivenCategory() throws Exception {
        // WHEN & THEN
        mockMvc.perform(get("/v1/api/expenses")
                        .param("category", "Electricity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void shouldThorwBadRequestWhenInvalidDates() throws Exception {
        // WHEN & THEN
        mockMvc.perform(get("/v1/api/expenses")
                        .param("start_date", "2022-01-0")
                        .param("end_date", "2022-12-"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Provided date (2022-01-0). Correct date format is YYYY-MM-DD."));
    }

    @Test
    void shouldThorwBadRequestWhenInvalidCategory() throws Exception {
        // WHEN & THEN
        mockMvc.perform(get("/v1/api/expenses")
                        .param("category", "invalid category"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Provided category id doesn't exist."));
    }
}
