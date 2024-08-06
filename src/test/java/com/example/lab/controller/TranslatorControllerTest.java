package com.example.lab.controller;

import com.example.lab.dto.request.TranslateRq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = "/sql/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TranslatorControllerTest {

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
    @DisplayName("Get translate")
    void getTranslate() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TranslateRq input = TranslateRq.builder()
                .languageFrom("en")
                .languageTo("ru")
                .text("Hello world")
                .build();

        this.mockMvc.perform(post("http://localhost:" + port + "/api/v1/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andDo(print())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.translate").value("Здравствуйте мир"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Get translate with not correct languageFrom")
    void getTranslateWithNotCorrectLanguageFrom() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TranslateRq input = TranslateRq.builder()
                .languageFrom("envjvhjvh")
                .languageTo("ru")
                .text("Hello world")
                .build();

        this.mockMvc.perform(post("http://localhost:" + port + "/api/v1/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andDo(print())
                .andExpectAll(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Не найден язык исходного сообщения"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Get translate with not correct languageTo")
    void getTranslateWithNotCorrectLanguageTo() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TranslateRq input = TranslateRq.builder()
                .languageFrom("en")
                .languageTo("rufhgthg")
                .text("Hello world")
                .build();

        this.mockMvc.perform(post("http://localhost:" + port + "/api/v1/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andDo(print())
                .andExpectAll(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Не найден язык целевого сообщения"))
                .andExpect(jsonPath("$.length()").value(1));
    }
}