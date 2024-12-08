package com.cleanChoice.cleanChoice.domain.intakeIngredient.domain;

import com.cleanChoice.cleanChoice.domain.ingredient.domain.Ingredient;
import com.cleanChoice.cleanChoice.domain.member.domain.Member;
import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "intake_ingredient")
public class IntakeIngredient extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = true)
    private Ingredient ingredient;

    @Column(name = "fake_name", nullable = true)
    private String fakeName;

    @Column(name = "amount", nullable = true)
    private String amount;

    @Column(name = "unit", nullable = true)
    private String unit;

    public static IntakeIngredient createWithIngredient(
            Member member,
            Ingredient ingredient,
            String amount,
            String unit
    ) {
        return IntakeIngredient.builder()
                .member(member)
                .ingredient(ingredient)
                .amount(amount)
                .unit(unit)
                .build();
    }

    public static IntakeIngredient createWithFakeName(
            Member member,
            String fakeName,
            String amount,
            String unit
    ) {
        return IntakeIngredient.builder()
                .member(member)
                .fakeName(fakeName)
                .amount(amount)
                .unit(unit)
                .build();
    }

}
