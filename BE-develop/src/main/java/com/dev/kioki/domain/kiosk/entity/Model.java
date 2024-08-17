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

    @ManyToMany(mappedBy = "modelList")
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        if (!this.users.contains(user)) {
            this.users.add(user);
            if (!user.getModelList().contains(this)) {
                user.getModelList().add(this);
            }
        }
    }

    public void removeUser(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
            if (user.getModelList().contains(this)) {
                user.getModelList().remove(this);
            }
        }
    }
}
