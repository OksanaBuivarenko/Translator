package com.example.lab.dto.yandex;

import lombok.Data;

@Data
public class GlossaryPairs {

    private String sourceText;

    private String translatedText;

    private Boolean exact;

}