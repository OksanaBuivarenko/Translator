package com.example.lab.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = "/sql/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TranslationRequestControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    MockMvc mockMvc;

    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.4");

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    public static void AfterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @DisplayName("Get all transactionRequest")
    void getAllTranslationRq() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/api/v1/translation-request"))
                .andDo(print())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Get transactionRequest by id with status 200")
    void getTranslationRqByIdWithStatus200() throws Exception {
        this.mockMvc.perform(get("http://localhost:" + port + "/api/v1/translation-request/1"))
                .andDo(print())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.addressIP").value("0:0:0:0:0:0:0:1"))
                .andExpect(jsonPath("$.startText").value("Кот ест снег"))
                .andExpect(jsonPath("$.endText").value("Cat eats snow"))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("Delete transactionRequest by id with status 200")
    void deleteTranslationRqByIdWithStatus200() throws Exception {
        this.mockMvc.perform(delete("http://localhost:" + port + "/api/v1/translation-request/1"))
                .andDo(print())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.message").value("Запись с id 1 удалена"))
                .andExpect(jsonPath("$.length()").value(1));
    }
}