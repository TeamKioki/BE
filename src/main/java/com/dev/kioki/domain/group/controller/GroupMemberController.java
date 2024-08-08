package com.dev.kioki.domain.group.controller;

import com.dev.kioki.domain.group.converter.GroupMemberConverter;
import com.dev.kioki.domain.group.dto.GroupResponseDTO;
import com.dev.kioki.domain.group.entity.GroupMember;
import com.dev.kioki.domain.group.service.GroupMemberService;
import com.dev.kioki.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/groups")
@Tag(name = "그룹 관련 컨트롤러")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @Operation(summary = "그룹 멤버 목록 조회 API", description = "")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @GetMapping("/members")
    public BaseResponse<List<GroupResponseDTO.GroupMemberDTO>> getGroupMembers(
            //@PathVariable Long groupId
    ) {
        Long groupId = 1L;
        List<GroupMember> members = groupMemberService.getGroupMembers(groupId);
        return BaseResponse.onSuccess(GroupMemberConverter.toGroupMemberListDTO(members));
    }

    @Operation(summary = "그룹 멤버 목록 조회 API (페이징 O)", description = "한 페이지 당 3개씩 보내도록 되어있습니다. 0이 첫번째 페이지 입니다!")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @GetMapping("/members/paged")
    public BaseResponse<GroupResponseDTO.GroupMemberListDTO> getGroupMembers(
            //@PathVariable Long groupId,
            @RequestParam(name = "page") Integer page) {
        Long groupId = 1L;
        Page<GroupMember> members = groupMemberService.getGroupMembersList(groupId, page);

        return BaseResponse.onSuccess(GroupMemberConverter.toGroupMemberListPageDTO(members));
    }
}
