package com.example.lab.mapper;

import com.example.lab.dto.response.TranslationRs;
import com.example.lab.entity.TranslationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TranslationRequestMapper {

    TranslationRequestMapper INSTANCE = getMapper(TranslationRequestMapper.class);

    TranslationRs toDto(TranslationRequest translationRequest);
}