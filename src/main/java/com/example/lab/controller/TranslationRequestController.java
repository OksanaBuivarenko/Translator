package com.example.lab.controller;

import com.example.lab.dto.response.DeleteRs;
import com.example.lab.dto.response.TranslationRs;
import com.example.lab.service.TranslationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/translation-request")
public class TranslationRequestController {
    private final TranslationRequestService translationRequestService;

    @GetMapping()
    public List<TranslationRs> getAllTranslationRq() {
        return translationRequestService.getAllTranslationRq();
    }

    @GetMapping("/{id}")
    public TranslationRs getTranslationRqById(@PathVariable Long id) {
        return translationRequestService.getTranslationRqById(id);
    }

    @DeleteMapping("/{id}")
    public DeleteRs deleteTranslationRqById(@PathVariable Long id) {
        return translationRequestService.deleteTranslationRqById(id);
    }
}