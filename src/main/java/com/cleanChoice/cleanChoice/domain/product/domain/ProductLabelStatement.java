package com.cleanChoice.cleanChoice.domain.product.domain;

import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "product_label_statement")
public class ProductLabelStatement extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ex: Precautions
    @Enumerated(EnumType.STRING)
    @Column(name = "statement_type", nullable = false)
    private StatementType statementType;

    // ex: WARNING: If you are pregnant, nursing or taking any medications, consult your doctor before use. Discontinue use and consult your doctor if any adverse reactions occur. Keep out of reach of children. Store in a cool, dry place. Do not use if seal under cap is broken or missing.
    @Column(name = "english_statement", nullable = false)
    private String englishStatement;

    @Column(name = "korean_statement", nullable = false)
    private String koreanStatement;

    public static ProductLabelStatement of(
            Product product,
            StatementType statementType,
            String englishStatement,
            String koreanStatement
    ) {
        return ProductLabelStatement.builder()
                .product(product)
                .statementType(statementType)
                .englishStatement(englishStatement)
                .koreanStatement(koreanStatement)
                .build();
    }

}
