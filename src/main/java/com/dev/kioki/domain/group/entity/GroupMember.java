package com.dev.kioki.domain.group.entity;

import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.entity.enums.FontSize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Builder
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_user_id")
    private User member;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String profileName;
    private String noteText;
    private String noteTitle;
    @Builder.Default
    private String color = "#000000";

    private String nickname;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private FontSize fontSize = FontSize.NORMAL;


    public Long getMemberUserId() {
        return member != null ? member.getId() : null;
    }
}