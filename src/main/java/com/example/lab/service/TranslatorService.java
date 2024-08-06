package com.example.lab.service;

import com.example.lab.dto.request.TranslateRq;
import com.example.lab.dto.response.TranslateRs;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.ExecutionException;

public interface TranslatorService {

    TranslateRs getTranslate(TranslateRq input, String remoteAddr)
            throws JsonProcessingException, ExecutionException, InterruptedException;

}