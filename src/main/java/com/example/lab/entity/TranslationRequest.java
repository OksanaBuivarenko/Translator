package com.example.lab.entity;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class TranslationRequest {

    private Long id;

    private String addressIP;

    private String startText;

    private String endText;

}