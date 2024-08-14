package com.dev.kioki.domain.group.controller;

import com.dev.kioki.domain.group.converter.GroupMemberConverter;
import com.dev.kioki.domain.group.dto.GroupRequestDTO;
import com.dev.kioki.domain.group.dto.GroupResponseDTO;
import com.dev.kioki.domain.group.entity.GroupMember;
import com.dev.kioki.domain.group.service.GroupMemberService;
import com.dev.kioki.domain.group.service.GroupService;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.global.common.BaseResponse;
import com.dev.kioki.global.security.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.dev.kioki.global.common.code.status.SuccessStatus._CREATED;

@RestController
@RequestMapping("/groups")
@Tag(name = "그룹 관련 컨트롤러")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private GroupService groupService;

    @Operation(summary = "그룹 멤버 목록 조회 API", description = "")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @GetMapping("/members")
    public BaseResponse<List<GroupResponseDTO.GroupMemberDTO>> getAllGroupMembers(
            @AuthUser User user
    ) {
        Long groupId = groupService.getGroupIdByUser(user);
        List<GroupMember> members = groupMemberService.getGroupMembers(groupId);
        return BaseResponse.onSuccess(GroupMemberConverter.toGroupMemberListDTO(members));
    }

    @Operation(summary = "그룹 멤버 목록 조회 API (페이징 O)", description = "한 페이지 당 3개씩 보내도록 되어있습니다. 0이 첫번째 페이지 입니다!")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @GetMapping("/members/paged")
    public BaseResponse<GroupResponseDTO.GroupMemberListDTO> getPagedGroupMembers(
            @AuthUser User user,
            @RequestParam(name = "page") Integer page) {
        Long groupId = groupService.getGroupIdByUser(user);
        Page<GroupMember> members = groupMemberService.getGroupMembersList(groupId, page);

        return BaseResponse.onSuccess(GroupMemberConverter.toGroupMemberListPageDTO(members));
    }

    @Operation(summary = "프로필 사진 설정 또는 수정", description = "multipart/form-data 타입으로 profielPicture라는 이름으로 보내주시면 됩니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "멤버를 찾을 수 없습니다!",content = @Content(schema = @Schema(implementation = BaseResponse.class))),
    })
    @PostMapping(value = "/members/{memberId}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<GroupResponseDTO.GroupMemberDetailsDTO> updateProfilePicture(
            @AuthUser User user,
            @PathVariable Long memberId,
            @RequestPart(value = "profilePicture") MultipartFile profilePicture) {

        Long groupId = groupService.getGroupIdByUser(user);
        GroupMember updatedGroupMember = groupMemberService.updateProfilePicture(groupId, memberId, profilePicture);
        return BaseResponse.onSuccess(GroupMemberConverter.toGroupMemberDetailsDTO(updatedGroupMember));
    }

    @Operation(summary = "그룹 멤버 추가", description = "유저 아이디 필요합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON201",description = "OK, 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4001", description = "유저를 찾을 수 없습니다!",content = @Content(schema = @Schema(implementation = BaseResponse.class))),
    })
    @PostMapping("/members")
    public BaseResponse<GroupResponseDTO.GroupMemberDetailsDTO> addMemberToGroup(
            @AuthUser User user,
            @RequestBody GroupRequestDTO.GroupMemberRequest request) {
        Long groupId = groupService.getGroupIdByUser(user);
        GroupMember groupMember = groupMemberService.addMemberToGroup(groupId, request.getUserId());
        return BaseResponse.of(_CREATED, GroupMemberConverter.toGroupMemberDetailsDTO(groupMember));
    }

    @Operation(summary = "그룹 멤버 메모 및 커스텀 상세 수정", description = "변경하지 않을 필드는 생략해서 보내주셔도 됩니다. 프로필 사진은 다른 api를 이용해주세요. 폰트 사이즈는 NORMAL 또는 LARGE로 보내주시면 됩니다. 색상은 색상 코드로 보내주세요. 멤버가 생성되었을 때는 default로 #000000(검정색)과 NORMAL로 설정됩니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "멤버를 찾을 수 없습니다!",content = @Content(schema = @Schema(implementation = BaseResponse.class))),
    })
    @PatchMapping(value = "/members/{memberId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<GroupResponseDTO.GroupMemberDetailsDTO> updateGroupMember(
            @AuthUser User user,
            @PathVariable Long memberId,
            @RequestBody @Valid GroupRequestDTO.GroupMemberUpdateDTO memberInfo) {

        Long groupId = groupService.getGroupIdByUser(user);
        GroupMember updatedGroupMember = groupMemberService.updateGroupMember(groupId, memberId, memberInfo);
        return BaseResponse.onSuccess(GroupMemberConverter.toGroupMemberDetailsDTO(updatedGroupMember));
    }

    @Operation(summary = "그룹 멤버 삭제")
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Void> removeMember(
            @AuthUser User user,
            @PathVariable Long memberId) {
        Long groupId = groupService.getGroupIdByUser(user);
        groupMemberService.removeMemberFromGroup(groupId, memberId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "그룹 멤버 상세 조회", description = "멤버 아이디 필요합니다. 멤버 아이디는 유저아이디와 별도입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER4001", description = "유저를 찾을 수 없습니다!",content = @Content(schema = @Schema(implementation = BaseResponse.class))),
    })
    @GetMapping("/members/{memberId}/details")
    public BaseResponse<GroupResponseDTO.GroupMemberDetailsDTO> getGroupMemberDetails(
            @AuthUser User user,
            @PathVariable Long memberId) {
        Long groupId = groupService.getGroupIdByUser(user);
        GroupMember groupMemberDetails = groupMemberService.getGroupMemberDetails(groupId, memberId);
        return BaseResponse.onSuccess(GroupMemberConverter.toGroupMemberDetailsDTO(groupMemberDetails));
    }

    @Operation(summary = "그룹 내 멤버 검색", description = "검색할 내용을 쿼리 스트링으로 보내주시면 됩니다. ex) /groups/members/search?nickname=지연")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @GetMapping("/members/search")
    public BaseResponse<List<GroupResponseDTO.GroupMemberDTO>> searchGroupMembers(
            @AuthUser User user,
            @RequestParam String nickname) {

        Long groupId = groupService.getGroupIdByUser(user);
        List<GroupMember> members = groupMemberService.searchGroupMembersByNickname(groupId, nickname);
        return BaseResponse.onSuccess(GroupMemberConverter.toGroupMemberListDTO(members));
    }
}
