package com.dev.kioki.domain.group.repository;

import com.dev.kioki.domain.group.entity.GroupMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroup_GroupId(Long groupId);

    Page<GroupMember> findByGroup_GroupId(Long groupId, PageRequest pageRequest);

    GroupMember findByGroup_GroupIdAndGroupMemberId(Long groupId, Long memberUserId);

    @Query("select gm from GroupMember gm where gm.group.groupId = :groupId and gm.nickname like %:nickname%")
    List<GroupMember> findByGroupIdAndNicknameContaining(@Param("groupId") Long groupId, @Param("nickname") String nickname);
}
