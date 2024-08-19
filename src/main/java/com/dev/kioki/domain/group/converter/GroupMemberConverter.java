package com.dev.kioki.domain.group.converter;

import com.dev.kioki.domain.group.dto.GroupResponseDTO;
import com.dev.kioki.domain.group.entity.GroupMember;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMemberConverter {

    public static List<GroupResponseDTO.GroupMemberDTO> toGroupMemberListDTO(List<GroupMember> members){
        return members.stream()
                .map(GroupMemberConverter::toGroupMemberDTO).collect(Collectors.toList());
    }

    public static GroupResponseDTO.GroupMemberListDTO toGroupMemberListPageDTO(Page<GroupMember> groupMembers){
        List<GroupResponseDTO.GroupMemberDTO> GroupMemberListDTOList = groupMembers.stream()
                .map(GroupMemberConverter::toGroupMemberDTO).collect(Collectors.toList());

        return GroupResponseDTO.GroupMemberListDTO.builder()
                .isLast(groupMembers.isLast())
                .isFirst(groupMembers.isFirst())
                .totalPage(groupMembers.getTotalPages())
                .totalElements(groupMembers.getTotalElements())
                .listSize(GroupMemberListDTOList.size())
                .groupMemberList(GroupMemberListDTOList)
                .build();
    }

    public static GroupResponseDTO.GroupMemberDTO toGroupMemberDTO(GroupMember member){
        return GroupResponseDTO.GroupMemberDTO.builder()
                .memberId(member.getId())
                .userId(member.getMemberUserId())
                .nickname(member.getNickname())
                .profileName(member.getProfileName())
                .noteTitle(member.getNoteTitle())
                .noteText(member.getNoteText())
                .build();
    }

    public static GroupResponseDTO.GroupMemberDetailsDTO toGroupMemberDetailsDTO(GroupMember member){
        return GroupResponseDTO.GroupMemberDetailsDTO.builder()
                .memberId(member.getId())
                .userId(member.getMemberUserId())
                .nickname(member.getNickname())
                .profileName(member.getProfileName())
                .noteTitle(member.getNoteTitle())
                .noteText(member.getNoteText())
                .color(member.getColor())
                .fontSize(member.getFontSize())
                .build();
    }
}
