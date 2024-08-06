package com.example.lab.mapper;

import com.example.lab.entity.TranslationRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestRowMapper implements RowMapper<TranslationRequest> {

    @Override
    public TranslationRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        TranslationRequest request = new TranslationRequest();
        request.setId(rs.getLong("id"));
        request.setAddressIP(rs.getString("address_ip"));
        request.setStartText(rs.getString("start_text"));
        request.setEndText(rs.getString("end_text"));
        return request;
    }
}