package com.dev.kioki.domain.store.entity;

import com.dev.kioki.domain.brand.entity.enums.BrandType;
import com.dev.kioki.domain.kiosk.entity.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    private String name;

    private BrandType brand;

    private Integer store_count;

    private Integer kiosk_count;

    private String icon_url;
}
