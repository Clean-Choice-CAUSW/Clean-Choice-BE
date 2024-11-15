package com.cleanChoice.cleanChoice.domain.member.domain;

import com.cleanChoice.cleanChoice.domain.intakeIngredient.domain.IntakeIngredient;
import com.cleanChoice.cleanChoice.domain.shopBasket.domain.ShopBasket;
import com.cleanChoice.cleanChoice.domain.shopRecord.domain.ShopRecord;
import com.cleanChoice.cleanChoice.domain.viewRecord.domain.ViewRecord;
import com.cleanChoice.cleanChoice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name ="gender", nullable = false)
    private Gender gender;

    @Column(name = "is_pregnant", nullable = false)
    private Boolean isPregnant;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<ViewRecord> viewRecordList;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<ShopBasket> shopBasketList;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<ShopRecord> shopRecordList;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<IntakeIngredient> intakeIngredientList;

    public static Member of(
            String loginId,
            String encodedPassword,
            String name
    ) {
        return Member.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .name(name)
                .build();
    }

}
