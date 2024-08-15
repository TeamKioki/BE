package com.dev.kioki.domain.group.service;

import com.dev.kioki.domain.group.Handler.GroupHandler;
import com.dev.kioki.domain.group.dto.GroupRequestDTO;
import com.dev.kioki.domain.group.entity.GroupMember;
import com.dev.kioki.domain.group.repository.GroupMemberRepository;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.repository.UserRepository;
import com.dev.kioki.global.common.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;

    private final UserRepository userRepository;

    public List<GroupMember> getGroupMembers(Long ownerId) {
        return groupMemberRepository.findByOwnerId(ownerId);
    }

    public Page<GroupMember> getGroupMembersList(Long ownerId, Integer page) {

        if (page < 0) {
            throw new GroupHandler(ErrorStatus.PAGE_UNDER_ZERO);
        }
        return groupMemberRepository.findByOwnerId(ownerId, PageRequest.of(page, 3));
    }

    @Transactional
    public GroupMember addMemberToGroup(Long ownerId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GroupHandler(ErrorStatus.USER_NOT_FOUND));

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new GroupHandler(ErrorStatus.USER_NOT_FOUND));


        String profilePictureUrl = user.getImageName();
        String noteTitle = user.getIntroduction();
        String noteText = "";
        String nickname = user.getName();


        GroupMember groupMember = GroupMember.builder()
                .owner(owner)
                .member(user)
                .profilePictureUrl(profilePictureUrl)
                .noteText(noteText)
                .noteTitle(noteTitle)
                .nickname(nickname)
                .build();

        return groupMemberRepository.save(groupMember);
    }


    @Transactional
    public void removeMemberFromGroup(Long memberId) {
        GroupMember groupMember = groupMemberRepository.findById(memberId)
                .orElseThrow(() -> new GroupHandler(ErrorStatus.USER_NOT_FOUND));
        groupMemberRepository.delete(groupMember);
    }

    @Transactional
    public GroupMember updateGroupMember(Long memberId, GroupRequestDTO.GroupMemberUpdateDTO memberInfo) {
        GroupMember groupMember = groupMemberRepository.findById(memberId)
                .orElseThrow(() -> new GroupHandler(ErrorStatus.USER_NOT_FOUND));
        if (groupMember == null) {
            throw new GroupHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        //DTO의 필드가 null이 아닌 경우에만 업데이트
        if (memberInfo.getNoteTitle() != null) {
            groupMember.setNoteTitle(memberInfo.getNoteTitle());
        }
        if (memberInfo.getNoteBody() != null) {
            groupMember.setNoteText(memberInfo.getNoteBody());
        }
        if (memberInfo.getColor() != null) {
            groupMember.setColor(memberInfo.getColor());
        }
        if (memberInfo.getFontSize() != null) {
            groupMember.setFontSize(memberInfo.getFontSize());
        }
        if (memberInfo.getNickname() != null) {
            groupMember.setNickname(memberInfo.getNickname());
        }

        return groupMemberRepository.save(groupMember);
    }


    public GroupMember getGroupMember(Long memberId) {
        return groupMemberRepository.findById(memberId)
                .orElseThrow(() -> new GroupHandler(ErrorStatus.USER_NOT_FOUND));
    }

    public List<GroupMember> searchGroupMembersByNickname(Long ownerId, String nickname) {
        return groupMemberRepository.findByOwnerIdAndNicknameContaining(ownerId, nickname);
    }

}
