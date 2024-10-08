package com.dev.kioki.global.common.code.status;

import com.dev.kioki.global.common.code.BaseErrorCode;
import com.dev.kioki.global.common.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 기본 에러
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),

    // 번호 인증
    SMS_CODE_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "SMS_001", "인증 번호 전송에 실패했습니다."),
    SMS_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "SMS_002", "유저 번호에 해당하는 인증 번호를 찾을 수 없습니다."),
    INCORRECT_SMS_CODE(HttpStatus.UNAUTHORIZED, "SMS_003", "휴대폰 인증 코드가 일치하지 않습니다."),

    // 공통 에러
    PAGE_UNDER_ZERO(HttpStatus.BAD_REQUEST, "COMM_001", "페이지는 0이상이어야 합니다."),
    PAGE_NOT_VALID(HttpStatus.BAD_REQUEST, "PAGE401", "유효하지 않는 페이지입니다."),

    // 유저 관련 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_401", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "USER_402", "닉네임은 필수 입니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER_403", "이미 가입된 번호입니다."),
    USER_MODEL_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "유저가 저장하지 않은 키오스크 모델입니다."),

    // 그룹 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER_401", "그룹 멤버가 없습니다."),

    // Auth 관련 에러
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_001", "토큰이 만료되었습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "토큰이 유효하지 않습니다."),
    USER_PHONE_NOT_FOUND(HttpStatus.NOT_FOUND, "Auth_003", "해당하는 휴대폰 번호를 찾을 수 없습니다. 회원가입을 진행해주세요."),
    LOGOUT_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_004", "로그아웃된 access 토큰 입니다."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "AUTH_005", "잘못된 요청 본문입니다."),
    INVALID_REQUEST_HEADER(HttpStatus.BAD_REQUEST, "AUTH_006", "잘못된 요청 헤더입니다."),
    AUTHORIZATION_HEADER_MISSING(HttpStatus.UNAUTHORIZED, "AUTH_015", "Authorization 헤더가 비어있습니다."),
    USER_AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "AUTH_013", "유저 인증에 실패했습니다."),
    USER_INSUFFICIENT_PERMISSION(HttpStatus.FORBIDDEN, "AUTH_014", "권한이 부족한 사용자 입니다."),

    // 키오스크 모델 관련 에러
    MODEL_NOT_FOUND(HttpStatus.NOT_FOUND, "MODEL404", "키오스크 모델이 존재하지 않습니다."),

    //리뷰 관련 에러
    ADVANTAGE_CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW400", "장점 평가가 선택/작성되지 않았습니다."),
    DISADVANTAGE_CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW401", "단점 평가가 선택/작성되지 않았습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder().isSuccess(false).code(code).message(message).build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .httpStatus(httpStatus)
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }
}
