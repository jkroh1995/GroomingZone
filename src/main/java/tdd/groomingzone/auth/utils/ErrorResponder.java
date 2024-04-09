package tdd.groomingzone.auth.utils;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import tdd.groomingzone.global.exception.ErrorResponse;
import tdd.groomingzone.global.exception.ExceptionCode;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {
        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(exceptionCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(exceptionCode.getStatus());
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }
}
