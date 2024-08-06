package com.example.lab.service.impl;

import com.example.lab.dto.request.TranslateRq;
import com.example.lab.dto.response.TranslateRs;
import com.example.lab.dto.yandex.Language;
import com.example.lab.dto.yandex.Languages;
import com.example.lab.dto.yandex.Translations;
import com.example.lab.dto.yandex.YandexTranslateDto;
import com.example.lab.entity.TranslationRequest;
import com.example.lab.exception.LanguageNotFoundException;
import com.example.lab.service.TranslationRequestService;
import com.example.lab.service.TranslatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class TranslatorServiceImpl implements TranslatorService {

    private final TranslationRequestService translationRequestService;

    @Value("${api.url.translate}")
    private String urlTranslate;

    @Value("${api.url.languages}")
    private String urlLanguages;

    @Value("${api.string}")
    private String key;

    @Override
    public TranslateRs getTranslate(TranslateRq input, String addressIP)
            throws ExecutionException, InterruptedException {
        List<String> text = new ArrayList<>(Arrays.asList(input.getText().trim().split(" ")));
        String endText = template(input.getLanguageFrom(), input.getLanguageTo(), text);
        saveRequest(addressIP, input.getText(), endText);
        return TranslateRs.builder().translate(endText).build();
    }

    public void saveRequest(String addressIP, String startText, String endText) {
        TranslationRequest request = new TranslationRequest();
        request.setAddressIP(addressIP);
        request.setStartText(startText);
        request.setEndText(endText);
        translationRequestService.save(request);
    }

    public String template(String sourceLanguageCode, String targetLanguageCode, List<String> text)
            throws ExecutionException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Api-Key %s", key));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        StringBuilder sb = new StringBuilder();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        if (isCorrectLanguages(restTemplate, headers, sourceLanguageCode, targetLanguageCode)) {
            for (String word : text) {
                List<String> wordForTranslate = List.of(word);

                Future<String> translate = executor.submit(() -> getTranslateWord(sourceLanguageCode, targetLanguageCode,
                        restTemplate, headers, wordForTranslate));
                sb.append(translate.get());
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public String getTranslateWord(String sourceLanguageCode, String targetLanguageCode, RestTemplate template,
                                   HttpHeaders headers, List<String> text) {
        try {
            HttpEntity<YandexTranslateDto> request = new HttpEntity<>(
                    createYandexTranslateDto(sourceLanguageCode, targetLanguageCode, text), headers);
            Translations result = template.postForObject(urlTranslate, request, Translations.class);
            return result.getTranslations().get(0).getText();
        } catch (Exception e) {
            throw new ResourceAccessException("Ошибка доступа к ресурсу перевода");
        }
    }

    public Boolean isCorrectLanguages(RestTemplate restTemplate, HttpHeaders headers, String sourceLanguageCode,
                                      String targetLanguageCode) {
        HttpEntity<String> request = new HttpEntity<>(headers);
        Languages result;
        try {
            result = restTemplate.postForObject(urlLanguages, request, Languages.class);
        } catch (Exception e) {
            throw new ResourceAccessException("Ошибка доступа к ресурсу перевода");
        }
        boolean isSource = false;
        boolean isTarget = false;
        for (Language l : result.getLanguages()) {
            if (l.getCode().equals(sourceLanguageCode)) {
                isSource = true;
            }
            if (l.getCode().equals(targetLanguageCode)) {
                isTarget = true;
            }
        }
        if (!isSource) {
            throw new LanguageNotFoundException("Не найден язык исходного сообщения");
        }
        if (!isTarget) {
            throw new LanguageNotFoundException("Не найден язык целевого сообщения");
        }
        return true;
    }

    public YandexTranslateDto createYandexTranslateDto(String sourceLanguageCode, String targetLanguageCode,
                                                       List<String> text) {
        return YandexTranslateDto.builder()
                .sourceLanguageCode(sourceLanguageCode)
                .targetLanguageCode(targetLanguageCode)
                .format("PLAIN_TEXT")
                .texts(text)
                .glossaryConfig(null)
                .speller(true)
                .build();
    }
}