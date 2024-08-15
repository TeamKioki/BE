package com.dev.kioki.domain.group.repository;

import com.dev.kioki.domain.group.entity.GroupMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByOwnerId(Long ownerId);

    Page<GroupMember> findByOwnerId(Long ownerId, PageRequest pageRequest);

    @Query("select gm from GroupMember gm where gm.owner.id = :ownerId and gm.nickname like %:nickname%")
    List<GroupMember> findByOwnerIdAndNicknameContaining(@Param("ownerId") Long ownerId, @Param("nickname") String nickname);
}
