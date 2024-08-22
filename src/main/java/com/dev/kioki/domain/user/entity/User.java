package com.dev.kioki.domain.user.entity;

import com.dev.kioki.domain.group.entity.GroupMember;
import com.dev.kioki.domain.inquire.entity.Inquire;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.user.entity.enums.FontSize;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.user.entity.enums.UserRole;
import com.dev.kioki.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicInsert
@DynamicUpdate
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private LocalDate birthday;

    private String introduction;

    private FontSize fontSize;

    private String fontColor;

    private String imageName;

    private String kioskDifficulty;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ROLE_USER'")
    private UserRole userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.ALL)
    private List<Helper> helperList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Inquire> inquireList = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<GroupMember> groupMembers;

    @ManyToMany
    @JoinTable(
            name = "user_model",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private List<Model> modelList = new ArrayList<>();

    public void addModel(Model model) {
        if (!this.modelList.contains(model)) {
            this.modelList.add(model);
            if (!model.getUsers().contains(this)) {
                model.getUsers().add(this);
            }
        }
    }

    public void removeModel(Model model) {
        if (this.modelList.contains(model)) {
            this.modelList.remove(model);
            if (model.getUsers().contains(this)) {
                model.getUsers().remove(this);
            }
        }
    }
}
