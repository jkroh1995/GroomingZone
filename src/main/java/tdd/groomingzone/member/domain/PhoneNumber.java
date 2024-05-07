package tdd.groomingzone.member.domain;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.util.regex.Pattern;

public record PhoneNumber(
        String phoneNumber
) {
    private static final Pattern PHONE_NUMBER = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");

    public PhoneNumber {
        verifyPhoneNumber(phoneNumber);
    }

    private void verifyPhoneNumber(String phoneNumber) {
        if(!PHONE_NUMBER.matcher(phoneNumber).matches()){
            throw new BusinessException(ExceptionCode.INVALID_PHONE_NUMBER);
        }
    }
}
