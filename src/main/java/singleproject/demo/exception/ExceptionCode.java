package singleproject.demo.exception;

import lombok.Getter;

public enum ExceptionCode{
    EMAIL_ALREADY_EXISTS(500, "이미 존재하는 이메일입니다."),
    MEMBER_NOT_FOUNT(404, "없는 멤버입니다."),
    NOT_AUTHORED_MEMBER(500, "권한이 없습니다."),
    QUESTION_ALREADY_ANSWERED(500, "이미 답변이 완료된 질문입니다."),
    QUESTION_NOT_FOUND(404, "존재하지 않는 질문입니다."),
    QUESTION_DELETED(404, "삭제된 질문입니다.");

    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
