package singleproject.demo.user;

import lombok.Getter;

public enum ExceptionCode{
    EMAIL_ALREADY_EXISTS(404, "이미 존재하는 이메일입니다.");

    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
