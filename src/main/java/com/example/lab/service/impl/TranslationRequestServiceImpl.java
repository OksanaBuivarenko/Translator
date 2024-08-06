package com.example.lab.service.impl;

import com.example.lab.dto.response.DeleteRs;
import com.example.lab.dto.response.TranslationRs;
import com.example.lab.entity.TranslationRequest;
import com.example.lab.mapper.TranslationRequestMapper;
import com.example.lab.repository.DatabaseTranslatorRepository;
import com.example.lab.service.TranslationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranslationRequestServiceImpl implements TranslationRequestService {

    private final DatabaseTranslatorRepository translatorRepository;

    @Override
    public List<TranslationRs> getAllTranslationRq() {
        return translatorRepository.findAll().stream().map((TranslationRequestMapper.INSTANCE::toDto))
                .collect(Collectors.toList());
    }

    @Override
    public TranslationRs getTranslationRqById(Long id) {
        return TranslationRequestMapper.INSTANCE.toDto(translatorRepository.findById(id)
                .orElseThrow());
    }

    @Override
    public DeleteRs deleteTranslationRqById(Long id) {
        translatorRepository.deleteById(id);
        return DeleteRs.builder().message(String.format("Запись с id %s удалена", id)).build();
    }

    @Override
    public void save(TranslationRequest request) {
        translatorRepository.save(request);
    }
}