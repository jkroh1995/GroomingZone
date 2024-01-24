package tdd.groomingzone.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    TOKEN_EXPIRED(401, "토큰이 만료되었습니다."),
    INVALID_SIGN_IN(401,"이메일 또는 비밀번호를 확인하세요."),
    ALREADY_SIGN_OUT(401, "이미 로그아웃 되었습니다."),

    NOT_SIGN_IN(403, "먼저 로그인 하세요."),
    UNAUTHORIZED(403, "권한이 없습니다."),
    EMAIL_ALREADY_EXISTS(409, "이미 가입한 이메일입니다."),
    ALREADY_SIGN_IN(409, "이미 로그인 처리 되었습니다."),

    MEMBER_NOT_FOUND(404, "없는 사용자입니다."),
    BOARD_NOT_FOUND(404, "존재하지 않는 게시글입니다."),
    COMMENT_NOT_FOUND(404, "없는 댓글입니다."),

    INVALID_EMAIL(409, "올바른 이메일 형식이 아닙니다."),
    INVALID_PASSWORD(409, "비밀번호는 영어 대소문자, 특수문자, 숫자를 포함한 8 ~ 16글자 사이로 이루어져야 합니다."),
    INVALID_PHONE_NUMBER(409, "올바른 전화번호 형식이 아닙니다."),
    INVALID_BOARD_TITLE(409, "게시글 제목은 빈 칸이면 안됩니다."),
    INVALID_ROLE(409, "바버, 고객 중 역할을 선택해주세요."),
    INVALID_REQUEST(409, "잘못된 요청입니다."),
    INVALID_BOARD_CONTENT(409, "글 내용은 빈 칸이면 안됩니다."),
    INVALID_RECRUITMENT_TYPE(409, "구인, 구직 중 선택해주세요."),
    INVALID_OAUTH_LOGIN(409, "유효하지 않은 소셜 로그인 시도입니다.");

    private final int status;
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
