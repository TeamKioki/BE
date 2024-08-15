package com.dev.kioki.domain.kiosk.entity;

import com.dev.kioki.domain.store.entity.Store;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.global.common.BaseEntity;
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
public class Model extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    private Integer level;

    private Float rate;

    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY)
    private List<Store> store  = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        if(this.user != null) {
            user.getModelList().remove(this);
        }
        this.user = user;
        user.getModelList().add(this);
    }
}
