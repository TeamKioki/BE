package com.dev.kioki.domain.group.service;

import com.dev.kioki.domain.group.Handler.GroupHandler;
import com.dev.kioki.domain.group.entity.Group;
import com.dev.kioki.domain.group.repository.GroupRepository;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.repository.UserRepository;
import com.dev.kioki.global.common.code.status.ErrorStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(readOnly = true)
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public Long getGroupIdByUser(User user) {
        Long groupId = groupRepository.findGroupIdByOwner(user)
                .orElseThrow(() -> new GroupHandler(ErrorStatus.GROUP_NOT_FOUND));
        return groupId;
    }

    @Transactional
    public Group createGroup(Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new GroupHandler(ErrorStatus.USER_NOT_FOUND));

        Group group = new Group();

        group.setOwner(owner);

        return groupRepository.save(group);
    }
}
