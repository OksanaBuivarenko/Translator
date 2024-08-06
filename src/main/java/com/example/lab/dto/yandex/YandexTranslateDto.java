package com.example.lab.dto.yandex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Builder
@Data
public class YandexTranslateDto {

    private String sourceLanguageCode;

    private String targetLanguageCode;

    private String format;

    private List<String> texts;

    private GlossaryConfig glossaryConfig;

    private Boolean speller;
}
