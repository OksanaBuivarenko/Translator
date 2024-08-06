package com.example.lab.repository;

import com.example.lab.entity.TranslationRequest;
import com.example.lab.mapper.RequestRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class DatabaseTranslatorRepository implements TranslatorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TranslationRequest> findAll() {
        log.debug("Calling DatabaseTranslatorRepository -> findAll");
        String sql = "SELECT * FROM translator_schema.requests";
        return jdbcTemplate.query(sql, new RequestRowMapper());
    }

    @Override
    public Optional<TranslationRequest> findById(Long id) {
        log.debug("Calling DatabaseTranslatorRepository -> findById with id {}", id);
        String sql = "SELECT * FROM requests WHERE id = ?";
        TranslationRequest request = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new RequestRowMapper(), 1)
                )
        );
        return Optional.ofNullable(request);
    }

    @Override
    public TranslationRequest save(TranslationRequest request) {
        log.debug("Calling DatabaseTranslatorRepository -> save with request {}", request);
        String sql = "INSERT INTO requests (address_ip, start_text, end_text) VALUES ( ?, ?, ?)";
        jdbcTemplate.update(sql, request.getAddressIP(), request.getStartText(), request.getEndText());
        return request;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling DatabaseTranslatorRepository -> delete by id {}", id);
        String sql = "DELETE FROM translator_schema.requests WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}