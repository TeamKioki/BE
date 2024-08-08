package com.dev.kioki.domain.group.repository;

import com.dev.kioki.domain.group.entity.GroupMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroup_GroupId(Long groupId);

    Page<GroupMember> findByGroup_GroupId(Long groupId, PageRequest pageRequest);
}
