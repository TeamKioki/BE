package com.dev.kioki.domain.group.service;

import com.dev.kioki.domain.group.Handler.GroupHandler;
import com.dev.kioki.domain.group.dto.GroupRequestDTO;
import com.dev.kioki.domain.group.entity.Group;
import com.dev.kioki.domain.group.entity.GroupMember;
import com.dev.kioki.domain.group.repository.GroupMemberRepository;
import com.dev.kioki.domain.group.repository.GroupRepository;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.repository.UserRepository;
import com.dev.kioki.global.common.code.status.ErrorStatus;
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
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    private String uploadDir = "uploads/";

    private GroupMember getGroupMemberOrThrow(Long groupId, Long memberId) {
        GroupMember groupMember = groupMemberRepository.findByGroup_GroupIdAndGroupMemberId(groupId, memberId);
        if (groupMember == null) {
            throw new GroupHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        return groupMember;
    }

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

    @Transactional
    public GroupMember addMemberToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(()-> new GroupHandler(ErrorStatus.GROUP_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GroupHandler(ErrorStatus.USER_NOT_FOUND));

        String profilePictureUrl = user.getImageName();
        String noteTitle = user.getIntroduction();
        String noteText = "";
        String nickname = user.getName();


        GroupMember groupMember = GroupMember.builder()
                .group(group)
                .member(user)
                .profilePictureUrl(profilePictureUrl)
                .noteText(noteText)
                .noteTitle(noteTitle)
                .nickname(nickname)
                .build();

        return groupMemberRepository.save(groupMember);
    }


    @Transactional
    public void removeMemberFromGroup(Long groupId, Long memberId) {
        // 해당 그룹에서 특정 멤버를 찾음
        GroupMember groupMember = getGroupMemberOrThrow(groupId, memberId);
        groupMemberRepository.delete(groupMember);
    }

    @Transactional
    public GroupMember updateProfilePicture(Long groupId, Long memberId, MultipartFile profilePicture) {
        GroupMember groupMember = getGroupMemberOrThrow(groupId, memberId);


        if (groupMember == null) {
            throw new GroupHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                // 기존 파일 삭제 (존재하는 경우)
                String existingFileName = groupMember.getProfilePictureUrl();
                if (existingFileName != null) {
                    Path existingFilePath = Paths.get(uploadDir, existingFileName.substring(existingFileName.lastIndexOf('/') + 1));
                    Files.deleteIfExists(existingFilePath);
                }

                // 새로운 파일 이름 생성 (UUID 사용)
                String originalFilename = profilePicture.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                String newFilename = UUID.randomUUID() + extension;

                // 새로운 파일 저장
                Path path = Paths.get(uploadDir, newFilename);
                Files.createDirectories(path.getParent());
                Files.write(path, profilePicture.getBytes());

                // 파일 URL 생성
                String fileUrl = "/files/" + newFilename;
                groupMember.setProfilePictureUrl(fileUrl);

            } catch (IOException e) {
                throw new RuntimeException("파일 저장 또는 삭제에 실패했습니다.", e);
            }
        }
        return groupMemberRepository.save(groupMember);
    }

    @Transactional
    public GroupMember updateGroupMember(Long groupId, Long memberId, GroupRequestDTO.GroupMemberUpdateDTO memberInfo) {
        GroupMember groupMember = getGroupMemberOrThrow(groupId, memberId);

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


    public GroupMember getGroupMemberDetails(Long groupId, Long memberId) {
        GroupMember groupMember = getGroupMemberOrThrow(groupId, memberId);

        if (groupMember == null) {
            throw new GroupHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        return groupMember;
    }

    public List<GroupMember> searchGroupMembersByNickname(Long groupId, String nickname) {
        List<GroupMember> members = groupMemberRepository.findByGroupIdAndNicknameContaining(groupId, nickname);

        return members;
    }

}
