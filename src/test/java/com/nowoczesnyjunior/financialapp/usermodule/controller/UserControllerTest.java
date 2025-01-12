package com.nowoczesnyjunior.financialapp.usermodule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nowoczesnyjunior.financialapp.expensemodule.repository.ExpenseRepository;
import com.nowoczesnyjunior.financialapp.expensemodule.utils.ExpenseDtoFixtures;
import com.nowoczesnyjunior.financialapp.expensemodule.utils.IntegrationTest;
import com.nowoczesnyjunior.financialapp.openapi.model.CredentialDetailsDto;
import com.nowoczesnyjunior.financialapp.openapi.model.ExpenseDto;
import com.nowoczesnyjunior.financialapp.usermodule.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoginWhenValidCredentials() throws Exception {
        // GIVEN
        CredentialDetailsDto credentialDetailsDto = new CredentialDetailsDto();
        credentialDetailsDto.setUsername("john_doe@gmail.com");
        credentialDetailsDto.setPassword("string");

        // WHEN & THEN
        mockMvc.perform(post("/v1/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentialDetailsDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

}