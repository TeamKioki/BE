package com.dev.kioki.domain.review.entity;

import com.dev.kioki.domain.brand.entity.Brand;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicInsert
@DynamicUpdate
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double score;

    //장점 항목 - 기타
    private String advantage_content;

    //장점 항목
    private String advantages;

    //단점 항목 - 기타
    private String disadvantage_content;

    //단점 항목
    private String disadvantages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    public void setUser(User user){
        if(this.user != null)
            user.getReviewList().remove(this);
        this.user = user;
        user.getReviewList().add(this);
    }

    public void setModel(Model model){
        if(this.model != null)
            model.getReviewList().remove(this);
        this.model = model;
        model.getReviewList().add(this);
    }
}
