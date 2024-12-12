package com.cleanChoice.cleanChoice.domain.ingredient.domain.repository;

import com.cleanChoice.cleanChoice.domain.dataInput.dto.CreateIngredientEffectivenessRequestDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class IngredientRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public IngredientRepositoryCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void bulkUpdateEffectiveness(List<CreateIngredientEffectivenessRequestDto> dtos) {
        if (dtos.isEmpty()) {
            return; // DTO가 비어있으면 처리하지 않음
        }

        // `CASE` 구문 생성
        StringBuilder sql = new StringBuilder("UPDATE ingredient SET effectiveness = CASE ");

        for (CreateIngredientEffectivenessRequestDto dto : dtos) {
            sql.append("WHEN english_name = '")
                    .append(dto.getIngredientName().replace("'", "''")) // SQL Injection 방지
                    .append("' THEN '")
                    .append(dto.getEffectiveness().replace("'", "''")) // SQL Injection 방지
                    .append("' ");
        }

        sql.append("END WHERE english_name IN (");

        // `WHERE IN` 조건 생성
        String whereClause = dtos.stream()
                .map(dto -> "'" + dto.getIngredientName().replace("'", "''") + "'") // SQL Injection 방지
                .collect(Collectors.joining(", "));

        sql.append(whereClause).append(")");

        // SQL 실행
        jdbcTemplate.execute(sql.toString());
    }

}
