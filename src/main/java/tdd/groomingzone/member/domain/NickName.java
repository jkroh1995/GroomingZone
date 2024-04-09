package tdd.groomingzone.member.domain;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

public class NickName {

    private String nickName;

    private NickName(String nickName) {
        this.nickName = nickName;
    }

    public static NickName of(String nickName) {
        verifyNickName(nickName);
        return new NickName(nickName);
    }

    private static void verifyNickName(String nickName) {
        verifyLength(nickName);
        verifyNickNameIsNotBlank(nickName);
    }

    private static void verifyNickNameIsNotBlank(String nickName) {
        if(nickName.isBlank()){
            throw new BusinessException(ExceptionCode.BLANK_NICK_NAME);
        }
    }

    private static void verifyLength(String nickName) {
        if(nickName.length() < 3 || nickName.length() > 10){
            throw new BusinessException(ExceptionCode.INVALID_NICK_NAME_LENGTH);
        }
    }

    public String getValue() {
        return this.nickName;
    }
}
