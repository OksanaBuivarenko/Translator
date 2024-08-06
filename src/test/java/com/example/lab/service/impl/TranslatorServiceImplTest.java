package com.example.lab.service.impl;

import com.example.lab.dto.yandex.*;
import com.example.lab.entity.TranslationRequest;
import com.example.lab.service.TranslationRequestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TranslatorServiceImplTest {

    private final TranslationRequestService translationRequestService = Mockito.mock(TranslationRequestService.class);
    private final TranslatorServiceImpl translatorService = new TranslatorServiceImpl(translationRequestService);

    @Test
    void saveRequest() {
        doNothing().when(translationRequestService).save(any(TranslationRequest.class));
        translatorService.saveRequest("0:0:0:0:0:0:0:1", "Кот ест снег", "Cat eats snow");
        verify(translationRequestService, times(1)).save(any(TranslationRequest.class));
    }

    @Test
    void createYandexTranslateDto() {
        List<String> text = List.of("Кот");
        YandexTranslateDto dto = translatorService.createYandexTranslateDto("ru",
                "en", text);
        assertEquals("ru", dto.getSourceLanguageCode());
        assertEquals("en", dto.getTargetLanguageCode());
        assertEquals("PLAIN_TEXT", dto.getFormat());
        assertEquals(true, dto.getSpeller());
    }
}