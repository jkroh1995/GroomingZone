package tdd.groomingzone.global.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionCodeMapper {

    static Map<String, ExceptionCode> map = new HashMap<>();

    static {
        ExceptionCode[] exceptionCodes = ExceptionCode.values();
        for(ExceptionCode exceptionCode : exceptionCodes){
            map.put(exceptionCode.getMessage(), exceptionCode);
        }
    }

    public static ExceptionCode mapExceptionCode(String message) {
        return map.get(message);
    }
}
