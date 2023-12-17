package tdd.groomingzone.member.domain;

import lombok.Getter;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.util.regex.Pattern;

@Getter
public class PhoneNumber {
    private static final Pattern PHONE_NUMBER = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");
    private final String phoneNumber;

    private PhoneNumber(String phoneNumber) {
        verifyPhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public static PhoneNumber of(String phoneNumber){
        return new PhoneNumber(phoneNumber);
    }

    private void verifyPhoneNumber(String phoneNumber) {
        if(!PHONE_NUMBER.matcher(phoneNumber).matches()){
            throw new BusinessException(ExceptionCode.INVALID_PHONE_NUMBER);
        }
    }
}
