package com.example.lab.controller;

import com.example.lab.dto.request.TranslateRq;
import com.example.lab.dto.response.TranslateRs;
import com.example.lab.service.TranslatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/translate")
public class TranslatorController {

    private final TranslatorService translatorService;

    @PostMapping()
    public TranslateRs getTranslate(@RequestBody TranslateRq input, HttpServletRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        return translatorService.getTranslate(input, request.getRemoteAddr());
    }
}