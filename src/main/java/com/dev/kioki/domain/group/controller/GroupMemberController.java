package com.dev.kioki.domain.group.controller;

import com.dev.kioki.domain.group.converter.GroupMemberConverter;
import com.dev.kioki.domain.group.dto.GroupRequestDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dev.kioki.global.common.code.status.SuccessStatus._CREATED;

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

    @Operation(summary = "그룹 멤버 추가", description = "유저 아이디 필요합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON201",description = "OK, 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4001", description = "유저를 찾을 수 없습니다!",content = @Content(schema = @Schema(implementation = BaseResponse.class))),
    })
    @PostMapping("/members")
    public BaseResponse<GroupResponseDTO.GroupMemberDetailsDTO> addMemberToGroup(
            //@PathVariable Long groupId,
            @RequestBody GroupRequestDTO.GroupMemberRequest request) {
        Long groupId = 1L;
        GroupMember groupMember = groupMemberService.addMemberToGroup(groupId, request.getUserId());
        return BaseResponse.of(_CREATED, GroupMemberConverter.toGroupMemberDetailsDTO(groupMember));
    }
}
