package com.example.lab.service;

import com.example.lab.dto.response.DeleteRs;
import com.example.lab.dto.response.TranslationRs;
import com.example.lab.entity.TranslationRequest;

import java.util.List;

public interface TranslationRequestService {

    List<TranslationRs> getAllTranslationRq();

    TranslationRs getTranslationRqById(Long id);

    DeleteRs deleteTranslationRqById(Long id);

    void save(TranslationRequest request);
}