package com.dev.kioki.domain.store.entity;

import com.dev.kioki.domain.brand.entity.enums.BrandType;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "store_count")
    private Integer storeCount;

    @Column(name = "kiosk_count")
    private Integer kioskCount;

    private Float distance;

    private String icon_url;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
