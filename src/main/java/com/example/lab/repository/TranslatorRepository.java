package com.example.lab.repository;

import com.example.lab.entity.TranslationRequest;

import java.util.List;
import java.util.Optional;

public interface TranslatorRepository {

    List<TranslationRequest> findAll();

    Optional<TranslationRequest> findById(Long id);

    TranslationRequest save(TranslationRequest request);

    void deleteById(Long id);
}