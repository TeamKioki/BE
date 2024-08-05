package com.dev.kioki.domain.brand.entity;

import com.dev.kioki.domain.brand.entity.enums.BrandType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicInsert
@DynamicUpdate
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double score;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'FASTFOOD'")
    private BrandType brandType;

    private String imageUrl;
}
