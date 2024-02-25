package com.nowoczesnyjunior.financialapp.expensemodule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.utils.ExpenseDtoFixtures;
import com.nowoczesnyjunior.financialapp.expensemodule.utils.IntegrationTest;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExpenseControllerTest extends IntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "john_doe")
    void shouldReturnExpenseDtoWhenExpenseSuccessfullySaved() throws Exception {
        // GIVEN
        ExpenseDto expenseDto = ExpenseDtoFixtures.createExpenseDtos(1).get(0);

        // WHEN & THEN
        ResultActions resultActions = mockMvc.perform(post("/v1/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(expenseDto.getAmount()));
    }

    @Test
    @WithMockUser(username = "john_doe")
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
    @WithMockUser(username = "john_doe")
    void shouldReturnAcceptedWhenExpenseSuccessfulyDeleted() throws Exception {
        // GIVEN
        Long expenseId = 1L;

        // WHEN & THEN
        mockMvc.perform(delete("/v1/api/expenses/{expenseId}", expenseId))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username = "john_doe")
    void shouldReturnOkWhenExpenseSuccessfullyUpdated() throws Exception {
        // GIVEN
        Long expenseId = expenseRepository.findAll().get(0).getId();
        ExpenseDto expenseDto = ExpenseDtoFixtures.createExpenseDtos(1).get(0);

        // WHEN & THEN
        mockMvc.perform(put("/v1/api/expenses/{expenseId}", expenseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(expenseDto.getAmount()));
    }

    @Test
    @WithMockUser(username = "john_doe")
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
    @WithMockUser(username = "john_doe")
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
    @WithMockUser(username = "john_doe")
    void shouldReturnFilteredExpensesWhenForGivenCategory() throws Exception {
        // WHEN & THEN
        mockMvc.perform(get("/v1/api/expenses")
                        .param("category", "Electricity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    @WithMockUser(username = "john_doe")
    void shouldThrowBadRequestWhenInvalidDates() throws Exception {
        // WHEN & THEN
        mockMvc.perform(get("/v1/api/expenses")
                        .param("start_date", "2022-01-0")
                        .param("end_date", "2022-12-"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Provided date (2022-01-0). Correct date format is YYYY-MM-DD."));
    }

    @Test
    @WithMockUser(username = "john_doe")
    void shouldThorwBadRequestWhenInvalidCategory() throws Exception {
        // WHEN & THEN
        mockMvc.perform(get("/v1/api/expenses")
                        .param("category", "invalid category"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Provided category id doesn't exist."));
    }
}
