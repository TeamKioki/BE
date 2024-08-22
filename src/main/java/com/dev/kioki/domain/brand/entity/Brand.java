package com.dev.kioki.domain.brand.entity;

import com.dev.kioki.domain.brand.entity.enums.BrandType;
import com.dev.kioki.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    //평균 점수
    private Double avgScore;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'FASTFOOD'")
    private BrandType brandType;

    private String imageUrl;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();
}
