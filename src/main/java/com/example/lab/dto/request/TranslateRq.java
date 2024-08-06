package com.example.lab.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TranslateRq {

    private String text;

    private String languageFrom;

    private String languageTo;
}