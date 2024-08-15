package com.dev.kioki.domain.user.entity;

import com.dev.kioki.domain.group.entity.GroupMember;
import com.dev.kioki.domain.inquire.entity.Inquire;
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

    private String kioskDifficulty;

    private String imageName;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ROLE_USER'")
    private UserRole userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.ALL)
    private List<Helper> helperList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Inquire> inquireList = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<GroupMember> groupMembers;

}
