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
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    public static IntakeIngredient of(Member member, Ingredient ingredient) {
        return IntakeIngredient.builder()
                .member(member)
                .ingredient(ingredient)
                .build();
    }

}
