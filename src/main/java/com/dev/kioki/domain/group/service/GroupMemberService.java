package com.dev.kioki.domain.group.service;

import com.dev.kioki.domain.group.Handler.GroupHandler;
import com.dev.kioki.domain.group.entity.GroupMember;
import com.dev.kioki.domain.group.repository.GroupMemberRepository;
import com.dev.kioki.global.common.code.status.ErrorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public List<GroupMember> getGroupMembers(Long groupId) {
        List<GroupMember> groupMembers = groupMemberRepository.findByGroup_GroupId(groupId);

        return groupMembers;
    }

    public Page<GroupMember> getGroupMembersList(Long groupId, Integer page) {

        if (page < 0) {
            throw new GroupHandler(ErrorStatus.PAGE_UNDER_ZERO);
        }
        Page<GroupMember> StorePage = groupMemberRepository.findByGroup_GroupId(groupId, PageRequest.of(page, 3));
        return StorePage;
    }
}
