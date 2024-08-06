package com.example.lab.service.impl;

import com.example.lab.dto.response.DeleteRs;
import com.example.lab.entity.TranslationRequest;
import com.example.lab.repository.DatabaseTranslatorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TranslationRequestServiceImplTest {

    private final DatabaseTranslatorRepository repository = Mockito.mock(DatabaseTranslatorRepository.class);
    private final TranslationRequestServiceImpl translationRequestService = new TranslationRequestServiceImpl(repository);
    private TranslationRequest translationRequest;

    @BeforeEach
    void setUp() {
        translationRequest = new TranslationRequest();
        translationRequest.setAddressIP("0:0:0:0:0:0:0:1");
        translationRequest.setStartText("Кот ест снег");
        translationRequest.setEndText("Cat eats snow");
    }

    @AfterEach
    void tearDown() {
        translationRequest = null;
    }

    @Test
    @DisplayName("Delete translationRequest by id")
    void deleteTranslationRqById() {
        translationRequest.setId(1L);
        doNothing().when(repository).deleteById(1L);
        DeleteRs res = translationRequestService.deleteTranslationRqById(1L);
        assertEquals("Запись с id 1 удалена", res.getMessage());
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Save translationRequest")
    void save() {
        when(repository.save(translationRequest)).thenReturn(translationRequest);
        translationRequestService.save(translationRequest);
        verify(repository, times(1)).save(translationRequest);
    }
}